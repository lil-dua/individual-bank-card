/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bankcard.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import static java.lang.Character.digit;

/**
 *
 * @author admin
 */
public class BytesConfig {
    public static String formatNumberCar(String numberCard){
        String numberCardFormat="";
        for(int i=0;i<numberCard.length();i++){
            numberCardFormat+=numberCard.charAt(i);
            if(i%3==0&&i>0){
                numberCardFormat+=" ";
            }
        }
        return numberCardFormat;
    }
public static byte [] fromFile( File file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            fileInputStream.read(buffer);
            fileInputStream.close();
            return buffer;
        } catch (IOException e) {
            return null;
        }
    }
    public static int makeInteger(byte[] bytes, int offset) {
        return ((int) bytes[offset] << 8) + ((int) bytes[offset + 1] & 255);
    }

    public static byte[] copyOfRange(byte[] original, int offset, int length) {
        if (length < 0) {
            throw new IllegalArgumentException("length is negative");
        }

        byte[] result = new byte[length];

        System.arraycopy(original, offset, result, 0, Math.min(original.length - offset, length));
        return result;
    }

    public static String toHexString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        String data;

        for (byte _byte : bytes) {
            data = String.format("%02X", _byte);
            stringBuilder.append(data);
        }
        return stringBuilder.toString();
    }

    public static byte[] fromHexString( String data) {
        int length = data.length();
        byte[] result = new byte[length / 2];

        for (int i = 0; i < length; i += 2) {
            result[i / 2] = (byte) ((digit(data.charAt(i), 16) << 4) + digit(data.charAt(i + 1), 16));
        }
        return result;
    }
}
