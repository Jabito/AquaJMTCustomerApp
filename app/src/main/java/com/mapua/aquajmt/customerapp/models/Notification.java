package com.mapua.aquajmt.customerapp.models;

/**
 * Created by JHAYCO on 1/7/2018.
 */

public class Notification {

    private String title;
    private String details;
    private String custId;
    private String shopId;

    public Notification() {
    }

    public Notification(String title, String details, String custId, String shopId) {
        this.title = title;
        this.details = details;
        this.custId = custId;
        this.shopId = shopId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
}
