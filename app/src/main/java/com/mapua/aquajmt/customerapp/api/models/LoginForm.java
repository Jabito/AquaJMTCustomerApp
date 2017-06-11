package com.mapua.aquajmt.customerapp.api.models;

/**
 * Created by Bryan on 6/11/2017.
 */

public class LoginForm {

    private final String username;
    private final String password;

    public LoginForm(String username, String password) {
        if (username == null || password == null) {
            throw new IllegalArgumentException("Properties of this object is not allowed " +
                    "to contain null values.");
        }

        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
