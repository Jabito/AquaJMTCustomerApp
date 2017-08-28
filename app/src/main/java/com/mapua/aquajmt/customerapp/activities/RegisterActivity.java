package com.mapua.aquajmt.customerapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.mapua.aquajmt.customerapp.R;
import com.mapua.aquajmt.customerapp.api.Api;
import com.mapua.aquajmt.customerapp.api.models.CustomerInfo;
import com.mapua.aquajmt.customerapp.api.models.RegisterForm;
import com.mapua.aquajmt.customerapp.api.retrofit.RetroFitApiImpl;
import com.mapua.aquajmt.customerapp.sqlite.LoginDbHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.txt_username) EditText txtUsername;
    @BindView(R.id.txt_password) EditText txtPassword;
    @BindView(R.id.txt_email) EditText txtEmail;
    @BindView(R.id.txt_first_name) EditText txtFirstName;
    @BindView(R.id.txt_last_name) EditText txtLastName;
    @BindView(R.id.txt_contact_no) EditText txtContactNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_signup)
    public void signUp() {
        RegisterForm registerForm = new RegisterForm();
        registerForm.setUsername(txtUsername.getText().toString());
        registerForm.setPassword(txtPassword.getText().toString().trim());
        registerForm.setEmail(txtEmail.getText().toString());
        registerForm.setFirstName(txtFirstName.getText().toString());
        registerForm.setLastName(txtLastName.getText().toString());
        registerForm.setCellphoneNo(txtContactNo.getText().toString());
        registerForm.setMiddleName("");
        registerForm.setLandline("");

        RetroFitApiImpl retroFitApi = new RetroFitApiImpl(Api.API_ENDPOINT);
        retroFitApi.register(registerForm, new Api.RegisterListener() {
            @Override
            public void success(CustomerInfo customerInfo) {
                LoginDbHelper.save(RegisterActivity.this, customerInfo);

                Intent intent = new Intent(RegisterActivity.this, MapsActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void usernameOrEmailAlreadyTaken() {
                Toast.makeText(RegisterActivity.this, "The username or the email you " +
                        "provided has already been taken.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void error() {
                Toast.makeText(RegisterActivity.this, "An error occurred.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
