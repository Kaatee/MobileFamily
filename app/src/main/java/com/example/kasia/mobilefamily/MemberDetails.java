package com.example.kasia.mobilefamily;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MemberDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_details);

        Bundle extras = getIntent().getExtras();
        String name = extras.getString("name");

        TextView textView = (TextView) findViewById(R.id.name);
        textView.setText(name);
    }
}
