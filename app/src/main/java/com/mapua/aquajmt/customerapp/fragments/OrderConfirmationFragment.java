package com.mapua.aquajmt.customerapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mapua.aquajmt.customerapp.R;
import com.mapua.aquajmt.customerapp.api.models.OrderForm;

import butterknife.ButterKnife;

public class OrderConfirmationFragment extends DialogFragment {

    public interface OrderConfirmationFragmentListener {
        void orderSucceeded();
        void orderFailed();
    }

    private static final String ORDER_FORM_PARAM = "order_form_param";

    private OrderConfirmationFragmentListener mListener;
    private OrderForm orderForm;

    public OrderConfirmationFragment() {
        // Required empty public constructor
    }

    public static OrderConfirmationFragment newInstance(OrderForm orderForm) {
        OrderConfirmationFragment fragment = new OrderConfirmationFragment();
        Bundle args = new Bundle();
        args.putParcelable(ORDER_FORM_PARAM, orderForm);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            orderForm = getArguments().getParcelable(ORDER_FORM_PARAM);

            if (orderForm == null)
                throw new IllegalStateException("The orderForm object cannot be null.");
        } else
            throw new IllegalArgumentException("No arguments supplied to this fragment.");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_confirmation, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OrderConfirmationFragmentListener) {
            mListener = (OrderConfirmationFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OrderConfirmationFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
