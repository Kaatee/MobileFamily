package com.example.kasia.mobilefamily;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class EventDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        Bundle extras = getIntent().getExtras();
        int eventId = extras.getInt("eventId");

        showEventDetails(eventId);
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
            }while(cursor.moveToNext());
        }
        cursor.close();

    }
}
