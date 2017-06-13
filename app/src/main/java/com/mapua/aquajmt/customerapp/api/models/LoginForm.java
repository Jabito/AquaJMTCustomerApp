package com.mapua.aquajmt.customerapp.api.models;

/**
 * Created by Bryan on 6/13/2017.
 */

public class LoginForm {

    private String username;
    private String password;

    public LoginForm() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
