package com.mapua.aquajmt.customerapp.models;

/**
 * Created by medicardphilippines on 05/06/2017.
 */

public class PaymentHistory {
    public String dayTime;
    public String orderDetails;
    public String transactionAmount;

    public PaymentHistory(String dayTime, String orderDetails, String transactionAmount) {
        this.dayTime = dayTime;
        this.orderDetails = orderDetails;
        this.transactionAmount = transactionAmount;
    }
}
