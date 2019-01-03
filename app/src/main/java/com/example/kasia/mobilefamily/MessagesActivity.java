package com.example.kasia.mobilefamily;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MessagesActivity extends AppCompatActivity {

    String[] groups = new String[] {"rodzeństwo", "bracia", "dziadki", "kuzynostwo","organizacja rocznicy"};
    String[] people;
    TextView groupsButton;
    TextView peopleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        setPeopleList();
        loadGroups();

        peopleButton =  findViewById(R.id.people);
        groupsButton = findViewById(R.id.groups);

        groupsButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                loadGroups();
                groupsButton.setBackgroundColor(getResources().getColor(R.color.colorButtons));
                groupsButton.setTextColor(Color.WHITE);
                peopleButton.setBackgroundColor(getResources().getColor(R.color.colorBackground));
                peopleButton.setTextColor(getResources().getColor(R.color.colorButtonsCorners));
            }
        });
        groupsButton.setBackgroundColor(getResources().getColor(R.color.colorButtons));
        groupsButton.setTextColor(Color.WHITE);

        peopleButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                loadPeople();
            }
        });
    }

    public void setPeopleList(){
        SQLiteOpenHelper familyDataBaseHelper = new FamilyDataBaseHelper(this);
        SQLiteDatabase db  =familyDataBaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM person", null);
        if(cursor!=null) {
            people = new String[cursor.getCount()];
            cursor.moveToFirst();

            int i = 0;
            while (!cursor.isAfterLast()) {
                String data = "";
                data += cursor.getString(cursor.getColumnIndexOrThrow("name"));
                data += " ";
                data += cursor.getString(cursor.getColumnIndexOrThrow("surname"));
                people[i] = data;
                cursor.moveToNext();
                i++;
            }
        }
    }

    public void loadGroups(){


        ListView groupsListView = findViewById(R.id.listView2);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, R.layout.photos_list_item,R.id.textView6, groups);
        groupsListView.setAdapter(adapter);
        groupsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openMessager(groups[i]);
            }
        });
    }

    public void loadPeople(){
        peopleButton.setBackgroundColor(getResources().getColor(R.color.colorButtons));
        peopleButton.setTextColor(Color.WHITE);
        groupsButton.setBackgroundColor(getResources().getColor(R.color.colorBackground));
        groupsButton.setTextColor(getResources().getColor(R.color.colorButtonsCorners));

        ListView groupsListView = findViewById(R.id.listView2);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, R.layout.photos_list_item,R.id.textView6, people);
        groupsListView.setAdapter(adapter);

        groupsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openMessager(people[i]);
            }
        });

    }

    public void openMessager(String groupName){
        Intent intent = new Intent(this, MessageContentActivity.class);
        Bundle dataBundle = new Bundle();
        dataBundle.putString("groupName", groupName);
        intent.putExtras(dataBundle);
        startActivity(intent);

    }
}
