package com.example.mikedrop;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mikedrop.databinding.ActivityMapsBinding;
import com.example.mikedrop.databinding.ContainerMapBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsContainer extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = MapsContainer.class.getSimpleName();
    private GoogleMap mMap;
    private ActivityMapsBinding mapBinding;
    private double lat = 30.407770339447527d;
    private double lon = -91.17940238659425d;
    private String markerName = "Patrick F. Taylor";
    private FusedLocationProviderClient fusedLocationClient;

    private ContainerMapBinding binding;

    public MapsContainer() {
        Log.i(TAG, "MapsActivity: ");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        binding = ContainerMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.homemap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MapsContainer.this, MikedropActivity.class));
            }
        });

        Intent intent = getIntent();
        if (intent != null) {
            Log.i(TAG, "onCreate: something started this application");
            lon = intent.getDoubleExtra("long", -91.17940238659425d);
            lat = intent.getDoubleExtra("lat", 30.407770339447527d);
            markerName = intent.getStringExtra("markerName");
        }

        mapBinding = binding.subMap;
        //setContentView(mapBinding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    Toast.makeText(getApplicationContext(), "Getting device location", Toast.LENGTH_SHORT).show();

                    if (location != null) {
                        LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                        MarkerOptions marker = new MarkerOptions().position(userLocation).title("YOU ARE HERE").draggable(false);
                        mMap.addMarker(marker);
                    } else {
                        Toast.makeText(getApplicationContext(), "Could not get device location", Toast.LENGTH_SHORT).show();
                    }
                });

        LatLng centerMarker = new LatLng(lat, lon);
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(centerMarker, 17f, 0f, 0f)));
        mMap.addMarker(new MarkerOptions().position(centerMarker).title(markerName));
        Log.i(TAG, "onMapReady: " + mMap.getCameraPosition().target.toString());
    }


    public void onClick(View v)
    {
        System.out.println("TESTTETSTTSTSTTSTSTS");
        switch (v.getId())
        {
            case R.id.search_address:
                System.out.println("INSIDE SWITCH");
                EditText addressField = (EditText) findViewById(R.id.location_search);
                String address = addressField.getText().toString();

                List<Address> addressList = null;
                MarkerOptions userMarkerOptions = new MarkerOptions();

                if (! TextUtils.isEmpty(address))
                {
                    System.out.println("INSIDE FIRST IF");
                    Geocoder geocoder = new Geocoder(this);
                    try {
                        addressList = geocoder.getFromLocationName(address, 10, 30.396985, -91.198076, 30.423757, -91.171560);
                        //geocoder.getFromLocationName(lowleftlat, lowleftlong, uprightlat, uprightlong)
                        if (addressList != null)
                        {
                            System.out.println("INSIDE SECOND IF");
                            for(int i = 0; i < addressList.size(); i++)
                            {
                                System.out.println(addressList.size());
                                Address userAddress = addressList.get(i);
                                LatLng latlng = new LatLng(userAddress.getLatitude(), userAddress.getLongitude());

                                userMarkerOptions.position(latlng);
                                userMarkerOptions.title("user Current Location");
                                userMarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

                                mMap.addMarker(userMarkerOptions);
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
                                mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
                            }
                        }
                        else
                        {
                            Toast.makeText(this, "Location not found.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    Toast.makeText(this, "Please enter location name", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}