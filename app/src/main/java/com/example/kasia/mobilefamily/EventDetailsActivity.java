package com.example.kasia.mobilefamily;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class EventDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        Bundle extras = getIntent().getExtras();
        final int eventId = extras.getInt("eventId");

        showEventDetails(eventId);

        Button deleteEventButton = findViewById(R.id.deleteEventButton);
        deleteEventButton.setOnClickListener((new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                deleteEvent(eventId);
            }
        }));
    }

    private void deleteEvent(int eventID) {
        //String photoQuery = "DELETE FROM photo WHERE event_id IS " + eventID;
        //String eventQuery = "DELETE FROM event WHERE _id IS " + eventID;

        //Log.d("---", photoQuery);
        //Log.d("---", eventQuery);

        SQLiteOpenHelper familyDataBaseHelper = new FamilyDataBaseHelper(this);
        SQLiteDatabase db = familyDataBaseHelper.getReadableDatabase();

        db.delete("photo", "_id = " + eventID, null);
        db.delete("event", "_id = " + eventID, null);

        Toast.makeText(this, "Wydarzenie zostało usunięte pomyślnie", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, CalendarActivity.class);
        startActivity(intent);
    }


    private void showEventDetails(int eventID){
        SQLiteOpenHelper familyDataBaseHelper = new FamilyDataBaseHelper(this);
        SQLiteDatabase db =familyDataBaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM event where _id is " + String.valueOf(eventID), null);

        if (cursor.moveToFirst()){
            do{
                String result = "Wydarzenie: " + cursor.getString(cursor.getColumnIndex("name"));
                TextView textView = findViewById(R.id.textView27);
                textView.setText(result);

                String date = cursor.getString(cursor.getColumnIndex("date"));
                TextView textViewDate = findViewById(R.id.eventDateTextView);
                textViewDate.setText(date);

                String description = cursor.getString(cursor.getColumnIndex("description"));
                TextView textViewDescription = findViewById(R.id.eventDescTextView);
                textViewDescription.setText(description);

                String place = cursor.getString(cursor.getColumnIndex("place"));
                TextView textViewPlace = findViewById(R.id.placeTextView);
                textViewPlace.setText(place);

            }while(cursor.moveToNext());
        }
        cursor.close();

    }
}
