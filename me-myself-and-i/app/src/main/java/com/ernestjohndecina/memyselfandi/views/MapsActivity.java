package com.ernestjohndecina.memyselfandi.views;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;

import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.ernestjohndecina.memyselfandi.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.ernestjohndecina.memyselfandi.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private LocationManager locationManager;
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    Geocoder geo;

    Button selectLocationButton;
    Button refreshLocationButton;
    ProgressBar progressBar;


    Double latitude;
    Double longitude;
    String addressString;

    private static final int MY_PERMISSION_GPS = 1;
    private long minTime = 1000;
    private float minDistance = 10;
    private float zoom = 10.0F;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        setGeocoder();
        setViews();
        setOnclickListeners();
        setUpLocation();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setOnMapClickListeners();
    }

    private void setViews() {
        selectLocationButton = findViewById(R.id.selectLocationButton);
        refreshLocationButton = findViewById(R.id.refreshLocationButton);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
    }

    private void setOnclickListeners() {
        selectLocationButton.setOnClickListener(v -> selectLocation());
        refreshLocationButton.setOnClickListener(v -> getLocation());
    }

    private void setGeocoder() {
        geo = new Geocoder(
                this.getApplicationContext(),
                Locale.getDefault()
        );
    }

    private void setOnMapClickListeners() {
        mMap.setOnMapClickListener(latLng -> {
            latitude = latLng.latitude;
            longitude = latLng.longitude;

            ArrayList<Address> addresses;
            try {
                addresses = (ArrayList<Address>) geo.getFromLocation(                  // ---- Returns a List of address containing the locations
                        latitude,
                        longitude,
                        1
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            addressString =
                    addresses.get(0).getAdminArea() + " " +
                            addresses.get(0).getCountryName();

            LatLng selectedLocation = new LatLng(latitude, longitude);
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(selectedLocation).title(addressString));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(selectedLocation, zoom));
        });
    }


    private void selectLocation() {
        Intent resultIntent = new Intent();

        resultIntent.putExtra("LONGITUDE", longitude);
        resultIntent.putExtra("LATITUDE", latitude);
        resultIntent.putExtra("ADDRESS", addressString);

        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    private void setUpLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            return;

        ActivityCompat.requestPermissions(
                MapsActivity.this,
                new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION
                },
                MY_PERMISSION_GPS
        );
    }


    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    MapsActivity.this,
                    new String[] {
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },
                    MY_PERMISSION_GPS
            );
        }
        else {
            progressBar.setVisibility(View.VISIBLE);

            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    minTime,
                    minDistance,
                    location -> {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();

                        ArrayList<Address> addresses;
                        try {
                            addresses = (ArrayList<Address>) geo.getFromLocation(
                                    latitude,
                                    longitude,
                                    1
                            );
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        addressString =
                                addresses.get(0).getAdminArea() + " " +
                                        addresses.get(0).getCountryName();

                        progressBar.setVisibility(View.GONE);

                        LatLng selectedLocation = new LatLng(latitude, longitude);
                        mMap.clear();
                        mMap.addMarker(new MarkerOptions().position(selectedLocation).title(addressString));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(selectedLocation, zoom));
                    }
            );
        }
    }
}