package com.example.kasia.mobilefamily;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MemberDetailsActivity extends AppCompatActivity {
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_details);

        Bundle extras = getIntent().getExtras();
        id = extras.getInt("id");


        SQLiteOpenHelper familyDataBaseHelper = new FamilyDataBaseHelper(this);
        SQLiteDatabase db  =familyDataBaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM person WHERE _id IS "+String.valueOf(id), null);

        showPhoto(id);

        TextView name =  findViewById(R.id.nameTextView);
        TextView birthdate =  findViewById(R.id.birthdateTextView);
        TextView namedate =  findViewById(R.id.namedateTextView);
        TextView phoneNumbeText = findViewById(R.id.phoneNumberTextView);
        TextView emailText = findViewById(R.id.emailTextView);
        TextView weddingDateText = findViewById(R.id.weddingDateTextView);
        TextView addressText = findViewById(R.id.addressTextView);
        TextView workPlaceText = findViewById(R.id.workplaceTextView);
        TextView dateOfDeathText = findViewById(R.id.dateOfDeathTextView);
        TextView familyNameText = findViewById(R.id.familyNameTextView);


        if(cursor.moveToNext()) {
            name.setText(cursor.getString(cursor.getColumnIndexOrThrow("name")) + " " + cursor.getString(cursor.getColumnIndexOrThrow("surname")));
            birthdate.setText(cursor.getString(cursor.getColumnIndexOrThrow("date_of_birth")));
            namedate.setText(cursor.getString(cursor.getColumnIndexOrThrow("date_of_name_day")));
            phoneNumbeText.setText(cursor.getString(cursor.getColumnIndexOrThrow("telephone_number")));
            emailText.setText(cursor.getString(cursor.getColumnIndexOrThrow("email")));
            weddingDateText.setText(cursor.getString(cursor.getColumnIndexOrThrow("wedding_date")));
            familyNameText.setText(cursor.getString(cursor.getColumnIndexOrThrow("family_name")));
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
        Intent intent = new Intent(this, AddRelativeActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    public void showPhoto(int memberId){
        ImageView img = findViewById(R.id.memberPhotoImageView);

        SQLiteOpenHelper familyDataBaseHelper = new FamilyDataBaseHelper(this);
        SQLiteDatabase db =familyDataBaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM  photo where member_id is  " + memberId, null);

        if (cursor !=null ) {
            if (cursor.moveToFirst()) {
                do {
                    try{

                        InputStream inputStream = this.getContentResolver().openInputStream(Uri.parse(cursor.getString(cursor.getColumnIndexOrThrow("uri"))));
                        Bitmap bmp = BitmapFactory.decodeStream(inputStream);
                        BitmapDrawable ob = new BitmapDrawable(getResources(), bmp);
                        img.setBackground(ob);

                    }
                    catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                } while (cursor.moveToNext());
            }
        }

        cursor.close();
        db.close();


    }

}
