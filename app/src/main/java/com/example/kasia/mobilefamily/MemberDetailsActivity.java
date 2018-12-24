package com.example.kasia.mobilefamily;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MemberDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_details);

        Bundle extras = getIntent().getExtras();
        String name = extras.getString("name");

        TextView textView =  findViewById(R.id.name);
        textView.setText(name);


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
