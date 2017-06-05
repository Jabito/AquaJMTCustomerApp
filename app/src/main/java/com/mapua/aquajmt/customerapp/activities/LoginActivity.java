package com.mapua.aquajmt.customerapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.mapua.aquajmt.customerapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.txt_username) EditText txtUsername;
    @BindView(R.id.txt_password) EditText txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_login)
    public void login() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);

        finish();
    }

    @OnClick(R.id.btn_signup)
    public void signUp() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);

        finish();
    }
}
