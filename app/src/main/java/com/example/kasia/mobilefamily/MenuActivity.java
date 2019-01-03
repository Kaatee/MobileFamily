package com.example.kasia.mobilefamily;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //go to family members data view
        ConstraintLayout familyMembersDataButton = findViewById(R.id.constraintLayout11);
        familyMembersDataButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                openFamilyMemberDataActivity();
            }
        });

        //go to family tree view
        ConstraintLayout familyTreeDataButton =  findViewById(R.id.constraintLayout10);
        familyTreeDataButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                openFamilyTreeActivity();
            }
        });

        //go to callender view
        ConstraintLayout calendarViewButton = findViewById(R.id.constraintLayout13);
        calendarViewButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                openCalendarActivity();
            }
        });

        //go to messages view
        ConstraintLayout messagesViewButton = findViewById(R.id.constraintLayout12);
        messagesViewButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                openMessagesActivity();
            }
        });

        //go to photos view
        ConstraintLayout photosViewButton = findViewById(R.id.constraintLayout15);
        photosViewButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                openPhotosActivity();
            }
        });

        //close app button
        ConstraintLayout closeAppButton =  findViewById(R.id.constraintLayout14);
        closeAppButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                closeApp();
            }
        });


        SQLiteOpenHelper familyDataBaseHelper = new FamilyDataBaseHelper(this);
        SQLiteDatabase db  =familyDataBaseHelper.getReadableDatabase();

    }

    public void openFamilyMemberDataActivity(){
        Intent intent = new Intent(this, ListFamilyMembersActivity.class);
        startActivity(intent);
    }

    public void openFamilyTreeActivity(){
        Intent intent = new Intent(this, FamilyTreeActivity.class);
        startActivity(intent);
    }

    public void openCalendarActivity(){
        Intent intent = new Intent(this, CalendarActivity.class);
        startActivity(intent);
    }

    public void openMessagesActivity(){
        Intent intent = new Intent(this, MessagesActivity.class);
        startActivity(intent);
    }

    public void openPhotosActivity(){
        Intent intent = new Intent(this, EventsListActivity.class);
        startActivity(intent);
    }


    //TODO logout not closeApp
    public void closeApp(){
        try {
            this.deleteDatabase("MobileFamilyDB");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
