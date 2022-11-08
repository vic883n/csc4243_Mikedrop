package com.example.mikedrop;

import android.app.DownloadManager;
import android.os.Bundle;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mikedrop.databinding.ActivityDiningBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DiningActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityDiningBinding binding;

    TextView txtString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dining);
        txtString=(TextView) findViewById(R.id.txtString);

        try {
            getRestaurantList();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }


    private void getRestaurantList() throws IOException {
        final String API_KEY = BuildConfig.API_KEY;
        final double lat = 30.407770339447527;
        final double lon = -91.17940238659425;
        final String latLongString = Double.toString(lat) + "," + Double.toString(lon);
        final String radius = "5000";

        if (!Places.isInitialized()){
            // Initialize the SDK
            Places.initialize(getApplicationContext(), API_KEY);
        }

        OkHttpClient client = new OkHttpClient();
        // Create a new PlacesClient instance
//        PlacesClient placesClient = Places.createClient(this);

        String baseUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(baseUrl).newBuilder();
        urlBuilder.addQueryParameter("location", latLongString);
        urlBuilder.addQueryParameter("radius", radius);
        urlBuilder.addQueryParameter("type", "restaurant");
        urlBuilder.addQueryParameter("key", API_KEY);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                final String myResponse = response.body().string();

                DiningActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject json = new JSONObject(myResponse);
                            txtString.setText(json.getJSONArray("results").getJSONObject(0).getString("name"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });
    }
}