package com.example.kasia.mobilefamily;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MessageContentActivity extends AppCompatActivity {
    private RecyclerView mMessageRecycler;
    private MessageListAdapter mMessageAdapter;
    private Context context=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_message_content);

        Bundle extras = getIntent().getExtras();
        String name = extras.getString("groupName");

//        TextView text = findViewById(R.id.textView35);
//        text.setText(name);

        setContentView(R.layout.activity_message_content);

        //create naive message list
        List<Message> messageList = new ArrayList<>();
        messageList.add(new Message("wyslana wiadomosc",1 , System.currentTimeMillis()));
        messageList.add(new Message("odebrana wiadomosc",2 , System.currentTimeMillis()));

        mMessageRecycler = findViewById(R.id.reyclerview_message_list);
        RecyclerView.LayoutManager layoutManager =new LinearLayoutManager(getApplicationContext());
        mMessageRecycler.setLayoutManager(layoutManager);
        mMessageAdapter = new MessageListAdapter(context, messageList);
        mMessageRecycler.setAdapter(mMessageAdapter);


    }


}
