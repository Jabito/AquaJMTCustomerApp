package com.mapua.aquajmt.customerapp.permissions;

import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;

public class PermissionsPipeline {

    private HashMap<Integer, PermissionFilter> permissionFilters;

    public PermissionsPipeline() {
        permissionFilters = new HashMap<>();
    }

    public void registerPermissionFilter(int requestId, PermissionFilter permissionFilter) {
        permissionFilters.put(requestId, permissionFilter);
    }

    public void execute(AppCompatActivity activity) {

    }
}
