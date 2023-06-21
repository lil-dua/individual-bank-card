/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bankcard.repository;

import com.mycompany.bankcard.accuracy.RSA;
import static com.mycompany.bankcard.accuracy.RSA.*;
import static com.mycompany.bankcard.config.BytesConfig.*;
import com.mycompany.bankcard.data.SqlManager;
import com.mycompany.bankcard.model.BankCard;
import static com.mycompany.bankcard.util.Constans.*;
import java.security.PublicKey;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import javax.smartcardio.CardException;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;

/**
 *
 * @author admin
 */
public class BankCardRepository {
    
    private SqlManager sqlManager;
    private final BankCardManager bankCardManager;
    
    public BankCardRepository(BankCardManager bankCardManager) {
        this.bankCardManager = bankCardManager;
    }
    //kiemtra ma pin nhap vao va ma pin hien tai
    public Integer verify(String pin) throws CardException {
        CommandAPDU verify = new CommandAPDU(0x00, INS_VERIFY, 0x00, 0x00, pin.getBytes());
        ResponseAPDU responseAPDU = bankCardManager.transmit(verify);
        if (responseAPDU.getSW1() == 0x90 && responseAPDU.getSW2() == 0x00) {
            return null;
        }
        return (int) responseAPDU.getData()[0];
    }
    
