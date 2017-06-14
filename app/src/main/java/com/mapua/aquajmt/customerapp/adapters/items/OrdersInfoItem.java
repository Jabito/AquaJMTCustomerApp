package com.mapua.aquajmt.customerapp.adapters.items;

import com.mapua.aquajmt.customerapp.adapters.OrdersAdapter;
import com.mapua.aquajmt.customerapp.api.models.OrderInfo;

/**
 * Created by Bryan on 6/14/2017.
 */

public class OrdersInfoItem implements OrdersAdapter.OrdersArrayAdapterItem {
    private OrderInfo orderInfo;

    public OrdersInfoItem(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }
}
