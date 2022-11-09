package com.example.mikedrop;

import android.app.DownloadManager;
import android.os.Bundle;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mikedrop.databinding.ActivityDiningBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DiningActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_dining);
//        List<Restaurants> list = new ArrayList<>();
//
//        //TODO: implement places api
////        try {
////            list = getRestaurantList();
////        } catch(IOException e) {
////            e.printStackTrace();
////        }
//
//        list = getRestaurantList();
//
//        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
//        adapter = new RecyclerAdapter(list, getApplication());
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(DiningActivity.this));
//    }


//  get restaurants with places api
//  TODO:I need to know how to return a list from another thread in run()

//    private List<Restaurants> getRestaurantList() throws IOException {
//        List<Restaurants> list = new ArrayList<>();
//        final String API_KEY = BuildConfig.API_KEY;
//        final double lat = 30.407770339447527;
//        final double lon = -91.17940238659425;
//        final String latLongString = Double.toString(lat) + "," + Double.toString(lon);
//        final String radius = "5000";
//
//        if (!Places.isInitialized()){
//            // Initialize the SDK
//            Places.initialize(getApplicationContext(), API_KEY);
//        }
//
//        OkHttpClient client = new OkHttpClient();
//        // Create a new PlacesClient instance
////        PlacesClient placesClient = Places.createClient(this);
//
//        String baseUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
//        HttpUrl.Builder urlBuilder = HttpUrl.parse(baseUrl).newBuilder();
//        urlBuilder.addQueryParameter("location", latLongString);
//        urlBuilder.addQueryParameter("radius", radius);
//        urlBuilder.addQueryParameter("type", "restaurant");
//        urlBuilder.addQueryParameter("key", API_KEY);
//        String url = urlBuilder.build().toString();
//
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NonNull Call call, @NonNull IOException e) {
//                call.cancel();
//            }
//
//            @Override
//            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//
//                final String myResponse = response.body().string();
//
//                DiningActivity.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            JSONObject json = new JSONObject(myResponse);
//                            int jsonLength = json.getJSONArray("results").length();
//                            JSONObject result = new JSONObject();
//
//                            for (int i = 0; i < jsonLength; i++) {
//                                result = json.getJSONArray("results").getJSONObject(i);
//                                String name = result.getString("name");
//                                String address = result.getString("vicinity");
//                                list.add(new Restaurants(name, address));
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                });
//            }
//        });
//        return list;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dining);
        List<Restaurants> list = new ArrayList<>();
        list = getRestaurantList();

        ArrayAdapter adapter = new ArrayAdapter<Restaurants>(this, R.layout.restaurant_card, list);
        ListView listView = (ListView) findViewById(R.id.restaurant_list);
        listView.setAdapter(adapter);
    }

    private List<Restaurants> getRestaurantList() {
        List<Restaurants> list = new ArrayList<>();
        list.add(new Restaurants("Chimes", "3357 Highland Rd"));
        list.add(new Restaurants("Parrain's Seafood Restaurant", "3225 Perkins Rd"));
        list.add(new Restaurants("Elsie's Plate & Pie", "3145 Government St"));
        list.add(new Restaurants("Cecelia Creole Bistro", "421 N 3rd St Suite B"));
        list.add(new Restaurants("Acme Oyster House","3535 Perkins Rd"));
        list.add(new Restaurants("Stroubes Seafood and Steaks","107 3rd St"));
        return list;
    }
}