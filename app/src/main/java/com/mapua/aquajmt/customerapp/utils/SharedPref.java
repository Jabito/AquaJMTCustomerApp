package com.mapua.aquajmt.customerapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by JHAYCO on 1/7/2018.
 */

public class SharedPref {

    public static String USER = "USER";
    public static String SHOP = "SHOP";


    public static String USER_ID = "USER_ID";
    public static String FIRST_NAME = "FIRST_NAME";
    public static String LAST_NAME = "LAST_NAME";


    public static String SELECTED_SHOP_ID = "SELECTED_SHOP_ID";








    public void setBoolValue(String Key, boolean value, Context context) {
        SharedPreferences prefs = context.getSharedPreferences(Key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("LOG", value);
        editor.commit();
    }

    public static boolean getBoolValue(String Key, Context context) {
        SharedPreferences preferences = context.getSharedPreferences(Key, Context.MODE_PRIVATE);
        return preferences.getBoolean("LOG", false);
    }

    public static void setStringValue(String Key, String specID, String value, Context context) {
        SharedPreferences prefs = context.getSharedPreferences(Key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(specID, value);
        editor.apply();
    }

    public static   String getStringValue(String Key, String specID, Context context) {
        SharedPreferences preferences = context.getSharedPreferences(Key, Context.MODE_PRIVATE);
        return preferences.getString(specID, "null");
    }
}

