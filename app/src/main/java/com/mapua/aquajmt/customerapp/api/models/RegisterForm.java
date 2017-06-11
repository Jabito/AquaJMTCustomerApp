package com.mapua.aquajmt.customerapp.api.models;

/**
 * Created by Bryan on 6/11/2017.
 */

public class RegisterForm {

    private final String username;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final String middleName;
    private final String email;
    private final String cellphoneNo;
    private final String landline;

    public RegisterForm(String username,
                        String password,
                        String firstName,
                        String lastName,
                        String middleName,
                        String email,
                        String cellphoneNo,
                        String landline) {

        if (username == null || password == null || firstName == null
                || lastName == null || middleName == null || email == null
                || cellphoneNo == null || landline == null) {
            throw new IllegalArgumentException("Properties of this object is not allowed " +
                    "to contain null values.");
        }

        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.email = email;
        this.cellphoneNo = cellphoneNo;
        this.landline = landline;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getEmail() {
        return email;
    }

    public String getCellphoneNo() {
        return cellphoneNo;
    }

    public String getLandline() {
        return landline;
    }
}
