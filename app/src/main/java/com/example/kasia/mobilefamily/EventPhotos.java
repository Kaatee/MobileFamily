package com.example.kasia.mobilefamily;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class EventPhotos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_photos);

        //getting valuesfrom bundle
        Bundle extras = getIntent().getExtras();
        String eventName = extras.getString("eventName");

        TextView textView = (TextView) findViewById(R.id.textView7);
        textView.setText("Zdjęcia z wydarzenia: " + eventName);


    }
}
