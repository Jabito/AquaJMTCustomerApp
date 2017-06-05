package com.mapua.aquajmt.customerapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.mapua.aquajmt.customerapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.txt_username) EditText txtUsername;
    @BindView(R.id.txt_password) EditText txtPassword;
    @BindView(R.id.txt_email) EditText txtEmail;
    @BindView(R.id.txt_full_name) EditText txtFullName;
    @BindView(R.id.txt_contact_no) EditText txtContactNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_signup)
    public void signUp() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);

        finish();
    }
}
