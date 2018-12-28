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

        TextView textView = findViewById(R.id.textView27);
        textView.setText(showEventDetails(eventId));
    }

    private String showEventDetails(int eventID){
        String result="Event: ";
        SQLiteOpenHelper familyDataBaseHelper = new FamilyDataBaseHelper(this);
        SQLiteDatabase db =familyDataBaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM event where _id is " + String.valueOf(eventID), null);

        if (cursor.moveToFirst()){
            do{
                result += cursor.getString(cursor.getColumnIndex("name"));
                result +=", data: " + cursor.getString(cursor.getColumnIndex("date"));
                // do what ever you want here
            }while(cursor.moveToNext());
        }
        cursor.close();


        return result;
    }
}
