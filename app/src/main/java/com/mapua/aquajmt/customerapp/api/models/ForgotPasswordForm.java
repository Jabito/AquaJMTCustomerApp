package com.mapua.aquajmt.customerapp.api.models;

/**
 * Created by Bryan on 6/11/2017.
 */

public class ForgotPasswordForm {

    private final String email;

    public ForgotPasswordForm(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Properties of this object is not allowed " +
                    "to contain null values.");
        }

        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
