package com.mapua.aquajmt.customerapp.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;

import com.mapua.aquajmt.customerapp.R;
import com.mapua.aquajmt.customerapp.adapters.PaymentHistoryAdapter;
import com.mapua.aquajmt.customerapp.models.PaymentHistory;

import java.util.ArrayList;

public class PaymentHistoryActivity extends AppCompatActivity {

    private PaymentHistoryAdapter paymentHistoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Payment History");
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        paymentHistoryAdapter = new PaymentHistoryAdapter(this, new ArrayList<PaymentHistory>());

        ListView lstPayments = (ListView) findViewById(R.id.lst_payments);
        lstPayments.setAdapter(paymentHistoryAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        paymentHistoryAdapter.addAll(
                new PaymentHistory("June 4 at 16:55", "Three slim containers from Aqua JMT", "PHP 75.00"),
                new PaymentHistory("May 30 at 12:33", "Five slim containers from Aqua JMT", "PHP 125.00"),
                new PaymentHistory("May 22 at 19:30", "Five slim containers from Aqua JMT", "PHP 125.00")
        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
