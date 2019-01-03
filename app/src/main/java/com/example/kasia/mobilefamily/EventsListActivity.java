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
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class EventsListActivity extends AppCompatActivity {


    private ArrayList<Integer> ids = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        ListView eventsListView = findViewById(R.id.listViewPhotos);
        SQLiteOpenHelper familyDataBaseHelper = new FamilyDataBaseHelper(this);
        SQLiteDatabase db =familyDataBaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM event order by date asc ", null);

       MyCursorAdapter myAdapter = new MyCursorAdapter(this, cursor);
       eventsListView.setAdapter(myAdapter);

        cursor.moveToFirst();
        ids.clear();
        while(!cursor.isAfterLast()) {
            ids.add(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("_id")))); //add the item
            cursor.moveToNext();
        }

        eventsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int idx, long l) {
                openEventPhotoActivity(ids.get(idx));
            }
        });


    }


    public void openEventPhotoActivity(int eventId){
        Intent intent = new Intent(this, EventPhotosActivity.class);
        Bundle dataBundle = new Bundle();
        dataBundle.putInt("eventId", eventId);
        intent.putExtras(dataBundle);
        startActivity(intent);
    }



    public class MyCursorAdapter extends CursorAdapter {
        public MyCursorAdapter(Context context, Cursor cursor) {
            super(context, cursor, 0);
        }

        // The newView method is used to inflate a new view and return it,
        // you don't bind any data to the view at this point.
        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(R.layout.event_list_item, parent, false);
        }

        // The bindView method is used to bind all data to a given view
        // such as setting the text on a TextView.
        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            TextView event = view.findViewById(R.id.textViewEventName);
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            // String surname = cursor.getString(cursor.getColumnIndexOrThrow("surname"));
            event.setText(name);
        }
    }
}
