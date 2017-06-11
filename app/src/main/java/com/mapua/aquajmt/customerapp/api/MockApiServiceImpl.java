package com.mapua.aquajmt.customerapp.api;

import com.google.android.gms.maps.model.LatLng;
import com.mapua.aquajmt.customerapp.models.ShopInfo;
import com.mapua.aquajmt.customerapp.utils.DateTimeUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Bryan on 6/11/2017.
 */

public class ApiService {

    public static HashMap<String, ShopInfo> shops;

    static {
        shops = new HashMap<>();

        try {
            shops.put("e91d9cb3-6a0d-403f-b420-6644b2e64c36",
                    new ShopInfo("e91d9cb3-6a0d-403f-b420-6644b2e64c36",
                            "Aqua JMT",
                            "8456 Trabajo St., Makati, 1207 Metro Manila",
                            new LatLng(14.572882, 121.021191),
                            "09165414338", "4172897",
                            DateTimeUtils.parseTime("08:00:00"),
                            DateTimeUtils.parseTime("20:00:00"),
                            true, true, "1111111", true,
                            new Date(), new Date(), "bdshi13"));
            shops.put("570d5687-e579-48fa-a243-318e6c1b8ea4",
                    new ShopInfo("570d5687-e579-48fa-a243-318e6c1b8ea4",
                            "Wilkin's",
                            "8192 Constancia St., Makati, 1207 Metro Manila",
                            new LatLng(14.571373, 121.020722),
                            "09390353822", "4281029",
                            DateTimeUtils.parseTime("06:00:00"),
                            DateTimeUtils.parseTime("21:00:00"),
                            true, true, "1111111", true,
                            new Date(), new Date(), "bdshi13"));
            shops.put("11513b9c-18e2-4bb5-bfaa-311da44c9a91",
                    new ShopInfo("11513b9c-18e2-4bb5-bfaa-311da44c9a91",
                            "JABI",
                            "5036 Malolos St., Makati, 1207 Metro Manila",
                            new LatLng(14.570862, 121.017774),
                            "09165414338", "4172897",
                            DateTimeUtils.parseTime("07:00:00"),
                            DateTimeUtils.parseTime("21:00:00"),
                            true, true, "1111111", true,
                            new Date(), new Date(), "bdshi13"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public List<ShopInfo> getShopsAt(LatLng location, float radius) {
        return new ArrayList<>(shops.values());
    }

    public ShopInfo getShopInfo(String id) {
        return shops.get(id);
    }
}
