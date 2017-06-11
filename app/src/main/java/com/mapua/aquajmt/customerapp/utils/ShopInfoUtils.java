package com.mapua.aquajmt.customerapp.utils;

import android.annotation.SuppressLint;

import com.mapua.aquajmt.customerapp.models.ShopInfo;

import java.util.Calendar;

/**
 * Created by Bryan on 6/11/2017.
 */

public class ShopInfoUtils {

    @SuppressLint("SwitchIntDef")
    public static boolean isShopOpen(ShopInfo shopInfo) {
        String daysAvailable = shopInfo.getDaysAvailable();

        Calendar now = Calendar.getInstance();

        Calendar timeOpen = Calendar.getInstance();
        timeOpen.setTime(shopInfo.getTimeOpen());
        timeOpen.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));

        Calendar timeClose = Calendar.getInstance();
        timeClose.setTime(shopInfo.getTimeClose());
        timeClose.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));

        // time right now is on or after the opening time and before the closing time
        if (now.compareTo(timeOpen) >= 0 && now.before(timeClose)) {
            switch (now.get(Calendar.DAY_OF_WEEK)) {
                case Calendar.SUNDAY:
                    return daysAvailable.charAt(0) == '1';
                case Calendar.MONDAY:
                    return daysAvailable.charAt(1) == '1';
                case Calendar.TUESDAY:
                    return daysAvailable.charAt(2) == '1';
                case Calendar.WEDNESDAY:
                    return daysAvailable.charAt(3) == '1';
                case Calendar.THURSDAY:
                    return daysAvailable.charAt(4) == '1';
                case Calendar.FRIDAY:
                    return daysAvailable.charAt(5) == '1';
                case Calendar.SATURDAY:
                    return daysAvailable.charAt(6) == '1';
                default:
                    throw new AssertionError();
            }
        }

        return false;
    }

    public static String getStars(float rating) {
        if (rating < 0.5)
            return "00000";
        else if (rating >= 0.5 && rating < 1)
            return "10000";
        else if (rating >= 1 && rating < 1.5)
            return "20000";
        else if (rating >= 1.5 && rating < 2)
            return "21000";
        else if (rating >= 2 && rating < 2.5)
            return "22000";
        else if (rating >= 2.5 && rating < 3)
            return "22100";
        else if (rating >= 3 && rating < 3.5)
            return "22200";
        else if (rating >= 3.5 && rating < 4)
            return "22210";
        else if (rating >= 4 && rating < 4.5)
            return "22220";
        else if (rating >= 4.5 && rating < 5)
            return "22221";
        else
            return "22222";
    }
}
