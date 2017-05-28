package com.mapua.aquajmt.customerapp.models;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Bryan on 5/28/2017.
 */

public class StoreInfo {

    private String id;
    private String name;
    private String description;
    private String address;
    private float rating;
    private LatLng location;

    public StoreInfo(String id, String name, String description, String address, float rating, LatLng location) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.rating = rating;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }
}
