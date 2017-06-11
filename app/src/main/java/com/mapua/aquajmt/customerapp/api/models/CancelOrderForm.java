package com.mapua.aquajmt.customerapp.api.models;

/**
 * Created by Bryan on 6/11/2017.
 */

public class CancelOrderForm {

    private final String orderId;
    private final String origin = "customer";

    public CancelOrderForm(String orderId) {
        if (orderId == null) {
            throw new IllegalArgumentException("Properties of this object is not allowed " +
                    "to contain null values.");
        }

        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getOrigin() {
        return origin;
    }
}
