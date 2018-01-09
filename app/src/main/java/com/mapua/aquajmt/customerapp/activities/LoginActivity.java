package com.mapua.aquajmt.customerapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.mapua.aquajmt.customerapp.R;
import com.mapua.aquajmt.customerapp.api.Api;
import com.mapua.aquajmt.customerapp.api.models.CustomerInfo;
import com.mapua.aquajmt.customerapp.api.retrofit.RetroFitApiImpl;
import com.mapua.aquajmt.customerapp.models.Notification;
import com.mapua.aquajmt.customerapp.sqlite.LoginDbHelper;
import com.mapua.aquajmt.customerapp.utils.Constant;
import com.mapua.aquajmt.customerapp.utils.CustomDialog;
import com.mapua.aquajmt.customerapp.utils.SharedPref;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.txt_username) EditText txtUsername;
    @BindView(R.id.txt_password) EditText txtPassword;
    @BindView(R.id.lbl_acct_inaccessible)
    TextView txtAccountInaccessible;
    CustomDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        dialog = new CustomDialog();
        Log.d("TOKEN", FirebaseInstanceId.getInstance().getToken());

    }


    @OnClick(R.id.btn_login)
    public void login() {
        RetroFitApiImpl retroFitApi = new RetroFitApiImpl(Api.API_ENDPOINT);
        retroFitApi.login(txtUsername.getText().toString(),
                txtPassword.getText().toString().trim(),
                new Api.LoginListener() {
                    @Override
                    public void success(CustomerInfo customerInfo) {
                        SharedPref.setStringValue(SharedPref.USER,SharedPref.USER_ID,customerInfo.getId(),LoginActivity.this);

                        LoginDbHelper.save(LoginActivity.this, customerInfo);
                        Intent intent = new Intent(LoginActivity.this, MapsActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void incorrectOrNotFound() {
                        Toast.makeText(LoginActivity.this, "Username and password " +
                                "does not match.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void error() {
                        Toast.makeText(LoginActivity.this, "An error occurred.",
                                Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @OnClick(R.id.btn_signup)
    public void signUp() {
        dialog.showTermsAndCondition(LoginActivity.this, new Api.TermsAndCondition() {
            @Override
            public void accept() {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void cancel() {

            }
        });
    }

    @OnClick(R.id.lbl_acct_inaccessible)
    public void inaccessibleAcountOnClick(){

    }

}
