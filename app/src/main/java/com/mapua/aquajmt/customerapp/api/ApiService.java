package com.mapua.aquajmt.customerapp.api;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.mapua.aquajmt.customerapp.models.ShopInfo;

import java.util.List;

/**
 * Created by Bryan on 6/11/2017.
 */

public interface ApiService {

    List<ShopInfo> getShopsAt(LatLng location, float radius);

    List<ShopInfo> getShopsAt(LatLngBounds latLngBounds);

    ShopInfo getShopInfo(String id);
}
