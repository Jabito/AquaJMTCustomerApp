package com.mapua.aquajmt.customerapp.activities;

import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mapua.aquajmt.customerapp.R;
import com.mapua.aquajmt.customerapp.api.Api;
import com.mapua.aquajmt.customerapp.api.models.CustomerInfo;
import com.mapua.aquajmt.customerapp.api.models.UpdateCustomerForm;
import com.mapua.aquajmt.customerapp.api.retrofit.RetroFitApiImpl;
import com.mapua.aquajmt.customerapp.sqlite.LoginDbHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditProfileActivity extends AppCompatActivity {

    @BindView(R.id.txt_username) TextView txtUsername;
    @BindView(R.id.txt_email) TextView txtEmail;
    @BindView(R.id.txt_first_name) EditText txtFirstName;
    @BindView(R.id.txt_middle_name) EditText txtMiddleName;
    @BindView(R.id.txt_last_name) EditText txtLastName;
    @BindView(R.id.txt_cellphone_no) EditText txtCellphoneNo;
    @BindView(R.id.txt_landline) EditText txtLandline;
    @BindView(R.id.btn_update_profile)
    Button updateProfile;

    private RetroFitApiImpl retroFitApi;
    private CustomerInfo customerInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Edit Profile");
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        retroFitApi = new RetroFitApiImpl(Api.API_ENDPOINT);
        customerInfo = LoginDbHelper.getTop1(this);
        if (customerInfo == null)
            throw new AssertionError("This activity should have not been accessible.");

        setStuff();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                finish();
                            case DialogInterface.BUTTON_NEGATIVE:
                                dialog.dismiss();
                                break;
                        }
                    }
                };
                new AlertDialog.Builder(this)
                        .setMessage("Are you sure you would like to cancel editing your profile?")
                        .setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener)
                        .show();
                return true;
            case R.id.btn_save_changes:
                UpdateCustomerForm updateCustomerForm = new UpdateCustomerForm();
                updateCustomerForm.setId(customerInfo.getId());
                updateCustomerForm.setFirstName(txtFirstName.getText().toString());
                updateCustomerForm.setMiddleName(txtMiddleName.getText().toString());
                updateCustomerForm.setLastName(txtLastName.getText().toString());
                updateCustomerForm.setCellphoneNo(txtCellphoneNo.getText().toString());
                updateCustomerForm.setLandline(txtLandline.getText().toString());

                retroFitApi.updateCustomer(updateCustomerForm, new Api.UpdateCustomerListener() {
                    @Override
                    public void success(CustomerInfo customerInfo) {
                        Toast.makeText(EditProfileActivity.this, "Successfully updated " +
                                "your profile.", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void error() {
                        Toast.makeText(EditProfileActivity.this, "An error occurred in saving " +
                                "changes.", Toast.LENGTH_SHORT).show();
                    }
                });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setStuff() {
        txtUsername.setText(customerInfo.getUsername());
        txtEmail.setText(customerInfo.getEmail());
        txtFirstName.setText(customerInfo.getFirstName());
        txtMiddleName.setText(customerInfo.getMiddleName());
        txtLastName.setText(customerInfo.getLastName());
        txtCellphoneNo.setText(customerInfo.getCellphoneNo());
        txtLandline.setText(customerInfo.getLandline());
    }
}
