package com.cmov.acme.singletons;

/**
 * Created by mauro on 26/10/2017.
 */

public class User {
    private static User instance = null;
    private String token;
    protected User() {
        // Exists only to defeat instantiation.
    }
    public static User getInstance() {
        if(instance == null) {
            instance = new User();
        }
        return instance;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
