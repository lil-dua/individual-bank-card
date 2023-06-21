/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bankcard.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author admin
 */
public class BankCard {
    private String numberCard;
    private String nameCard;
    private long numberBalance;
    private Date createDate;
    private Date expirationDate;
    private byte[] imageAvatar;

    public BankCard() {
    }

    public BankCard(String numberCard, String nameCard, long numberBalance, Date createDate, Date expirationDate) {
        this.numberCard = numberCard;
        this.nameCard = nameCard;
        this.numberBalance = numberBalance;
        this.createDate = createDate;
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return "BankCard{" + "numberCard=" + numberCard + ", nameCard=" + nameCard + ", numberBalance=" + numberBalance + ", createDate=" + createDate + ", expirationDate=" + expirationDate + ", imageAvatar=" + imageAvatar + '}';
    }

    
    public String getNumberCard() {
        return numberCard;
    }

    public void setNumberCard(String numberCard) {
        this.numberCard = numberCard;
    }

    public String getNameCard() {
        return nameCard;
    }

    public void setNameCard(String nameCard) {
        this.nameCard = nameCard;
    }

    public long getNumberBalance() {
        return numberBalance;
    }

    public void setNumberBalance(long numberBalance) {
        this.numberBalance = numberBalance;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public byte[] getImageAvatar() {
        return imageAvatar;
    }

    public void setImageAvatar(byte[] imageAvatar) {
        this.imageAvatar = imageAvatar;
    }
    public byte[] formatDataBankCard(){
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String createDateFormat = dateFormat.format(createDate);
        String expirationDateFormat = dateFormat.format(expirationDate);
        String numberCardFomat =  String.valueOf(this.numberCard);
        String numberBalanceFormat = String.valueOf(this.numberBalance);
        return ((char)numberCardFomat.length() + numberCardFomat
                +(char)nameCard.length() + nameCard
                +(char)createDateFormat.length()+createDateFormat
                +(char)expirationDateFormat.length()+expirationDateFormat
                +(char)numberBalanceFormat.length()+numberBalanceFormat).getBytes();
        //data[] =  {43245 11111 13hoanghuuthom 10 05/29/2023.....}
    }
}
