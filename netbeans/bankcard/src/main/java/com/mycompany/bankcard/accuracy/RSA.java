/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bankcard.accuracy;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 *
 * @author admin
 */
public class RSA {
    public static PublicKey generatePublicKey(byte[] exponentBytes, byte[] modulusBytes) {
        BigInteger exponent = new BigInteger(1, exponentBytes);
        BigInteger modulus = new BigInteger(1, modulusBytes);

        try {
            RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(modulus, exponent);
            KeyFactory factory = KeyFactory.getInstance("RSA");

            return factory.generatePublic(publicKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            return null;
        }
    }

    public static PublicKey generatePublicKey(byte[] data) {
        try {
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(data);
            KeyFactory factory = KeyFactory.getInstance("RSA");

            return factory.generatePublic(x509EncodedKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            return null;
        }
    }

    public static boolean accuracy(byte[] signature, PublicKey publicKey, String code) {
        try {
            Signature verifier = Signature.getInstance("SHA1withRSA");
            verifier.initVerify(publicKey);
            verifier.update(code.getBytes());
            return verifier.verify(signature);
        } catch (NoSuchAlgorithmException | SignatureException | InvalidKeyException e) {
            return false;
        }
    }
}
