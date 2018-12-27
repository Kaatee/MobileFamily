package com.example.kasia.mobilefamily;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListFamilyMembersActivity extends AppCompatActivity {
    public Context context = this;
    public ArrayList<Integer> ids = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_member_data);


        //spinner (sorting value) handle
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sortingValues, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                SQLiteOpenHelper familyDataBaseHelper = new FamilyDataBaseHelper(context);
                SQLiteDatabase db  =familyDataBaseHelper.getReadableDatabase();

                Resources res = getResources();
                String[] items = res.getStringArray(R.array.sortingValues);
                String sortByStr = items[position];
                String sortBy ="";
                if(sortByStr.equals("Imie")) {
                    sortBy="name";
                }
                if(sortByStr.equals("Nazwisko")) {
                    sortBy="surname";
                }
                if(sortByStr.equals("Data urodzenia")) {
                    sortBy="date_of_birth";
                }

                if(sortByStr.equals("Nazwisko panienskie")) {
                    sortBy="family_name";
                }

                Cursor cursor = db.rawQuery("SELECT  * FROM person ORDER BY " + sortBy, null);

                ListView eventsListView =  findViewById(R.id.familyMembersList);

                MyCursorAdapter myAdapter = new MyCursorAdapter(context, cursor);
                eventsListView.setAdapter(myAdapter);

                //make list od ids
                cursor.moveToFirst();
                while(!cursor.isAfterLast()) {
                    ids.add(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("_id")))); //add the item
                    cursor.moveToNext();
                }

                eventsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int idx, long l) {
                        openFamilyMemberDetails(ids.get(idx));
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                //database handle
                SQLiteOpenHelper familyDataBaseHelper = new FamilyDataBaseHelper(context);
                SQLiteDatabase db  =familyDataBaseHelper.getReadableDatabase();
                Cursor cursor = db.rawQuery("SELECT  * FROM person", null);
                ListView eventsListView =  findViewById(R.id.familyMembersList);

                MyCursorAdapter myAdapter = new MyCursorAdapter(context, cursor);
                eventsListView.setAdapter(myAdapter);

                //make list od ids
                cursor.moveToFirst();
                while(!cursor.isAfterLast()) {
                    ids.add(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("_id")))); //add the item
                    cursor.moveToNext();
                }

                eventsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int idx, long l) {
                        openFamilyMemberDetails(ids.get(idx));
                    }
                });
            }

        });


        Button addMemberButton = findViewById(R.id.button2);
        addMemberButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                openAddFamilyMemberActivity();
            }
        });


    }


    public void openFamilyMemberDetails(Integer id){
        Intent intent = new Intent(this, MemberDetailsActivity.class);
        Bundle dataBundle = new Bundle();
        dataBundle.putInt("id", id);
        intent.putExtras(dataBundle);
        startActivity(intent);
    }

    public void openAddFamilyMemberActivity(){
        Intent intent = new Intent(this, AddFamilyMemberActivity.class);
        startActivity(intent);
    }

    public class MyCursorAdapter extends CursorAdapter {
        public MyCursorAdapter(Context context, Cursor cursor) {
            super(context, cursor, 0);
        }

        // The newView method is used to inflate a new view and return it,
        // you don't bind any data to the view at this point.
        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(R.layout.family_member_item, parent, false);
        }

        // The bindView method is used to bind all data to a given view
        // such as setting the text on a TextView.
        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            TextView nameText = view.findViewById(R.id.nameTextView);
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String surname = cursor.getString(cursor.getColumnIndexOrThrow("surname"));
            nameText.setText(name + " "+surname);
        }
    }
}
