package com.mapua.aquajmt.customerapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.mapua.aquajmt.customerapp.R;
import com.mapua.aquajmt.customerapp.models.ShopInfo;

public class ShopInfoActivity extends AppCompatActivity {

    private ShopInfo shopInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_info);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        // TODO: call backend to retrieve the shop info.
        // then, at the callback, call ShopInfoActivity#setShopInfo
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(RESULT_OK);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }

    private void setShopInfo(ShopInfo shopInfo) {
        this.shopInfo = shopInfo;
    }
}
