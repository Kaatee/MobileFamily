package com.example.kasia.mobilefamily;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //go to family members data view
        ConstraintLayout familyMembersDataButton = (ConstraintLayout) findViewById(R.id.constraintLayout11);
        familyMembersDataButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                openFamilyMemberDataActivity();
            }
        });

        //go to family tree view
        ConstraintLayout familyTreeDataButton = (ConstraintLayout) findViewById(R.id.constraintLayout10);
        familyTreeDataButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                openFamilyTreeActivity();
            }
        });

        //go to callender view
        ConstraintLayout callenderViewButton = (ConstraintLayout) findViewById(R.id.constraintLayout13);
        callenderViewButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                openCallenderActivity();
            }
        });

        //go to messages view
        ConstraintLayout messagesViewButton = (ConstraintLayout) findViewById(R.id.constraintLayout12);
        messagesViewButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                openMessagesActivity();
            }
        });

        //go to photos view
        ConstraintLayout photosViewButton = (ConstraintLayout) findViewById(R.id.constraintLayout15);
        photosViewButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                openPhotosActivity();
            }
        });

        //close app button
        ConstraintLayout closeAppButton = (ConstraintLayout) findViewById(R.id.constraintLayout14);
        closeAppButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                closeApp();
            }
        });

    }

    public void openFamilyMemberDataActivity(){
        Intent intent = new Intent(this, FamilyMemberData.class);
        startActivity(intent);
    }

    public void openFamilyTreeActivity(){
        Intent intent = new Intent(this, FamilyTree.class);
        startActivity(intent);
    }

    public void openCallenderActivity(){
        Intent intent = new Intent(this, Timetable.class);
        startActivity(intent);
    }

    public void openMessagesActivity(){
        Intent intent = new Intent(this, Messages.class);
        startActivity(intent);
    }

    public void openPhotosActivity(){
        Intent intent = new Intent(this, Photos.class);
        startActivity(intent);
    }

    public void closeApp(){
        finishAffinity();
    }
}
