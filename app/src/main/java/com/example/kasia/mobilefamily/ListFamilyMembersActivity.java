package com.example.kasia.mobilefamily;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListFamilyMembersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_member_data);

        SQLiteOpenHelper familyDataBaseHelper = new FamilyDataBaseHelper(this);
        SQLiteDatabase db  =familyDataBaseHelper.getReadableDatabase();
        //Cursor cursor = db.query("person", new String[] {"name", "surname"},null,null,null,null,null );
        Cursor cursor = db.rawQuery("SELECT  * FROM person", null);
        ListView eventsListView =  findViewById(R.id.familyMembersList);

        TodoCursorAdapter todoAdapter = new TodoCursorAdapter(this, cursor);

        eventsListView.setAdapter(todoAdapter);

        Button addMemberButton = findViewById(R.id.button2);
        addMemberButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                openAddFamilyMemberActivity();
            }
        });

        todoAdapter.changeCursor(cursor);

    }

    public void openFamilyMemberDetails(String name){
        Intent intent = new Intent(this, MemberDetailsActivity.class);
        Bundle dataBundle = new Bundle();
        dataBundle.putString("name", name);
        intent.putExtras(dataBundle);
        startActivity(intent);
    }

    public void openAddFamilyMemberActivity(){
        Intent intent = new Intent(this, AddFamilyMemberActivity.class);
        startActivity(intent);
    }

    public class TodoCursorAdapter extends CursorAdapter {
        public TodoCursorAdapter(Context context, Cursor cursor) {
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
            TextView nameText = view.findViewById(R.id.name);
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String surname = cursor.getString(cursor.getColumnIndexOrThrow("surname"));
            nameText.setText(name + " "+surname);
        }
    }
}
