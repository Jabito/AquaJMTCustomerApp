package com.mapua.aquajmt.customerapp.permissions;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Iterator;

public class PermissionsPipeline {

    public interface PipelineObserver {
        void success();
        void failed();
    }

    private final Context context;
    private final PipelineObserver pipelineObserver;

    private Iterator<Integer> iterator;
    private HashMap<Integer, PermissionFilter> permissionFilters;

    public PermissionsPipeline(Context context, PipelineObserver pipelineObserver) {
        this.context = context;
        this.pipelineObserver = pipelineObserver;

        permissionFilters = new HashMap<>();
    }

    public PermissionsPipeline registerPermissionFilter(int requestCode, String permission) {
        permissionFilters.put(requestCode, new PermissionFilter(requestCode, permission));
        return this;
    }

    public void execute(AppCompatActivity activity) {
        iterator = permissionFilters.keySet().iterator();
        if (iterator.hasNext()) {
            nextPermissionFilter(activity);
        } else {
            pipelineObserver.success(); // There are no permissions to check
        }
    }

    public void onRequestPermissionResult(AppCompatActivity activity, int requestCode, String[] permissions, int[] grantResults) {
        PermissionFilter permissionFilter = permissionFilters.get(requestCode);
        permissionFilter.onRequestPermissionResult(grantResults);

        if (iterator.hasNext()) {
            nextPermissionFilter(activity);
        } else {
            boolean allPermissionsAreGranted = true;
            for (PermissionFilter pf : permissionFilters.values()) {
                allPermissionsAreGranted = allPermissionsAreGranted && pf.isGranted();
            }
            if (allPermissionsAreGranted) {
                pipelineObserver.success();
            } else {
                pipelineObserver.failed();
            }
        }
    }

    private void checkPermissionCompat(Context context, AppCompatActivity activity, PermissionFilter permissionFilter) {
        if (ActivityCompat.checkSelfPermission(context, permissionFilter.getPermission()) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[] {
                    permissionFilter.getPermission()
            }, permissionFilter.getRequestCode());
        } else if (iterator.hasNext()) {
            nextPermissionFilter(activity);
        }
    }

    private void nextPermissionFilter(AppCompatActivity activity) {
        Integer requestCode = iterator.next();
        PermissionFilter permissionFilter = permissionFilters.get(requestCode);
        checkPermissionCompat(context, activity, permissionFilter);
    }
}
