package com.example.kasia.mobilefamily;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddFamilyMemberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_family_member);
    }


    public void addFamilyMember(View view){
        SQLiteOpenHelper familyDataBaseHelper = new FamilyDataBaseHelper(this);
        SQLiteDatabase db  =familyDataBaseHelper.getReadableDatabase();

        EditText nameText = findViewById(R.id.nameText);
        EditText sureNameText = findViewById(R.id.surnameText);
        EditText dateOfBirthText = findViewById(R.id.dateOfBirthText);
        EditText dateOfDeathText = findViewById(R.id.dateOfDeathText);
        EditText nameDayDateText = findViewById(R.id.nameDayDateText);
        EditText phoneNumbeText = findViewById(R.id.phoneNumberText);
        EditText emailText = findViewById(R.id.emailText);
        EditText addressText = findViewById(R.id.addressText);
        EditText weddingDateText = findViewById(R.id.weddingDateText);
        EditText workPlaceText = findViewById(R.id.workPlaceText);

        ContentValues familyMemberValues = new ContentValues();
        familyMemberValues.put("name", nameText.getText().toString());
        familyMemberValues.put("surname", sureNameText.getText().toString());
        familyMemberValues.put("date_of_birth", dateOfBirthText.getText().toString());
        familyMemberValues.put("date_of_death", dateOfDeathText.getText().toString());
        familyMemberValues.put("date_of_name_day", nameDayDateText.getText().toString());
        familyMemberValues.put("telephone_number", phoneNumbeText.getText().toString());
        familyMemberValues.put("email", emailText.getText().toString());
        familyMemberValues.put("address", addressText.getText().toString());
        familyMemberValues.put("place_of_work", workPlaceText.getText().toString());
        familyMemberValues.put("name", nameText.getText().toString());

        db.insert("person", null, familyMemberValues);
        Toast.makeText(this,"Dodano członka rodziny"+ nameText.getText().toString() ,Toast.LENGTH_LONG).show();
    }
}
