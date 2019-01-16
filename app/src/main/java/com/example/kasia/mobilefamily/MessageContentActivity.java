package com.example.kasia.mobilefamily;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MessageContentActivity extends AppCompatActivity {
    private RecyclerView mMessageRecycler;
    private MessageListAdapter mMessageAdapter;
    private Context context=this;
    private List<Message> messageList = new ArrayList<>();
    private String name;
    private int senderIdx=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_content);
        Bundle extras = getIntent().getExtras();
        if (extras.size()==1){
            name = extras.getString("userName");
        }
        else {
            name = extras.getString("userName");
            senderIdx = extras.getInt("userIdx");
        }
        //user

        TextView text = findViewById(R.id.userNameText);
        text.setText(name);

        //create naive(start) message list
        messageList.add(new Message("Wysłana wiadomość - TEST",1 , returnCurrentTime()));
        messageList.add(new Message("Odebrana wiadomość - TEST",2 , returnCurrentTime()));

        mMessageRecycler = findViewById(R.id.reyclerview_message_list);
        RecyclerView.LayoutManager layoutManager =new LinearLayoutManager(getApplicationContext());
        mMessageRecycler.setLayoutManager(layoutManager);
        mMessageAdapter = new MessageListAdapter(context, messageList);
        mMessageRecycler.setAdapter(mMessageAdapter);

    }

    public void sendMessage(View view){
       TextView message= findViewById(R.id.edittext_chatbox);
       String messageContent = message.getText().toString();
       messageList.add(new Message(messageContent,1,returnCurrentTime()));
       messageList.add(new Message("Potwierdzam odbiór wiadomości",senderIdx,returnCurrentTime()));
       message.setText(null);
       mMessageAdapter.notifyDataSetChanged();
    }


    private String returnCurrentTime(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

}
