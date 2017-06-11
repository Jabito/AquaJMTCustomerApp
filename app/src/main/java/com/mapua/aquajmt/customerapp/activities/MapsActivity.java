package com.mapua.aquajmt.customerapp.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Rect;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mapua.aquajmt.customerapp.R;
import com.mapua.aquajmt.customerapp.api.ApiService;
import com.mapua.aquajmt.customerapp.api.MockApiServiceImpl;
import com.mapua.aquajmt.customerapp.fragments.OrderFragment;
import com.mapua.aquajmt.customerapp.fragments.ShopInfoFragment;
import com.mapua.aquajmt.customerapp.models.ShopInfo;
import com.mapua.aquajmt.customerapp.utils.VectorDrawableUtils;

import java.util.HashMap;
import java.util.List;

@SuppressWarnings("MissingPermission")
public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
        View.OnClickListener, LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, DrawerLayout.DrawerListener,
        GoogleMap.OnMarkerClickListener, GoogleMap.OnCameraIdleListener,
        ShopInfoFragment.StoreInfoFragmentListener {

    public static final int ORDER_FROM_STORE_REQUEST = 1;
    public static final int VIEW_STORE_INFO_REQUEST = 2;

    private static final int LIGHT_STATUS_BAR_FLAG =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ?
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR : 0;
    private static final int UI_FLAGS =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

    private static final String CURRENT_STORE_ID = "MapsActivity.CurrentStoreId";

    private GoogleMap googleMap;
    private GoogleApiClient googleApiClient;

    private Marker userLocationMarker;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    private View controlButtonsContainer;
    private ImageButton btnPrevStore;
    private ImageButton btnGoToLocation;
    private Button btnEditProfile;
    private Button btnUserPaymentHistory;
    private Button btnLogout;

    private ApiService apiService;

    private View storeInfoFragmentContainer;
    private ShopInfoFragment shopInfoFragment;
    private HashMap<String, Marker> markerMapById;

    private ShopInfo currentShopInfo;
    private int currentStoreIndex = 0;
    private boolean isFromViewShopInfo = false;

    public MapsActivity() {
        this.markerMapById = new HashMap<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            final Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(UI_FLAGS | LIGHT_STATUS_BAR_FLAG);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerToggle.getDrawerArrowDrawable().setColor(Color.BLACK);

        drawerLayout.addDrawerListener(this);
        drawerLayout.addDrawerListener(drawerToggle);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        controlButtonsContainer = findViewById(R.id.ctrl_buttons);
        btnPrevStore = ((ImageButton) findViewById(R.id.btn_prev_store));
        btnGoToLocation = ((ImageButton) findViewById(R.id.btn_go_to_location));

        btnPrevStore.setOnClickListener(this);
        btnGoToLocation.setOnClickListener(this);

        btnEditProfile = (Button) findViewById(R.id.btn_edit_profile);
        btnUserPaymentHistory = (Button) findViewById(R.id.btn_user_payment_history);
        btnLogout = (Button) findViewById(R.id.btn_logout);

        btnEditProfile.setOnClickListener(this);
        btnUserPaymentHistory.setOnClickListener(this);
        btnLogout.setOnClickListener(this);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        storeInfoFragmentContainer = findViewById(R.id.store_info_fragment_container);
        if (storeInfoFragmentContainer != null && savedInstanceState == null) {
            shopInfoFragment = new ShopInfoFragment();

            getFragmentManager().beginTransaction()
                    .add(R.id.store_info_fragment_container, shopInfoFragment)
                    .commit();
        }

        apiService = MockApiServiceImpl.getInstance();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onPause() {
        if (storeInfoFragmentContainer.getVisibility() == View.VISIBLE) {
            storeInfoFragmentContainer.setVisibility(View.GONE);
            controlButtonsContainer.setVisibility(View.VISIBLE);
        }

        super.onPause();
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_prev_store:
                break;
            case R.id.btn_go_to_location:
                LocationServices.FusedLocationApi.requestLocationUpdates(
                        googleApiClient, LocationRequest.create(), this);
                break;
            case R.id.btn_edit_profile:
                startActivity(new Intent(this, EditProfileActivity.class));
                break;
            case R.id.btn_user_payment_history:
                startActivity(new Intent(this, PaymentHistoryActivity.class));
                break;
            case R.id.btn_logout:
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                startActivity(new Intent(MapsActivity.this, LoginActivity.class));
                                finish();
                            case DialogInterface.BUTTON_NEGATIVE:
                                dialog.dismiss();
                                break;
                        }
                    }
                };
                new AlertDialog.Builder(this)
                        .setMessage("Are you sure you would like to log out?")
                        .setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener)
                        .show();
                break;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.getUiSettings().setCompassEnabled(false);
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_json));
        googleMap.setOnMarkerClickListener(this);
        googleMap.setOnCameraIdleListener(this);

        storeInfoFragmentContainer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
                int statusBarHeight = rect.top;

                if (storeInfoFragmentContainer.getVisibility() == View.VISIBLE) {
                    googleMap.setPadding(0, statusBarHeight, 0, storeInfoFragmentContainer.getHeight());
                    googleMap.animateCamera(CameraUpdateFactory.newLatLng(currentShopInfo.getLocation()));
                } else {
                    googleMap.setPadding(0, statusBarHeight, 0, 0);
                }
            }
        });

        markerMapById.clear();
        googleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (isFromViewShopInfo && currentShopInfo != null) {
            setCurrentShopInfo(currentShopInfo);
        } else {
            Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if (location != null) {
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17);
                googleMap.animateCamera(cameraUpdate);
            }

            LocationServices.FusedLocationApi.requestLocationUpdates(
                    googleApiClient, LocationRequest.create(), this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) { }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) { }

    @Override
    public void onLocationChanged(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17);
        googleMap.animateCamera(cameraUpdate);

        if (userLocationMarker == null) {
            userLocationMarker = googleMap.addMarker(new MarkerOptions().position(latLng).icon(
                    VectorDrawableUtils.createBitmapDescriptor(getResources(),
                            R.drawable.ic_radio_button_checked_primary, getTheme())));
        } else {
            userLocationMarker.setPosition(latLng);
        }

        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
    }

    @Override
    public void onCameraIdle() {
        final LatLngBounds latLngBounds = googleMap.getProjection().getVisibleRegion().latLngBounds;
        List<ShopInfo> shops = apiService.getShopsAt(latLngBounds);

        for (ShopInfo shopInfo : shops) {
            if (markerMapById.containsKey(shopInfo.getId()))
                continue;

            Marker marker = googleMap.addMarker(new MarkerOptions().position(shopInfo.getLocation())
                    .icon(VectorDrawableUtils.createBitmapDescriptor(getResources(),
                            R.drawable.ic_local_cafe_35dp_primary_dark, getTheme())));
            marker.setTag(shopInfo);
            markerMapById.put(shopInfo.getId(), marker);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker == userLocationMarker)
            return true;

        Object storeIdObject = marker.getTag();
        if (storeIdObject != null && storeIdObject instanceof ShopInfo) {
            ShopInfo shopInfo = (ShopInfo) storeIdObject;
            setCurrentShopInfo(shopInfo);
        }

        return true;
    }

    private void setCurrentShopInfo(ShopInfo shopInfo) {
        if (shopInfo != null) {
            currentShopInfo = shopInfo;
            shopInfoFragment.setStoreInView(shopInfo);

            controlButtonsContainer.setVisibility(View.GONE);
            storeInfoFragmentContainer.setVisibility(View.VISIBLE);
        } else {
            storeInfoFragmentContainer.setVisibility(View.GONE);
            controlButtonsContainer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        if (shopInfoFragment.getMoreStoreInfoVisibility()) {
            shopInfoFragment.setMoreStoreInfoVisibility(false);
        } else if (storeInfoFragmentContainer.getVisibility() == View.VISIBLE) {
            setCurrentShopInfo(null);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void orderFromStore() {
        // TODO: set the store id as an extra
        OrderFragment orderFragment = new OrderFragment();
        orderFragment.show(getSupportFragmentManager(), "orderDialog");
    }

    @Override
    public void viewAllStoreInfo() {
        // TODO: set the store id as an extra
        Intent intent = new Intent(this, ShopInfoActivity.class);
        intent.putExtra(CURRENT_STORE_ID, currentShopInfo.getId());
        startActivityForResult(intent, VIEW_STORE_INFO_REQUEST);
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.argb((int)(slideOffset * 255), 0, 0, 0));

            if (slideOffset >= 0.5f) { // open
                getWindow().getDecorView().setSystemUiVisibility(UI_FLAGS);
            } else { // closed
                getWindow().getDecorView().setSystemUiVisibility(UI_FLAGS | LIGHT_STATUS_BAR_FLAG);
            }
        }
    }

    @Override
    public void onDrawerOpened(View drawerView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(UI_FLAGS);
        }
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(UI_FLAGS | LIGHT_STATUS_BAR_FLAG);
        }
    }

    @Override
    public void onDrawerStateChanged(int newState) { }
}
