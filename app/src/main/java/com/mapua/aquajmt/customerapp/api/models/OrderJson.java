package com.mapua.aquajmt.customerapp.api.models;

/**
 * Created by Bryan on 6/14/2017.
 */

public class OrderJson {

    private String orderedFrom;
    private String orderedBy;
    private double longitude;
    private double latitude;
    private String moreDetails;
    private String customerAddress;
    private String waterType;
    private int slimOrdered;
    private int roundOrdered;
    private boolean isSwapping;

    public OrderJson() {
    }

    public OrderJson(OrderForm orderForm) {
        orderedFrom = orderForm.getStoreId();
        orderedBy = orderForm.getCustomerId();
        longitude = orderForm.getDeliveryLocation().longitude;
        latitude = orderForm.getDeliveryLocation().latitude;
        moreDetails = orderForm.getDeliveryDetails();
        customerAddress = orderForm.getDeliveryAddress();
        waterType = orderForm.getWaterType();
        slimOrdered = orderForm.getSlimOrdered();
        roundOrdered = orderForm.getRoundOrdered();
        isSwapping = orderForm.isSwapping();
    }

    public String getOrderedFrom() {
        return orderedFrom;
    }

    public void setOrderedFrom(String orderedFrom) {
        this.orderedFrom = orderedFrom;
    }

    public String getOrderedBy() {
        return orderedBy;
    }

    public void setOrderedBy(String orderedBy) {
        this.orderedBy = orderedBy;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getMoreDetails() {
        return moreDetails;
    }

    public void setMoreDetails(String moreDetails) {
        this.moreDetails = moreDetails;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getWaterType() {
        return waterType;
    }

    public void setWaterType(String waterType) {
        this.waterType = waterType;
    }

    public int getSlimOrdered() {
        return slimOrdered;
    }

    public void setSlimOrdered(int slimOrdered) {
        this.slimOrdered = slimOrdered;
    }

    public int getRoundOrdered() {
        return roundOrdered;
    }

    public void setRoundOrdered(int roundOrdered) {
        this.roundOrdered = roundOrdered;
    }

    public boolean isSwapping() {
        return isSwapping;
    }

    public void setSwapping(boolean swapping) {
        isSwapping = swapping;
    }
}
