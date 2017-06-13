package com.mapua.aquajmt.customerapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;
import java.util.IllegalFormatException;

public class ShopInfo implements Parcelable {

    private String id;
    private String businessName;
    private String address;
    private LatLng location;
    private String cellphoneNo;
    private String alternateNo;
    private Date timeOpen;
    private Date timeClose;
    private boolean allowSwap;
    private boolean accountVerified;
    private String daysAvailable;
    private boolean openOnHolidays;
    private double rating;
    private Date createdOn;
    private Date updatedOn;
    private String updatedBy;

    public ShopInfo() {
    }

//    public ShopInfo(String id,
//                    String businessName,
//                    String address,
//                    LatLng location, // longitude:Float, latitude:Float
//                    String cellphoneNo,
//                    String alternateNo,
//                    Date timeOpen,
//                    Date timeClose,
//                    boolean allowSwap,
//                    boolean accountVerified,
//                    String daysAvailable,
//                    boolean openOnHolidays,
//                    double rating,
//                    Date createdOn,
//                    Date updatedOn,
//                    String updatedBy) {
//
//        if (id == null || businessName == null || address == null || location == null
//                || cellphoneNo == null || alternateNo == null || timeOpen == null
//                || timeClose == null || daysAvailable == null || createdOn == null
//                || updatedOn == null || updatedBy == null || daysAvailable.length() != 7) {
//            throw new IllegalArgumentException("Properties of this object is not allowed " +
//                    "to contain null values.");
//        }
//
//        this.id = id;
//        this.businessName = businessName;
//        this.cellphoneNo = cellphoneNo;
//        this.address = address;
//        this.location = location;
//        this.alternateNo = alternateNo;
//        this.timeOpen = timeOpen;
//        this.timeClose = timeClose;
//        this.allowSwap = allowSwap;
//        this.accountVerified = accountVerified;
//        this.daysAvailable = daysAvailable;
//        this.openOnHolidays = openOnHolidays;
//        this.rating = rating;
//        this.createdOn = createdOn;
//        this.updatedOn = updatedOn;
//        this.updatedBy = updatedBy;
//    }

    protected ShopInfo(Parcel in) {
        id = in.readString();
        businessName = in.readString();
        address = in.readString();
        location = in.readParcelable(LatLng.class.getClassLoader());
        cellphoneNo = in.readString();
        alternateNo = in.readString();
        allowSwap = in.readByte() != 0;
        accountVerified = in.readByte() != 0;
        daysAvailable = in.readString();
        openOnHolidays = in.readByte() != 0;
        rating = in.readDouble();
        updatedBy = in.readString();
    }

    public static final Creator<ShopInfo> CREATOR = new Creator<ShopInfo>() {
        @Override
        public ShopInfo createFromParcel(Parcel in) {
            return new ShopInfo(in);
        }

        @Override
        public ShopInfo[] newArray(int size) {
            return new ShopInfo[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public String getCellphoneNo() {
        return cellphoneNo;
    }

    public void setCellphoneNo(String cellphoneNo) {
        this.cellphoneNo = cellphoneNo;
    }

    public String getAlternateNo() {
        return alternateNo;
    }

    public void setAlternateNo(String alternateNo) {
        this.alternateNo = alternateNo;
    }

    public Date getTimeOpen() {
        return timeOpen;
    }

    public void setTimeOpen(Date timeOpen) {
        this.timeOpen = timeOpen;
    }

    public Date getTimeClose() {
        return timeClose;
    }

    public void setTimeClose(Date timeClose) {
        this.timeClose = timeClose;
    }

    public boolean isAllowSwap() {
        return allowSwap;
    }

    public void setAllowSwap(boolean allowSwap) {
        this.allowSwap = allowSwap;
    }

    public boolean isAccountVerified() {
        return accountVerified;
    }

    public void setAccountVerified(boolean accountVerified) {
        this.accountVerified = accountVerified;
    }

    public String getDaysAvailable() {
        return daysAvailable;
    }

    public void setDaysAvailable(String daysAvailable) {
        this.daysAvailable = daysAvailable;
    }

    public boolean isOpenOnHolidays() {
        return openOnHolidays;
    }

    public void setOpenOnHolidays(boolean openOnHolidays) {
        this.openOnHolidays = openOnHolidays;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String[] getDaysAvailableInStringArray() {
        if (daysAvailable.length() != 7) {
            throw new IllegalStateException("This string's length should be equal to 7, " +
                    "otherwise, continuing would cause further errors.");
        }

        ArrayList<String> daysAvailableList = new ArrayList<>();
        if (daysAvailable.charAt(0) == '1') daysAvailableList.add("Sunday");
        if (daysAvailable.charAt(1) == '1') daysAvailableList.add("Monday");
        if (daysAvailable.charAt(2) == '1') daysAvailableList.add("Tuesday");
        if (daysAvailable.charAt(3) == '1') daysAvailableList.add("Wednesday");
        if (daysAvailable.charAt(4) == '1') daysAvailableList.add("Thursday");
        if (daysAvailable.charAt(5) == '1') daysAvailableList.add("Friday");
        if (daysAvailable.charAt(6) == '1') daysAvailableList.add("Saturday");

        return daysAvailableList.toArray(new String[7]);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(businessName);
        dest.writeString(address);
        dest.writeParcelable(location, flags);
        dest.writeString(cellphoneNo);
        dest.writeString(alternateNo);
        dest.writeByte((byte) (allowSwap ? 1 : 0));
        dest.writeByte((byte) (accountVerified ? 1 : 0));
        dest.writeString(daysAvailable);
        dest.writeByte((byte) (openOnHolidays ? 1 : 0));
        dest.writeDouble(rating);
        dest.writeString(updatedBy);
    }
}
