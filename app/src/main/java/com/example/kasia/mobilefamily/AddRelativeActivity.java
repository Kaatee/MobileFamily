package com.example.kasia.mobilefamily;

import android.content.ContentValues;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;



public class AddRelativeActivity extends AddFamilyMemberActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_family_member);

        Spinner spinner = findViewById(R.id.typeOfRelationSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.relationTypesValues, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }
    @Override
    public void addFamilyMember(View view) {
        super.addFamilyMember(view);

        SQLiteOpenHelper familyDataBaseHelper = new FamilyDataBaseHelper(this);
        SQLiteDatabase db  =familyDataBaseHelper.getReadableDatabase();

        //adding relation
        Spinner spinner = findViewById(R.id.typeOfRelationSpinner);
        String relationType = spinner.getSelectedItem().toString();
        long relationNo = spinner.getSelectedItemId();
        EditText nameText = findViewById(R.id.nameText);

        Cursor cursorId = db.rawQuery("SELECT  count(*) as counter from relationship", null);
        int currentId=0;
        if (cursorId .moveToFirst()){
            do{
                currentId= cursorId .getInt(cursorId .getColumnIndex("counter"));
                currentId+=1;
            }while(cursorId.moveToNext());
        }
        cursorId.close();


        ContentValues relationValues = new ContentValues();
        relationValues.put("_id", currentId);
        relationValues.put("name", relationType);
        relationValues.put("person1_id", person1ID);
        relationValues.put("person2_id",memberID);

        db.insert("relationship", null, relationValues);
        Toast.makeText(this,"Relację"+ nameText.getText().toString() ,Toast.LENGTH_LONG).show();
        Log.d("---","Dodano relację");

        //adding corresponding relation (mum-son --> son-mam)
        Resources res = getResources();
        String[] items = res.getStringArray(R.array.relationTypesCorrespondingValues);
        String nameCor = items[(int)relationNo];

        ContentValues correspondingRelation = new ContentValues();
        correspondingRelation.put("_id", currentId+1);
        correspondingRelation.put("name", nameCor);
        correspondingRelation.put("person1_id", memberID);
        correspondingRelation.put("person2_id",person1ID);

        db.insert("relationship", null, correspondingRelation);

    }



}

