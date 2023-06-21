/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bankcard.util;

/**
 *
 * @author admin
 */
public class Constans {
    public static final byte[] AID = new byte[]{0x11, 0x22, 0x33, 0x44, 0x55, 0x00};
    public static final byte INS_VERIFY = (byte) 0x00;
    public static final byte INS_CREATE=(byte) 0x01;
    public static final byte INS_GET=(byte) 0x02;
    public static final byte INS_UPDATE=(byte) 0x03;
    public static final byte P1_PIN=(byte) 0x04;
    public static final byte P1_INFORMATION=(byte) 0x05;
    public static final byte P1_SIGNATURE=(byte) 0x06;

    public static final byte P2_INFORMATION=(byte) 0x07;
    public static final byte P2_REMAINING_BALANCE=(byte) 0x08;
    public static final byte P2_AVATAR=(byte) 0x09;
    public static final byte INS_RESET_TRY_PIN=(byte) 0x10;
    public static final String CARD_HAS_BLOCKED="Card has block!";
}
