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
import android.widget.CalendarView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class CalendarActivity extends AppCompatActivity {


    private Calendar calendar=new GregorianCalendar();
    private     ArrayList<Integer> ids = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        updateUpcomingEvents();

        CalendarView v = findViewById(R.id.calendarView);
        v.setOnDateChangeListener( new CalendarView.OnDateChangeListener() {
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                calendar = new GregorianCalendar( year, month, dayOfMonth );
                updateListView(calendar);
            }
        });

    }

    private void updateListView(Calendar x){
        ListView eventsListView = findViewById(R.id.listView);
        SQLiteOpenHelper familyDataBaseHelper = new FamilyDataBaseHelper(this);
        SQLiteDatabase db =familyDataBaseHelper.getReadableDatabase();
        String myDate = "\""+ format(x) +"\"" ;
        Cursor cursor = db.rawQuery("SELECT  * FROM event where date is "+ myDate, null);

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

    private void updateUpcomingEvents(){
        ListView eventsListView = findViewById(R.id.listView);
        SQLiteOpenHelper familyDataBaseHelper = new FamilyDataBaseHelper(this);
        SQLiteDatabase db =familyDataBaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM event order by date asc", null);

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
        Intent intent = new Intent(this, EventDetailsActivity.class);
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



    public void addEvent(View view){
        Intent intent = new Intent(this,AddEventActivity.class);
        intent.putExtra("date", format(calendar));
        startActivity(intent);
    }


    public void showUpcomingEvents(View view ){
        updateUpcomingEvents();
    }


    public static String format(Calendar calendar){
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        fmt.setCalendar(calendar);
        String dateFormatted = fmt.format(calendar.getTime());
        return dateFormatted;
    }
}

