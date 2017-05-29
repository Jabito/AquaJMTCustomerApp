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
import com.mapua.aquajmt.customerapp.models.StoreInfo;

public class StoreInfoFragment extends Fragment implements View.OnClickListener {

    private StoreInfoFragmentListener mListener;

    private TextView txtName;
    private TextView txtDescription;
    private TextView txtAddress;
    private TextView txtRating;
    private ImageButton btnPreviousStore;
    private ImageButton btnNextStore;

    public StoreInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store_info, container, false);

        txtName = (TextView) view.findViewById(R.id.txt_name);
        txtDescription = (TextView) view.findViewById(R.id.txt_description);
        txtAddress = (TextView) view.findViewById(R.id.txt_address);
        txtRating = (TextView) view.findViewById(R.id.txt_rating);

        btnPreviousStore = (ImageButton) view.findViewById(R.id.btn_prev_store);
        btnNextStore = (ImageButton) view.findViewById(R.id.btn_next_store);
        Button btnViewMore = (Button) view.findViewById(R.id.btn_view_more);
        Button btnOrder = (Button) view.findViewById(R.id.btn_order);

        btnPreviousStore.setOnClickListener(this);
        btnNextStore.setOnClickListener(this);
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
            case R.id.btn_prev_store:
                mListener.goToPreviousStore();
                break;
            case R.id.btn_next_store:
                mListener.goToNextStore();
                break;
            case R.id.btn_view_more:
                mListener.viewAllStoreInfo();
                break;
            case R.id.btn_order:
                mListener.orderFromStore();
                break;
        }
    }

    public void setIsList(boolean isList) {
        btnNextStore.setVisibility(isList ? View.VISIBLE : View.GONE);
        btnPreviousStore.setVisibility(isList ? View.VISIBLE : View.GONE);
    }

    public void setStoreInView(StoreInfo storeInfo) {
        txtName.setText(storeInfo.getName());
        txtDescription.setText(storeInfo.getDescription());
        txtAddress.setText(storeInfo.getAddress());
        txtRating.setText(String.valueOf(storeInfo.getRating()));
    }

    public interface StoreInfoFragmentListener {
        void goToPreviousStore();
        void goToNextStore();
        void orderFromStore();
        void viewAllStoreInfo();
    }
}
