package com.example.kasia.mobilefamily;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class AddEventActivity extends AppCompatActivity {
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        Intent intent = getIntent();
        date = intent.getStringExtra("date");
        TextView dateText = findViewById(R.id.dateTextView);
        dateText.setText(date);

    }

    public void addToDB (View view) {
        SQLiteOpenHelper familyDataBaseHelper = new FamilyDataBaseHelper(this);
        SQLiteDatabase db  =familyDataBaseHelper.getReadableDatabase();

        EditText nameText = findViewById(R.id.nameEditText);
        EditText descriptionText = findViewById(R.id.descriptionEditText);
        EditText placeText = findViewById(R.id.placeEditText);

        ContentValues eventValues = new ContentValues();
        eventValues.put("name", nameText.getText().toString());
        eventValues.put("description", descriptionText.getText().toString());
        eventValues.put("date", date);
        eventValues.put("place", placeText.getText().toString());

        db.insert("event", null, eventValues);
        Toast.makeText(this,"Dodano event pomyślnie" ,Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, CalendarActivity.class);
        startActivity(intent);
    }
}
