package com.mapua.aquajmt.customerapp.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.mapua.aquajmt.customerapp.R;
import com.mapua.aquajmt.customerapp.api.Api;
import com.mapua.aquajmt.customerapp.api.models.RateOrderForm;
import com.mapua.aquajmt.customerapp.api.retrofit.RetroFitApiImpl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RateOrderFragment extends DialogFragment {

    private static final String STORE_ID_PARAM = "store_id_param";
    private static final String ORDER_ID_PARAM = "order_id_param";

    public interface RateOrderFragmentListener { }

    @BindView(R.id.img_first_star) ImageView imgFirstStar;
    @BindView(R.id.img_second_star) ImageView imgSecondStar;
    @BindView(R.id.img_third_star) ImageView imgThirdStar;
    @BindView(R.id.img_fourth_star) ImageView imgFourthStar;
    @BindView(R.id.img_fifth_star) ImageView imgFifthStar;

    private RateOrderFragmentListener mListener;
    private int rating;
    private String storeId;
    private String orderId;

    public static RateOrderFragment newInstance(String storeId, String orderId) {
        RateOrderFragment rateOrderFragment = new RateOrderFragment();
        Bundle args = new Bundle();
        args.putString(STORE_ID_PARAM, storeId);
        args.putString(ORDER_ID_PARAM, orderId);
        rateOrderFragment.setArguments(args);
        return rateOrderFragment;
    }

    public RateOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            storeId = getArguments().getString(STORE_ID_PARAM);
            orderId = getArguments().getString(ORDER_ID_PARAM);
        }

        if (storeId == null || orderId == null) {
            throw new IllegalArgumentException("The storeId and the orderId " +
                    "of the RateOrderFragment cannot be equal to null.");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_rate_order, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RateOrderFragmentListener) {
            mListener = (RateOrderFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement RateOrderFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onStart() {
        super.onStart();

        rating = 0;
        updateViewStars();
    }

    @OnClick(R.id.img_first_star)
    public void firstStarClick() {
        rating = 1;
        updateViewStars();
    }

    @OnClick(R.id.img_second_star)
    public void secondStarClick() {
        rating = 2;
        updateViewStars();
    }

    @OnClick(R.id.img_third_star)
    public void thirdStarClick() {
        rating = 3;
        updateViewStars();
    }

    @OnClick(R.id.img_fourth_star)
    public void fourthStarClick() {
        rating = 4;
        updateViewStars();
    }

    @OnClick(R.id.img_fifth_star)
    public void fifthStarClick() {
        rating = 5;
        updateViewStars();
    }

    @OnClick(R.id.btn_ok)
    public void ok() {
        RateOrderFragment.this.dismiss();
        RetroFitApiImpl retroFitApi = new RetroFitApiImpl(Api.API_ENDPOINT);

        RateOrderForm rateOrderForm = new RateOrderForm();
        rateOrderForm.setShopId(storeId);
        rateOrderForm.setOrderId(orderId);
        retroFitApi.rateOrder(rateOrderForm, new Api.RateOrderListener() {
            @Override
            public void success() {
                Toast.makeText(getContext(), "The order you selected has been rated " +
                        "successfully.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void error() {
                Toast.makeText(getContext(), "An error occured while " +
                        "trying to rate the order you selected.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateViewStars() {
        updateStarImageView(imgFirstStar, 1);
        updateStarImageView(imgSecondStar, 2);
        updateStarImageView(imgThirdStar, 3);
        updateStarImageView(imgFourthStar, 4);
        updateStarImageView(imgFifthStar, 5);
    }

    private void updateStarImageView(ImageView imageView, int limit) {
        if (rating >= limit)
            imageView.setImageResource(R.drawable.ic_star_black);
        else
            imageView.setImageResource(R.drawable.ic_star_border_black);
    }
}
