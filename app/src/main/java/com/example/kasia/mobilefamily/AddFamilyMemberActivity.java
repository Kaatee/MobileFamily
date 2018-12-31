package com.example.kasia.mobilefamily;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class AddFamilyMemberActivity extends AppCompatActivity {

    private EditText mDisplayBirthDate;
    private DatePickerDialog.OnDateSetListener mBirthDateSetListener;

    private EditText mDisplayNamedayDate;
    private DatePickerDialog.OnDateSetListener mNamedayDateSetListener;

    private EditText mDisplayWeddingDate;
    private DatePickerDialog.OnDateSetListener mWeddingDateSetListener;

    private EditText mDisplayDeathDate;
    private DatePickerDialog.OnDateSetListener mDeathDateSetListener;

    private static final int SET_URI = 0;

    int memberID;
    String uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_family_member);

        //birthday menage
        mDisplayBirthDate = findViewById(R.id.dateOfBirthText);
        mDisplayBirthDate.setOnClickListener(new View.OnClickListener() {
            DatePickerDialog.OnDateSetListener listener = mBirthDateSetListener;
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddFamilyMemberActivity.this, R.style.Theme_AppCompat_Light_Dialog, mBirthDateSetListener, year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.LTGRAY));
                dialog.show();
            }
        });

        mBirthDateSetListener = myOnDataSetDMY(mDisplayBirthDate);


        //deathdate menage
        mDisplayDeathDate = findViewById(R.id.dateOfDeathText);
        mDisplayDeathDate.setOnClickListener(new View.OnClickListener() {
            DatePickerDialog.OnDateSetListener listener = mDeathDateSetListener;
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddFamilyMemberActivity.this, R.style.Theme_AppCompat_Light_Dialog, mDeathDateSetListener, year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.LTGRAY));
                dialog.show();
            }
        });

        mDeathDateSetListener = myOnDataSetDMY(mDisplayDeathDate);


        //wedding date menage
        mDisplayWeddingDate = findViewById(R.id.weddingDateText);
        mDisplayWeddingDate.setOnClickListener(new View.OnClickListener() {
            DatePickerDialog.OnDateSetListener listener = mWeddingDateSetListener;
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddFamilyMemberActivity.this, R.style.Theme_AppCompat_Light_Dialog, mWeddingDateSetListener, year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.LTGRAY));
                dialog.show();
            }
        });

        mWeddingDateSetListener = myOnDataSetDMY(mDisplayWeddingDate);
    }

    private DatePickerDialog.OnDateSetListener myOnDataSetDMY(final EditText myeditText){
        DatePickerDialog.OnDateSetListener xx = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month=month+1;
                myeditText.setText(format(new GregorianCalendar(year,month,day)));
            }
        };
        return xx;
    }

    public static String format(Calendar calendar){
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        fmt.setCalendar(calendar);
        String dateFormatted = fmt.format(calendar.getTime());
        return dateFormatted;
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
        EditText familyNameText = findViewById(R.id.familyNameEditText);

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
        familyMemberValues.put("family_name", familyNameText.getText().toString());
        familyMemberValues.put("wedding_date", weddingDateText.getText().toString());

        db.insert("person", null, familyMemberValues);
        Toast.makeText(this,"Dodano członka rodziny"+ nameText.getText().toString() ,Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, ListFamilyMembersActivity.class);

        String query = "SELECT * FROM person WHERE name = '"+  nameText.getText().toString() + "' AND surname = '" + sureNameText.getText().toString()+"'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        memberID = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("_id")));
        saveImgDatabase(uri);
        startActivity(intent);
    }

    public void addFamilyMemberPhoto(View view){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent, SET_URI);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SET_URI && resultCode == Activity.RESULT_OK){
            uri = data.getData().toString();
        }
    }


    public void saveImgDatabase(String uri){
        SQLiteOpenHelper familyDataBaseHelper = new FamilyDataBaseHelper(this);
        SQLiteDatabase db =familyDataBaseHelper.getReadableDatabase();

        ContentValues imageValues = new ContentValues();

        imageValues.put("member_id", memberID);
        imageValues.put("uri", uri);

        if(uri!=null) {
            db.insert("photo", null, imageValues);
            Toast.makeText(this,"Dodano obraz do bazy",Toast.LENGTH_LONG).show();
        }

        db.close();
    }
}
