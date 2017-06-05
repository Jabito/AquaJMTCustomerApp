package com.mapua.aquajmt.customerapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mapua.aquajmt.customerapp.R;
import com.mapua.aquajmt.customerapp.models.PaymentHistory;

import java.util.ArrayList;

/**
 * Created by medicardphilippines on 05/06/2017.
 */

public class PaymentHistoryAdapter extends ArrayAdapter<PaymentHistory> {

    public PaymentHistoryAdapter(Context context, ArrayList<PaymentHistory> paymentHistories) {
        super(context, 0, paymentHistories);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        PaymentHistory paymentHistory = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_payment_history, parent, false);
        }

        TextView txtDayTime = (TextView) convertView.findViewById(R.id.txt_day_time);
        TextView txtOrderDetails = (TextView) convertView.findViewById(R.id.txt_order_details);
        TextView txtPrice = (TextView) convertView.findViewById(R.id.txt_price);

        txtDayTime.setText(paymentHistory.dayTime);
        txtOrderDetails.setText(paymentHistory.orderDetails);
        txtPrice.setText(paymentHistory.transactionAmount);

        return convertView;
    }
}
