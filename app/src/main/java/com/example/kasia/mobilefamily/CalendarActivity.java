package com.example.kasia.mobilefamily;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CalendarActivity extends AppCompatActivity {

    String items[] = new String[] {"urodziny Janiny", "rocznica slubu Kowalskich", "roczek Krzysia", "zjazd rodzinny"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);


        ListView eventsListView = findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.photos_list_item,R.id.textView6, items);
        eventsListView.setAdapter(adapter);
        eventsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openEventPhotoActivity(items[i]);
            }
        });
    }


    public void openEventPhotoActivity(String eventName){
        Intent intent = new Intent(this, EventPhotosActivity.class);
        Bundle dataBundle = new Bundle();
        dataBundle.putString("eventName", eventName);
        intent.putExtras(dataBundle);
        startActivity(intent);
    }
}
