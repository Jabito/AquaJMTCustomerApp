package com.mapua.aquajmt.customerapp.api.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

/**
 * Request object for `OrderController#addOrderInfo` = "/api/addOrderInfo"
 */
public class OrderForm implements Parcelable {

    private String storeId; // orderedFrom
    private String customerId; // orderedBy
    private LatLng deliveryLocation; // longitude, latitude
    private String deliveryAddress; // customerAddress
    private String deliveryDetails; // moreDetails
    private String waterType; // as is
    private int slimOrdered; // as is
    private int roundOrdered; // as is
    private boolean isSwapping;

    public OrderForm() {
    }

    protected OrderForm(Parcel in) {
        storeId = in.readString();
        customerId = in.readString();
        deliveryLocation = in.readParcelable(LatLng.class.getClassLoader());
        deliveryAddress = in.readString();
        deliveryDetails = in.readString();
        waterType = in.readString();
        slimOrdered = in.readInt();
        roundOrdered = in.readInt();
        isSwapping = in.readByte() != 0;
    }

    public static final Creator<OrderForm> CREATOR = new Creator<OrderForm>() {
        @Override
        public OrderForm createFromParcel(Parcel in) {
            return new OrderForm(in);
        }

        @Override
        public OrderForm[] newArray(int size) {
            return new OrderForm[size];
        }
    };

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

    public boolean isSwapping() {
        return isSwapping;
    }

    public void setSwapping(boolean swapping) {
        isSwapping = swapping;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(storeId);
        dest.writeString(customerId);
        dest.writeParcelable(deliveryLocation, flags);
        dest.writeString(deliveryAddress);
        dest.writeString(deliveryDetails);
        dest.writeString(waterType);
        dest.writeInt(slimOrdered);
        dest.writeInt(roundOrdered);
        dest.writeByte((byte) (isSwapping ? 1 : 0));
    }
}