    public BankCard createInformationBankCard(String cardName) throws CardException {
        sqlManager = new SqlManager();
        String id = String.valueOf(sqlManager.getNumberCard() + 1);
        Date now = Calendar.getInstance().getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.YEAR, 5);
        BankCard bankCard = new BankCard(id, cardName, 0L, now, calendar.getTime());
        CommandAPDU createCommand = new CommandAPDU(0x00, INS_CREATE, P1_INFORMATION, 0x00, bankCard.formatDataBankCard());
        ResponseAPDU createResponse = bankCardManager.transmit(createCommand);
        if (createResponse.getSW1() != 0x90 || createResponse.getSW2() != 0x00) {
            return null;
        }
        generateKey(id, createResponse.getData());
        return bankCard;
    }
    
    private void generateKey(String numberCard, byte[] data) {
        int offset = 0x00;
        int exponentLength = makeInteger(data, offset);
        int modulusLength = makeInteger(data, offset + 2 + exponentLength);
        byte[] exponentBytes = copyOfRange(data, offset + 2, exponentLength);
        byte[] modulusBytes = copyOfRange(data, offset + 2 + exponentLength + 2, modulusLength);
        PublicKey publicKey = generatePublicKey(exponentBytes, modulusBytes);
        
        if (publicKey == null) {
            return;
        }
        sqlManager.insertData(numberCard, publicKey);
    }
    
    private BankCard parseInformation(byte[] data) throws ParseException {
        if (data.length < 1) {
            return null;
        }
        //51111113hoanghuuthom1005292023
//        return ((char)numberCardFomat.length() + numberCardFomat
//                +(char)nameCard.length() + nameCard
//                +(char)numberBalanceFormat.length()+numberBalanceFormat
//                +(char)createDateFormat.length()+createDateFormat
//                +(char)expirationDateFormat.length()+expirationDateFormat).getBytes();
        BankCard bankCard = new BankCard();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int offset = 0;
        byte[] dataFormat;
        dataFormat = Arrays.copyOfRange(data, offset + 1, data[offset] + 1);
        String numberCard = new String(dataFormat);
        //System.out.println(numberCard);
        
        offset += numberCard.length() + 1;
        dataFormat = Arrays.copyOfRange(data, offset + 1, offset + data[offset] + 1);
        String nameCard = new String(dataFormat);
        
        //System.out.println(nameCard);
        offset += nameCard.length() + 1;
        dataFormat = Arrays.copyOfRange(data, offset + 1, offset + data[offset] + 1);
        String createDate = new String(dataFormat);
        //System.out.println(createDate);
        offset += createDate.length() + 1;
        dataFormat = Arrays.copyOfRange(data, offset + 1, offset + data[offset] + 1);
        String expiretionDate = new String(dataFormat);
        //System.out.println(expiretionDate);
        offset += expiretionDate.length() + 1;
        dataFormat = Arrays.copyOfRange(data, offset + 1, offset + data[offset] + 1);
        long numberBalance = Long.parseLong(new String(dataFormat));
        
        bankCard.setNumberCard(numberCard);
        bankCard.setNameCard(nameCard);
        bankCard.setCreateDate(dateFormat.parse(createDate));
        bankCard.setExpirationDate(dateFormat.parse(expiretionDate));
        bankCard.setNumberBalance(numberBalance);
        return bankCard;
    }
    
    public BankCard getInformationCard() {
        try {
            CommandAPDU getCommand = new CommandAPDU(0x00, INS_GET, P1_INFORMATION, P2_INFORMATION);
            ResponseAPDU response = bankCardManager.transmit(getCommand);
            if (response.getSW1() != 0x90 || response.getSW2() != 0x00) {
                return null;
            }
            BankCard bankCard = parseInformation(response.getData());
            if (bankCard == null) {
                return null;
            }
            byte[] avatar = getAvatar();
            bankCard.setImageAvatar(avatar);
            return bankCard;
        } catch (CardException | ParseException e) {
            return null;
        }
    }

    public byte[] getAvatar() throws CardException {
        CommandAPDU getCommand = new CommandAPDU(0x00, INS_GET, P1_INFORMATION, P2_AVATAR);
        ResponseAPDU response = bankCardManager.transmit(getCommand);
        return response.getSW1() == 0x90 && response.getSW2() == 0x00 ? response.getData() : null;
    }
    
    public boolean updateAvatar(byte[] avatar) throws CardException {
        CommandAPDU updateCommand = new CommandAPDU(0x00, INS_UPDATE, P1_INFORMATION, P2_AVATAR, avatar);
        ResponseAPDU response = bankCardManager.transmit(updateCommand);
        return response.getSW1() == 0x90 && response.getSW2() == 0x00;
    }
    
    public Long getNumberBalance() throws CardException {
        CommandAPDU getCommand = new CommandAPDU(0x00, INS_GET, P1_INFORMATION, P2_REMAINING_BALANCE);
        ResponseAPDU response = bankCardManager.transmit(getCommand);
        return response.getSW1() != 0x90 || response.getSW2() != 0x00 ? null : Long.valueOf(new String(response.getData()));
    }
    
    private boolean updateNumberMoney(long remainingBalance) throws CardException {
        byte[] data = String.valueOf(remainingBalance).getBytes();
        CommandAPDU updateCommand = new CommandAPDU(0x00, INS_UPDATE, P1_INFORMATION, P2_REMAINING_BALANCE, data);
        ResponseAPDU response = bankCardManager.transmit(updateCommand);
        return response.getSW1() == 0x90 && response.getSW2() == 0x00;
    }
    
    public boolean recharge(long money) throws CardException {
        BankCard bankCard =getInformationCard();
        Long currentRemainingBalance = getNumberBalance();
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        String description = "Nhan tien, So tien nhan: "+money+", So su hien tai: "+String.valueOf(getNumberBalance() + money);
        sqlManager = new SqlManager();
        sqlManager.insertHistory(bankCard.getNumberCard(), timeStamp, description);
        
        return currentRemainingBalance != null && updateNumberMoney(currentRemainingBalance + money);
    }
    
    public boolean updateInformation(String nameCard) throws CardException {
        byte[] data = ((char) nameCard.length() + nameCard).getBytes();
        CommandAPDU updateCommand = new CommandAPDU(0x00, INS_UPDATE, P1_INFORMATION, P2_INFORMATION, data);
        ResponseAPDU response = bankCardManager.transmit(updateCommand);
        return response.getSW1() == 0x90 && response.getSW2() == 0x00;
    }
    
    public Integer updatePin(String currentPIN, String newPIN) throws CardException {
        byte[] data = ((char) currentPIN.length() + currentPIN + (char) newPIN.length() + newPIN).getBytes();
        CommandAPDU updateCommand = new CommandAPDU(0x00, INS_UPDATE, P1_PIN, 0x00, data);
        ResponseAPDU response = bankCardManager.transmit(updateCommand);
        return response.getSW1() == 0x90 && response.getSW2() == 0x00 ? null : (int) response.getData()[0];
    }
    public boolean withdrawMoney(long money, String PIN) throws CardException, RuntimeException, SQLException {
        Integer retriesRemaining = verify(PIN);
        System.out.println("retriesRemaining: "+retriesRemaining);
        if (retriesRemaining != null) {
            if (retriesRemaining == 0) {
                throw new RuntimeException(CARD_HAS_BLOCKED);
            }
            throw new RuntimeException("Your PIN is incorrect!");
        }
        BankCard bankCard = getInformationCard();
        if (bankCard == null) {
            return false;
        }
        if (money > bankCard.getNumberBalance()) {
            throw new RuntimeException("Your money is not enough");
        }
        accuracy(bankCard.getNumberCard());
        updateNumberMoney(bankCard.getNumberBalance() - money);
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        String description = "Chuyen tien, So tien chuyen: -"+money+", So su hien tai: "+String.valueOf(bankCard.getNumberBalance() - money);
        sqlManager = new SqlManager();
        sqlManager.insertHistory(bankCard.getNumberCard(), timeStamp, description);
        return true;
    }
    private void accuracy(String numberCard) throws CardException, RuntimeException, SQLException {
        Random random = new Random();
        String code = String.valueOf(random.nextInt(900000) + 100000);
        byte[] signature = getSignature(code);
        PublicKey key = getPublicKey(numberCard);
        if (key == null) {
            throw new RuntimeException("Authentication failed!");
        }
        if (RSA.accuracy(signature, key, code)) {
            return;
        }
        throw new RuntimeException("Authentication failed!");
    }

    private byte[] getSignature(String code) throws CardException {
        CommandAPDU getCommand = new CommandAPDU(0x00, INS_CREATE, P1_SIGNATURE, 0x00, code.getBytes());
        ResponseAPDU response = bankCardManager.transmit(getCommand);
        return response.getSW1() == 0x90 && response.getSW2() == 0x00 ? response.getData() : null;
    }

    private PublicKey getPublicKey(String numberCard) throws SQLException {
        sqlManager=new SqlManager();
        return sqlManager.getData(numberCard);
    }
}
