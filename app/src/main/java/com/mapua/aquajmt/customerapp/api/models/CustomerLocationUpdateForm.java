package com.mapua.aquajmt.customerapp.api.models;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Bryan on 6/11/2017.
 */

public class CustomerLocationUpdateForm {

    private final String id;
    private final String address;
    private final LatLng location;

    public CustomerLocationUpdateForm(String id, String address, LatLng location) {

        if (id == null || address == null || location == null) {
            throw new IllegalArgumentException("Properties of this object is not allowed " +
                    "to contain null values.");
        }

        this.id = id;
        this.address = address;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public LatLng getLocation() {
        return location;
    }
}
