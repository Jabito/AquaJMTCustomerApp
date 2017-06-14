package com.mapua.aquajmt.customerapp.adapters.items;

import com.mapua.aquajmt.customerapp.adapters.OrdersAdapter;

/**
 * Created by Bryan on 6/14/2017.
 */
public class OrdersLabelItem implements OrdersAdapter.OrdersArrayAdapterItem {
    private String label;

    public OrdersLabelItem(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
