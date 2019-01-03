package com.example.kasia.mobilefamily;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MessageContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_content);

        Bundle extras = getIntent().getExtras();
        String name = extras.getString("groupName");

        TextView text = findViewById(R.id.textView35);
        text.setText(name);
    }
}
