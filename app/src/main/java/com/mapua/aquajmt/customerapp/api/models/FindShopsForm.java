package com.mapua.aquajmt.customerapp.api.models;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Bryan on 6/11/2017.
 */

public class FindShopsForm {

    private final LatLng location;
    private final float radius;

    public FindShopsForm(LatLng location, float radius) {
        if (location == null) {
            throw new IllegalArgumentException("Properties of this object is not allowed " +
                    "to contain null values.");
        }

        this.location = location;
        this.radius = radius;
    }

    public LatLng getLocation() {
        return location;
    }

    public float getRadius() {
        return radius;
    }
}
