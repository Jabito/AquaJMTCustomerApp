package com.mapua.aquajmt.customerapp.api.models;

import com.google.android.gms.maps.model.LatLng;

/**
 * Request object for `OrderController#addOrderInfo` = "/api/addOrderInfo"
 */
public class OrderForm {

    public final static String ALKALINE_WATER_TYPE = "ALKALINE";
    public final static String DISTILLED_WATER_TYPE = "DISTILLED";
    public final static String PURIFIED_WATER_TYPE = "PURIFIED";

    private final String storeId; // orderedFrom
    private final String customerId; // orderedBy

    private final LatLng deliveryLocation; // longitude, latitude
    private final String deliveryAddress; // customerAddress
    private final String deliveryDetails; // moreDetails

    private final String waterType; // as is
    private final int slimOrdered; // as is
    private final int roundOrdered; // as is

    public OrderForm(String storeId,
                     String customerId,
                     LatLng deliveryLocation,
                     String deliveryAddress,
                     String deliveryDetails,
                     String waterType,
                     int slimOrdered,
                     int roundOrdered) {

        if (storeId == null || customerId == null || deliveryLocation == null
                || deliveryAddress == null || deliveryAddress.isEmpty()
                || deliveryDetails == null || waterType == null || waterType.isEmpty()) {
            throw new IllegalArgumentException("Properties of this object is not allowed " +
                    "to contain null values.");
        }

        this.storeId = storeId;
        this.customerId = customerId;
        this.deliveryLocation = deliveryLocation;
        this.deliveryAddress = deliveryAddress;
        this.deliveryDetails = deliveryDetails;
        this.waterType = waterType;
        this.slimOrdered = slimOrdered;
        this.roundOrdered = roundOrdered;
    }

    public String getStoreId() {
        return storeId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public LatLng getDeliveryLocation() {
        return deliveryLocation;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public String getDeliveryDetails() {
        return deliveryDetails;
    }

    public String getWaterType() {
        return waterType;
    }

    public int getSlimOrdered() {
        return slimOrdered;
    }

    public int getRoundOrdered() {
        return roundOrdered;
    }
}
