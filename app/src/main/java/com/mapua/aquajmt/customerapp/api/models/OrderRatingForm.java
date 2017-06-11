package com.mapua.aquajmt.customerapp.api.models;

/**
 * Created by Bryan on 6/11/2017.
 */

public class OrderRatingForm {

    private final String orderId;
    private final int rating;
    private final String comments;

    public OrderRatingForm(String orderId, int rating, String comments) {
        this.orderId = orderId;
        this.rating = rating;
        this.comments = comments;
    }

    public String getOrderId() {
        return orderId;
    }

    public int getRating() {
        return rating;
    }

    public String getComments() {
        return comments;
    }
}
