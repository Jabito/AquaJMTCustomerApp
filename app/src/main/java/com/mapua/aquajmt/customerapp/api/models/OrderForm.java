package com.mapua.aquajmt.customerapp.api.models;

import com.google.android.gms.maps.model.LatLng;

/**
 * Request object for `OrderController#addOrderInfo` = "/api/addOrderInfo"
 */
public class OrderForm {

    private String storeId; // orderedFrom
    private String customerId; // orderedBy

    private LatLng deliveryLocation; // longitude, latitude
    private String deliveryAddress; // customerAddress
    private String deliveryDetails; // moreDetails

    private String waterType; // as is
    private int slimOrdered; // as is
    private int roundOrdered; // as is

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public LatLng getDeliveryLocation() {
        return deliveryLocation;
    }

    public void setDeliveryLocation(LatLng deliveryLocation) {
        this.deliveryLocation = deliveryLocation;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getDeliveryDetails() {
        return deliveryDetails;
    }

    public void setDeliveryDetails(String deliveryDetails) {
        this.deliveryDetails = deliveryDetails;
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
}
