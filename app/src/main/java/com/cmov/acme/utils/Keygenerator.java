package com.cmov.acme.utils;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Created by mauro on 31/10/2017.
 */

public class Keygenerator {

    private String publicKey;
    private String privateKey;

    public Keygenerator() {
        KeyPairGenerator kgen = null;  //RSA keys
        PrivateKey pri = null;                             // private key in a Java class
        PublicKey pub = null;
        try {
            kgen = KeyPairGenerator.getInstance("RSA");
            kgen.initialize(368);
            KeyPair kp = kgen.generateKeyPair();
            pri = kp.getPrivate();                             // private key in a Java class
            pub = kp.getPublic();//size in bits
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        StringBuilder str = new StringBuilder("-----BEGIN PUBLIC KEY-----\n");
        str.append(new String(Base64.encode(pub.getEncoded(), Base64.DEFAULT)));
        str.append("-----END PUBLIC KEY-----\n");

        publicKey = null;
        try {
            publicKey = new String(str.toString().getBytes(), "UTF_8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        StringBuilder strprivate = new StringBuilder("-----BEGIN PRIVATE RSA KEY-----\n");
        strprivate.append(new String(Base64.encode(pri.getEncoded(), Base64.DEFAULT)));
        strprivate.append("-----END PRIVATE RSA KEY-----\n");

        privateKey = null;
        try {
            privateKey = new String(str.toString().getBytes(), "UTF_8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }
}
