package com.example.kasia.mobilefamily;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class Messages extends AppCompatActivity {

    String[] groups = new String[] {"rodzeństwo", "bracia", "dziadki", "kuzynostwo","organizacja rocznicy"};
    String[] people = new String[] {"Janina Kowalska", "Jan Kowalski", "Kazimierz Nowak", "Katarzyna Jozwiak"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        loadGroups();



        TextView groupsButton = (TextView) findViewById(R.id.groups);
        groupsButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                loadGroups();
            }
        });


        TextView peopleButton = (TextView) findViewById(R.id.people);
        peopleButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                loadPeople();
            }
        });
    }

    public void loadGroups(){
        ListView groupsListView = (ListView) findViewById(R.id.listView2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.photos_list_item,R.id.textView6, groups);
        groupsListView.setAdapter(adapter);
        groupsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openMessager(groups[i]);
            }
        });
    }

    public void loadPeople(){
        ListView groupsListView = (ListView) findViewById(R.id.listView2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.photos_list_item,R.id.textView6, people);
        groupsListView.setAdapter(adapter);
        groupsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openMessager(people[i]);
            }
        });

    }

    public void openMessager(String groupName){

    }
}
