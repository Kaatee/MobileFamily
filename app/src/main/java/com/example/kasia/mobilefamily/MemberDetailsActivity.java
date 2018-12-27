package com.example.kasia.mobilefamily;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MemberDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_details);

        Bundle extras = getIntent().getExtras();
        int id = extras.getInt("id");


        SQLiteOpenHelper familyDataBaseHelper = new FamilyDataBaseHelper(this);
        SQLiteDatabase db  =familyDataBaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM person WHERE _id IS "+String.valueOf(id), null);

        TextView name =  findViewById(R.id.nameTextView);
        TextView birthdate =  findViewById(R.id.birthdateTextView);
        TextView namedate =  findViewById(R.id.namedateTextView);
        TextView phoneNumbeText = findViewById(R.id.phoneNumberTextView);
        TextView emailText = findViewById(R.id.emailTextView);
        TextView weddingDateText = findViewById(R.id.weddingDateTextView);
        TextView addressText = findViewById(R.id.weddingDateTextView);
        TextView workPlaceText = findViewById(R.id.workplaceTextView);
        TextView dateOfDeathText = findViewById(R.id.dateOfDeathTextView);

/*

 telephone_number text not null , email text not null , address text not null , place_of_work text not null)";

*/

        if(cursor.moveToNext()) {
            name.setText(cursor.getString(cursor.getColumnIndexOrThrow("name")) + " " + cursor.getString(cursor.getColumnIndexOrThrow("surname")));
            birthdate.setText(cursor.getString(cursor.getColumnIndexOrThrow("date_of_birth")));
            namedate.setText(cursor.getString(cursor.getColumnIndexOrThrow("date_of_name_day")));
            phoneNumbeText.setText(cursor.getString(cursor.getColumnIndexOrThrow("telephone_number")));
            emailText.setText(cursor.getString(cursor.getColumnIndexOrThrow("email")));
            weddingDateText.setText("DOROBIC DO BAZY DANYCH");
            addressText.setText(cursor.getString(cursor.getColumnIndexOrThrow("address")));
            workPlaceText.setText(cursor.getString(cursor.getColumnIndexOrThrow("place_of_work")));
            dateOfDeathText.setText(cursor.getString(cursor.getColumnIndexOrThrow("date_of_death")));
        }

        Button addMemberButton =  findViewById(R.id.button3);
        addMemberButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                openAddFamilyMemberActivity();
            }
        });
    }

    public void openAddFamilyMemberActivity(){
        Intent intent = new Intent(this, AddFamilyMemberActivity.class);
        startActivity(intent);
    }

}
