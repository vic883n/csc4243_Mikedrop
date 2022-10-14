package com.example.mikedrop;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

public class MikedropActivity extends AppCompatActivity {
    private TextView header;
    private TextView footer;
    private TextView output;
    private Button mapButton;
    private Button weatherButton;
    private Button eventsButton;
    private Button diningButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        header = (TextView) findViewById(R.id.header);
        footer = (TextView) findViewById(R.id.footer);
        output = (TextView) findViewById(R.id.message);
        mapButton = (Button) findViewById(R.id.mapButton);
        weatherButton = (Button) findViewById(R.id.weatherButton);
        eventsButton = (Button) findViewById(R.id.eventsButton);
        diningButton = (Button) findViewById(R.id.diningButton);

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                output.setText("Map Button Clicked");
            }
        });

        weatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                output.setText("Weather Button Clicked");
            }
        });

        eventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                output.setText("Events Button Clicked");
            }
        });

        diningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                output.setText("Dining Button Clicked");
            }
        });
    }
}