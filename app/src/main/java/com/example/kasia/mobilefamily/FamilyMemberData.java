package com.example.kasia.mobilefamily;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class FamilyMemberData extends AppCompatActivity {

    String[] familyMembers = new String[] {"Janina Kowalska", "Jan Kowalski", "Kazimierz Nowak", "Katarzyna Jozwiak"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_member_data);

        ListView eventsListView = (ListView) findViewById(R.id.familyMembersList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.family_member_item,R.id.name, familyMembers);
        eventsListView.setAdapter(adapter);
        eventsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openFamilyMemberDetails(familyMembers[i]);
            }
        });


        Button addMemberButton = (Button) findViewById(R.id.button2);
        addMemberButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                openAddFamilyMemberActivity();
            }
        });

    }

    public void openFamilyMemberDetails(String name){
        Intent intent = new Intent(this, MemberDetails.class);
        Bundle dataBundle = new Bundle();
        dataBundle.putString("name", name);
        intent.putExtras(dataBundle);
        startActivity(intent);
    }

    public void openAddFamilyMemberActivity(){
        Intent intent = new Intent(this, AddFamilyMember.class);
        startActivity(intent);
    }
}
