package com.cmov.acme.singletons;

import java.security.KeyPair;

/**
 * Created by mauro on 26/10/2017.
 */

public class User {
    private static User instance = null;
    private String token;
    private String privateKey;
    private String publicKey;

    protected User() {
        // Exists only to defeat instantiation.
    }
    public static User getInstance() {
        if(instance == null) {
            instance = new User();
        }
        return instance;
    }

    public void deleteInstance(){
        instance=null;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setKeyPair(String privateKey, String publicKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }
}
