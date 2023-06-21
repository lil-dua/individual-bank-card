/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bankcard.repository;

import static com.mycompany.bankcard.util.Constans.AID;
import java.util.List;
import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;
import javax.smartcardio.TerminalFactory;

/**
 *
 * @author admin
 */
public class BankCardManager {
    private  static BankCardManager instance=null;
   private Card card;
   public BankCardManager(){}
   public void connect() throws CardException {
        TerminalFactory factory = TerminalFactory.getDefault();
        //lấy rất cả list card kết nối

        List<CardTerminal> terminals= factory.terminals().list();
        Card card = terminals.get(0).connect("*");
        CardChannel channel = card.getBasicChannel();
        //kết nối với thẻ
        CommandAPDU selectCommand = new CommandAPDU(0x00, 0xA4, 0x04, 0x00, AID);
        //
        ResponseAPDU response = channel.transmit(selectCommand);
        //response trả về kết quả 90 00 là kết nối thành công
        if (response.getSW1() == 0x90 && response.getSW2() == 0x00) {
            this.card = card;
            return;
        }
        throw new CardException("Incorrect AID");
    }
   //apdu phản hồi(trả về thông báo thanh công/dữ liệu,...)
    public ResponseAPDU transmit(CommandAPDU command) throws NullPointerException, CardException {
        return card.getBasicChannel().transmit(command);
    }

    public void disconnect() throws NullPointerException, CardException {
        card.disconnect(false);
    }

    //kiểm soát việc tạo mới đối tượng
    public static BankCardManager getInstance() {
        return instance == null ? instance = new BankCardManager(): instance;
    }
}
