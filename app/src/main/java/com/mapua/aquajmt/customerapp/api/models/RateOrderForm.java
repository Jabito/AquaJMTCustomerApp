package com.mapua.aquajmt.customerapp.api.models;

/**
 * Created by Bryan on 6/13/2017.
 */

public class RateOrderForm {

    private String orderId;
    private String shopId;
    private float rating;
    private String comments;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
