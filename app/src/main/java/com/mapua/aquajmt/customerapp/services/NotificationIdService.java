package com.mapua.aquajmt.customerapp.services;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Bryan on 6/16/2017.
 */

public class NotificationIdService extends FirebaseInstanceIdService {
    public static final String TAG = NotificationIdService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        String newToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "New FirebaseInstanceIdService Token: " + newToken);
    }
}
