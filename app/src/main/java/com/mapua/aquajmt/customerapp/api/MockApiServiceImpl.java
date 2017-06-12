package com.mapua.aquajmt.customerapp.api;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.mapua.aquajmt.customerapp.models.ShopInfo;
import com.mapua.aquajmt.customerapp.models.ShopSalesInfo;
import com.mapua.aquajmt.customerapp.utils.DateTimeUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Bryan on 6/11/2017.
 */

public class MockApiServiceImpl implements ApiService {

    private static MockApiServiceImpl instance;
    private static HashMap<String, ShopInfo> shops;
    private static HashMap<String, ShopSalesInfo> shopSalesInfo;

    static {
        shops = new HashMap<>();
        shopSalesInfo = new HashMap<>();

        try {
            String id = "e91d9cb3-6a0d-403f-b420-6644b2e64c36";
            shops.put(id, new ShopInfo(id, "Aqua JMT",
                    "8456 Trabajo St., Makati, 1207 Metro Manila",
                    new LatLng(14.572882, 121.021191),
                    "09165414338", "4172897",
                    DateTimeUtils.parseTime("08:00:00"),
                    DateTimeUtils.parseTime("20:00:00"),
                    true, true, "1111111", true, 0.0,
                    new Date(), new Date(), "bdshi13"));
            shopSalesInfo.put(id, new ShopSalesInfo(id,
                    true, true, 20, 20,
                    true, true, true, true,
                    25.0, 25.0, 25.0, 30.0, 30.0, 30.0,
                    new Date(), new Date(), ""));

            id = "570d5687-e579-48fa-a243-318e6c1b8ea4";
            shops.put(id, new ShopInfo(id, "Wilkin's",
                    "8192 Constancia St., Makati, 1207 Metro Manila",
                    new LatLng(14.571373, 121.020722),
                    "09390353822", "4281029",
                    DateTimeUtils.parseTime("06:00:00"),
                    DateTimeUtils.parseTime("21:00:00"),
                    true, true, "1111111", true, 0.0,
                    new Date(), new Date(), "bdshi13"));
            shopSalesInfo.put(id, new ShopSalesInfo(id,
                    true, true, 20, 20,
                    true, true, false, false,
                    25.0, 25.0, 25.0, 30.0, 30.0, 30.0,
                    new Date(), new Date(), ""));

            id = "11513b9c-18e2-4bb5-bfaa-311da44c9a91";
            shops.put(id, new ShopInfo(id, "JABI",
                    "5036 Malolos St., Makati, 1207 Metro Manila",
                    new LatLng(14.570862, 121.017774),
                    "09165414338", "4172897",
                    DateTimeUtils.parseTime("07:00:00"),
                    DateTimeUtils.parseTime("21:00:00"),
                    true, true, "1111111", true, 0.0,
                    new Date(), new Date(), "bdshi13"));
            shopSalesInfo.put(id, new ShopSalesInfo(id,
                    false, false, 20, 20,
                    true, true, true, true,
                    25.0, 25.0, 25.0, 30.0, 30.0, 30.0,
                    new Date(), new Date(), ""));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static ApiService getInstance() {
        if (instance == null)
            instance = new MockApiServiceImpl();
        return instance;
    }

    private MockApiServiceImpl() { }

    public List<ShopInfo> getShopsAt(LatLng location, float radius) {
        return new ArrayList<>(shops.values());
    }

    @Override
    public List<ShopInfo> getShopsAt(LatLngBounds latLngBounds) {
        ArrayList<ShopInfo> filteredShopInfo = new ArrayList<>();
        for (ShopInfo shopInfo : shops.values()) {
            if (latLngBounds.contains(shopInfo.getLocation())) {
                filteredShopInfo.add(shopInfo);
            }
        }

        return filteredShopInfo;
    }

    public ShopInfo getShopInfo(String id) {
        return shops.get(id);
    }

    public ShopSalesInfo getShopSalesInfo(String id) {
        return shopSalesInfo.get(id);
    }
}
