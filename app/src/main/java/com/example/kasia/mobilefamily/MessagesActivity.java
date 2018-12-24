package com.example.kasia.mobilefamily;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MessagesActivity extends AppCompatActivity {

    String[] groups = new String[] {"rodzeństwo", "bracia", "dziadki", "kuzynostwo","organizacja rocznicy"};
    String[] people = new String[] {"Janina Kowalska", "Jan Kowalski", "Kazimierz Nowak", "Katarzyna Jozwiak"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        loadGroups();

        TextView groupsButton =  findViewById(R.id.groups);
        groupsButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                loadGroups();
            }
        });


        TextView peopleButton =  findViewById(R.id.people);
        peopleButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                loadPeople();
            }
        });
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
