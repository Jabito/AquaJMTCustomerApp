package com.mapua.aquajmt.customerapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mapua.aquajmt.customerapp.R;
import com.mapua.aquajmt.customerapp.models.ShopInfo;

public class ShopInfoFragment extends Fragment implements View.OnClickListener {

    private StoreInfoFragmentListener mListener;

    private TextView txtName;
    private TextView txtAddress;

    public ShopInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store_info, container, false);

        txtName = (TextView) view.findViewById(R.id.txt_name);
        txtAddress = (TextView) view.findViewById(R.id.txt_address);

        Button btnViewMore = (Button) view.findViewById(R.id.btn_view_more);
        Button btnOrder = (Button) view.findViewById(R.id.btn_order);

        btnViewMore.setOnClickListener(this);
        btnOrder.setOnClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof StoreInfoFragmentListener) {
            mListener = (StoreInfoFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement StoreInfoFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_view_more:
                mListener.viewAllStoreInfo();
                break;
            case R.id.btn_order:
                mListener.orderFromStore();
                break;
        }
    }

    public void setStoreInView(ShopInfo shopInfo) {
        txtName.setText(shopInfo.getBusinessName());
        txtAddress.setText(shopInfo.getAddress());
    }

    public interface StoreInfoFragmentListener {
        void orderFromStore();
        void viewAllStoreInfo();
    }
}
