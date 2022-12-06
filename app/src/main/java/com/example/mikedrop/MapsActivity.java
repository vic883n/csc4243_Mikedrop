package com.example.mikedrop;

import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.renderscript.Element;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.mikedrop.databinding.ActivityMapsBinding;
import com.google.android.gms.tasks.OnSuccessListener;

import javax.xml.datatype.Duration;

import kotlinx.coroutines.channels.BroadcastChannel;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = MapsActivity.class.getSimpleName();
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private double lat = 30.407770339447527d;
    private double lon = -91.17940238659425d;
    private String markerName = "Patrick F. Taylor";
    private FusedLocationProviderClient fusedLocationClient;

    public MapsActivity() {
        Log.i(TAG, "MapsActivity: ");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        Intent intent = getIntent();
        if (intent != null) {
            Log.i(TAG, "onCreate: something started this application");
            lon = intent.getDoubleExtra("long", -91.17940238659425d);
            lat = intent.getDoubleExtra("lat", 30.407770339447527d);
            markerName = intent.getStringExtra("markerName");

            //-91.17940238659425    30.407770339447527
            //We need map here
        }

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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

        // Add a marker in Sydney and move the camera
        LatLng centerMarker = new LatLng(lat, lon);
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(centerMarker, 17f, 0f, 0f)));
        mMap.addMarker(new MarkerOptions().position(centerMarker).title(markerName));

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    Toast.makeText(getApplicationContext(), "Location Found", Toast.LENGTH_SHORT).show();

                    if (location != null) {
                        LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                        MarkerOptions marker = new MarkerOptions().position(userLocation).title("YOU ARE HERE").draggable(false);
                        mMap.addMarker(marker);
                    } else {
                        Toast.makeText(getApplicationContext(), "Could not get device location", Toast.LENGTH_SHORT).show();
                    }
                });

        Log.i(TAG, "onMapReady: " + mMap.getCameraPosition().target.toString());
    }
}