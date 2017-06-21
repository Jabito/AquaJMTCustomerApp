package com.mapua.aquajmt.customerapp.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.mapua.aquajmt.customerapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LocationExplActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_expl);

        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        LocationManager locationManager = (LocationManager) getApplicationContext()
                .getSystemService(Context.LOCATION_SERVICE);

        boolean gpsEnabled = locationManager.getProvider(LocationManager.GPS_PROVIDER) != null
                && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean networkEnabled = locationManager.getProvider(LocationManager.NETWORK_PROVIDER) != null
                && locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (gpsEnabled || networkEnabled) {
            finish(); // go back to maps activity
        }
    }

    @OnClick(R.id.btn_enable_location)
    void onClickEnableLocation() {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
    }
}
