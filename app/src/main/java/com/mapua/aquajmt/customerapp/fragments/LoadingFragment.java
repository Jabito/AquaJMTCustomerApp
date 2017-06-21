package com.mapua.aquajmt.customerapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mapua.aquajmt.customerapp.R;

/**
 * Created by Bryan on 6/16/2017.
 */

public class LoadingFragment extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loading, container, false);
        getDialog().setCanceledOnTouchOutside(false);

        return view;
    }
}
