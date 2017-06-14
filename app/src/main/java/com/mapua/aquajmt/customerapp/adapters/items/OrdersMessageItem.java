package com.mapua.aquajmt.customerapp.adapters.items;

import com.mapua.aquajmt.customerapp.adapters.OrdersAdapter;

/**
 * Created by Bryan on 6/14/2017.
 */
public class OrdersMessageItem implements OrdersAdapter.OrdersArrayAdapterItem {
    private String message;

    public OrdersMessageItem(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
