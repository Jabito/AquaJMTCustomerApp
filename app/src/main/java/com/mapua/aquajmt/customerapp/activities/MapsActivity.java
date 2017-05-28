package com.mapua.aquajmt.customerapp.activities;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;

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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mapua.aquajmt.customerapp.R;
import com.mapua.aquajmt.customerapp.fragments.StoreInfoFragment;
import com.mapua.aquajmt.customerapp.models.StoreInfo;
import com.mapua.aquajmt.customerapp.utils.VectorDrawableUtils;

import java.util.HashMap;

@SuppressWarnings("MissingPermission")
public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
        View.OnClickListener, LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, DrawerLayout.DrawerListener,
        GoogleMap.OnMarkerClickListener, GoogleMap.OnCameraIdleListener,
        StoreInfoFragment.StoreInfoFragmentListener {

    private static final int LIGHT_STATUS_BAR_FLAG =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ?
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR : 0;
    private static final int UI_FLAGS =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

    private GoogleMap googleMap;
    private GoogleApiClient googleApiClient;

    private Marker userLocationMarker;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    private View controlButtonsContainer;
    private ImageButton btnNearbyStores;
    private ImageButton btnPrevStore;
    private ImageButton btnGoToLocation;

    private View storeInfoFragmentContainer;
    private StoreInfoFragment storeInfoFragment;
    private HashMap<String, Marker> markerMapById;
    private StoreInfo currentStoreInfo;
    private int currentStoreIndex = 0;

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
        btnNearbyStores = ((ImageButton) findViewById(R.id.btn_nearby_stores));
        btnPrevStore = ((ImageButton) findViewById(R.id.btn_prev_store));
        btnGoToLocation = ((ImageButton) findViewById(R.id.btn_go_to_location));

        btnNearbyStores.setOnClickListener(this);
        btnPrevStore.setOnClickListener(this);
        btnGoToLocation.setOnClickListener(this);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        storeInfoFragmentContainer = findViewById(R.id.store_info_fragment_container);
        if (storeInfoFragmentContainer != null && savedInstanceState == null) {
            storeInfoFragment = new StoreInfoFragment();

            getFragmentManager().beginTransaction()
                    .add(R.id.store_info_fragment_container, storeInfoFragment)
                    .commit();
        }
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
            case R.id.btn_nearby_stores: {
                break;
            }
            case R.id.btn_prev_store: {
                break;
            }
            case R.id.btn_go_to_location: {
                LocationServices.FusedLocationApi.requestLocationUpdates(
                        googleApiClient, LocationRequest.create(), this);
                break;
            }
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
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.getUiSettings().setCompassEnabled(false);
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_json));
        googleMap.setOnMarkerClickListener(this);
        googleMap.setOnCameraIdleListener(this);

        googleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (location != null) {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17);
            googleMap.animateCamera(cameraUpdate);
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(
                googleApiClient, LocationRequest.create(), this);
    }

    @Override
    public void onConnectionSuspended(int i) { }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) { }

    @Override
    public void onLocationChanged(Location location) {
        markerMapById.clear();

        // TODO: retrieve nearby stores here and create markers for each of them
        StoreInfo storeInfo = new StoreInfo("19928374", "Aqua Water Station (Muntinlupa Branch)",
                getString(R.string.lorem_ipsum), getString(R.string.sample_address), 4.5f,
                new LatLng(14.449353, 120.952437));

        Marker marker = googleMap.addMarker(new MarkerOptions().position(storeInfo.getLocation())
                .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.mipmap.sample_image))));
        marker.setTag(storeInfo);
        marker.setVisible(false);
        markerMapById.put(storeInfo.getId(), marker);

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
        for (Marker m : markerMapById.values()) {
            m.setVisible(true);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker == userLocationMarker)
            return true;

        Object storeIdObject = marker.getTag();
        if (storeIdObject != null && storeIdObject instanceof StoreInfo) {
            StoreInfo storeInfo = (StoreInfo) storeIdObject;
            storeInfoFragment.setStoreInView(storeInfo);
            storeInfoFragment.setIsList(false);

            controlButtonsContainer.setVisibility(View.GONE);
            storeInfoFragmentContainer.setVisibility(View.VISIBLE);

            googleMap.setPadding(0, 0, 0, (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 230, getResources().getDisplayMetrics()));
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(storeInfo.getLocation()));

            currentStoreInfo = storeInfo;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        if (storeInfoFragmentContainer.getVisibility() == View.VISIBLE) {
            googleMap.setPadding(0, 0, 0, 0);

            storeInfoFragmentContainer.setVisibility(View.GONE);
            controlButtonsContainer.setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void goToPreviousStore() {

    }

    @Override
    public void goToNextStore() {

    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.argb((int)(slideOffset * 255), 0, 0, 0));

            if (slideOffset >= 0.5f) { // open
                drawerToggle.getDrawerArrowDrawable().setColor(Color.WHITE);
                getWindow().getDecorView().setSystemUiVisibility(UI_FLAGS);
            } else { // closed
                drawerToggle.getDrawerArrowDrawable().setColor(Color.BLACK);
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

    private Bitmap getMarkerBitmapFromView(@DrawableRes int resId) {
        View view = getLayoutInflater().inflate(R.layout.store_badge_layout, null);
        ImageView storeImageView = (ImageView) view.findViewById(R.id.img_store);

        Bitmap storeImage = BitmapFactory.decodeResource(getResources(), resId);
        RoundedBitmapDrawable storeImageDrawable = RoundedBitmapDrawableFactory
                .create(getResources(), storeImage);
        storeImageDrawable.setCircular(true);
        storeImageView.setImageDrawable(storeImageDrawable);

        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();

        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);

        Drawable backgroundDrawable = view.getBackground();
        if (backgroundDrawable != null) {
            backgroundDrawable.draw(canvas);
        }
        view.draw(canvas);

        return bitmap;
    }
}
