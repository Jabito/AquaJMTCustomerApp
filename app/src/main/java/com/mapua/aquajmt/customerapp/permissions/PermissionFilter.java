package com.mapua.aquajmt.customerapp.permissions;

import android.content.pm.PackageManager;

class PermissionFilter {

    private final int requestCode;
    private final String permission;
    private boolean isGranted;

    public PermissionFilter(int requestCode, String permission) {
        this.requestCode = requestCode;
        this.permission = permission;
    }

    int getRequestCode() {
        return requestCode;
    }

    String getPermission() {
        return permission;
    }

    boolean isGranted() {
        return isGranted;
    }

    void onRequestPermissionResult(int[] grantResults) {
        isGranted = grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
    }
}
