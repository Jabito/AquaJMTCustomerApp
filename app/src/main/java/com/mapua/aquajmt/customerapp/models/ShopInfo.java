package com.mapua.aquajmt.customerapp.models;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;
import java.util.IllegalFormatException;

public class ShopInfo {

    private String id;
    private String businessName;
    private String address;
    private LatLng location;
    private String cellphoneNo;
    private String alternateNo;
    private Date timeOpen;
    private Date timeClose;
    private Boolean allowSwap;
    private Boolean accountVerified;
    private String daysAvailable;
    private Boolean openOnHolidays;
    private Date createdOn;
    private Date updatedOn;
    private String updatedBy;

    public ShopInfo(String id,
                    String businessName,
                    String address,
                    LatLng location, // longitude:Float, latitude:Float
                    String cellphoneNo,
                    String alternateNo,
                    Date timeOpen,
                    Date timeClose,
                    Boolean allowSwap,
                    Boolean accountVerified,
                    String daysAvailable,
                    Boolean openOnHolidays,
                    Date createdOn,
                    Date updatedOn,
                    String updatedBy) {

        if (id == null || businessName == null || address == null || location == null
                || cellphoneNo == null || alternateNo == null || timeOpen == null
                || timeClose == null || allowSwap == null || accountVerified == null
                || daysAvailable == null || openOnHolidays == null || createdOn == null
                || updatedOn == null || updatedBy == null || daysAvailable.length() != 7) {
            throw new IllegalArgumentException("Properties of this object is not allowed " +
                    "to contain null values.");
        }

        this.id = id;
        this.businessName = businessName;
        this.cellphoneNo = cellphoneNo;
        this.address = address;
        this.location = location;
        this.alternateNo = alternateNo;
        this.timeOpen = timeOpen;
        this.timeClose = timeClose;
        this.allowSwap = allowSwap;
        this.accountVerified = accountVerified;
        this.daysAvailable = daysAvailable;
        this.openOnHolidays = openOnHolidays;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
        this.updatedBy = updatedBy;
    }

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

    public Boolean getAllowSwap() {
        return allowSwap;
    }

    public void setAllowSwap(Boolean allowSwap) {
        this.allowSwap = allowSwap;
    }

    public Boolean getAccountVerified() {
        return accountVerified;
    }

    public void setAccountVerified(Boolean accountVerified) {
        this.accountVerified = accountVerified;
    }

    public String getDaysAvailable() {
        return daysAvailable;
    }

    public void setDaysAvailable(String daysAvailable) {
        this.daysAvailable = daysAvailable;
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

    public Boolean getOpenOnHolidays() {
        return openOnHolidays;
    }

    public void setOpenOnHolidays(Boolean openOnHolidays) {
        this.openOnHolidays = openOnHolidays;
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
}
