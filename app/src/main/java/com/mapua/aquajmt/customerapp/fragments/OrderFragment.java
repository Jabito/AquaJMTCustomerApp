package com.mapua.aquajmt.customerapp.fragments;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mapua.aquajmt.customerapp.R;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderFragment extends DialogFragment {

    @BindView(R.id.txt_ordering_from) TextView txtOrderingFrom;
    @BindView(R.id.txt_price) TextView txtPrice;
    @BindView(R.id.txt_container_slim_count) EditText txtContainerSlimCount;
    @BindView(R.id.txt_container_round_count) EditText txtContainerRoundCount;
    @BindView(R.id.chk_swap_containers) CheckBox chkSwapContainers;

    private double totalPrice = 0.0;
    private double containerSlimPrice = 25.0;
    private double containerRoundPrice = 25.0;

    public OrderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        updateTotalPriceText();
    }

    @OnClick(R.id.btn_order)
    public void confirmOrder() {

    }

    @OnClick(R.id.btn_container_slim_count_dec)
    public void decrementContainerSlimCount() {
        int count = getCountFromTextView(txtContainerSlimCount);
        if (count > 0) {
            txtContainerSlimCount.setText(String.format(Locale.getDefault(), "%d", --count));
        }
        updateTotalPriceText();
    }

    @OnClick(R.id.btn_container_slim_count_inc)
    public void incrementContainerSlimCount() {
        int count = getCountFromTextView(txtContainerSlimCount);
        txtContainerSlimCount.setText(String.format(Locale.getDefault(), "%d", ++count));
        updateTotalPriceText();
    }

    @OnClick(R.id.btn_container_round_count_dec)
    public void decrementContainerRoundCount() {
        int count = getCountFromTextView(txtContainerRoundCount);
        if (count > 0) {
            txtContainerRoundCount.setText(String.format(Locale.getDefault(), "%d", --count));
        }
        updateTotalPriceText();
    }

    @OnClick(R.id.btn_container_round_count_inc)
    public void incrementContainerRoundCount() {
        int count = getCountFromTextView(txtContainerRoundCount);
        txtContainerRoundCount.setText(String.format(Locale.getDefault(), "%d", ++count));
        updateTotalPriceText();
    }

    private void updateTotalPriceText() {
        totalPrice = getCountFromTextView(txtContainerRoundCount) * containerRoundPrice;
        totalPrice += getCountFromTextView(txtContainerSlimCount) * containerSlimPrice;

        txtPrice.setText(String.format(Locale.getDefault(), "%.2f", totalPrice));
    }

    private int getCountFromTextView(EditText editText) {
        int count;
        try {
            count = Integer.parseInt(editText.getText().toString());
        } catch (NumberFormatException nfe) {
            count = 0;
        }
        return count;
    }
}
