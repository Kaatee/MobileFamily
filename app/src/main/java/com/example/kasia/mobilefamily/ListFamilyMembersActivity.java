package com.example.kasia.mobilefamily;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class ListFamilyMembersActivity extends AppCompatActivity {
    public Context context = this;
    public ArrayList<Integer> ids = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_list_members);


        //spinner (sorting value) handle
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sortingValues, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                SQLiteOpenHelper familyDataBaseHelper = new FamilyDataBaseHelper(context);
                SQLiteDatabase db  =familyDataBaseHelper.getReadableDatabase();

                Resources res = getResources();
                String[] items = res.getStringArray(R.array.sortingValues);
                String sortByStr = items[position];
                String sortBy ="";
                if(sortByStr.equals("Imie")) {
                    sortBy="name";
                }
                if(sortByStr.equals("Nazwisko")) {
                    sortBy="surname";
                }
                if(sortByStr.equals("Data urodzenia")) {
                    sortBy="date_of_birth";
                }

                if(sortByStr.equals("Nazwisko panienskie")) {
                    sortBy="family_name";
                }

                Cursor cursor = db.rawQuery("SELECT  * FROM person ORDER BY " + sortBy, null);

                ListView eventsListView =  findViewById(R.id.familyMembersList);

                MyCursorAdapter myAdapter = new MyCursorAdapter(context, cursor);
                eventsListView.setAdapter(myAdapter);

                //make list od ids
                cursor.moveToFirst();
                while(!cursor.isAfterLast()) {
                    ids.add(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("_id")))); //add the item
                    cursor.moveToNext();
                }

                eventsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int idx, long l) {
                        openFamilyMemberDetails(ids.get(idx));
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                //database handle
                SQLiteOpenHelper familyDataBaseHelper = new FamilyDataBaseHelper(context);
                SQLiteDatabase db  =familyDataBaseHelper.getReadableDatabase();
                Cursor cursor = db.rawQuery("SELECT  * FROM person", null);
                ListView eventsListView =  findViewById(R.id.familyMembersList);

                MyCursorAdapter myAdapter = new MyCursorAdapter(context, cursor);
                eventsListView.setAdapter(myAdapter);

                //make list od ids
                cursor.moveToFirst();
                while(!cursor.isAfterLast()) {
                    ids.add(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("_id")))); //add the item
                    cursor.moveToNext();
                }

                eventsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int idx, long l) {
                        openFamilyMemberDetails(ids.get(idx));
                    }
                });
            }

        });


        Button addMemberButton = findViewById(R.id.button2);
        addMemberButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                openAddFamilyMemberActivity();
            }
        });
    }


    public void openFamilyMemberDetails(Integer id){
        Intent intent = new Intent(this, MemberDetailsActivity.class);
        Bundle dataBundle = new Bundle();
        dataBundle.putInt("id", id);
        intent.putExtras(dataBundle);
        startActivity(intent);
    }

    public void openAddFamilyMemberActivity(){
        Intent intent = new Intent(this, AddFamilyMemberActivity.class);
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
            return LayoutInflater.from(context).inflate(R.layout.family_member_item, parent, false);
        }

        // The bindView method is used to bind all data to a given view
        // such as setting the text on a TextView.
        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            TextView nameText = view.findViewById(R.id.nameTextView);
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String surname = cursor.getString(cursor.getColumnIndexOrThrow("surname"));
            nameText.setText(name + " "+surname);


            TextView extraText = view.findViewById(R.id.extraTextView);
            String familyName = cursor.getString(cursor.getColumnIndexOrThrow("family_name"));
            if(familyName.length() == 0) familyName="-";
            String dateOfBirth = cursor.getString(cursor.getColumnIndexOrThrow("date_of_birth"));
            if(dateOfBirth.length() == 0) dateOfBirth="-";
            extraText.setText("ur. " + dateOfBirth +", z domu " + familyName);

            //MemberDetailsActivity.showPhoto(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("_id"))));
            ImageView img = view.findViewById(R.id.memberPhotoImageView);

            SQLiteOpenHelper familyDataBaseHelper = new FamilyDataBaseHelper(context);
            SQLiteDatabase db =familyDataBaseHelper.getReadableDatabase();
            int memberId = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("_id")));
            Cursor cursor2 = db.rawQuery("SELECT  * FROM  photo where member_id is  " + memberId, null);

            if (cursor2 !=null ) {
                if (cursor2.moveToFirst()) {
                    do {
                        try{
                            InputStream inputStream = context.getContentResolver().openInputStream(Uri.parse(cursor2.getString(cursor2.getColumnIndexOrThrow("uri"))));
                            Bitmap bmp = BitmapFactory.decodeStream(inputStream);
                            BitmapDrawable ob = new BitmapDrawable(getResources(), bmp);
                            img.setBackground(ob);
                        }
                        catch (FileNotFoundException e){
                            e.printStackTrace();
                        }
                    } while (cursor.moveToNext());
                }
            }

            cursor2.close();
            db.close();
        }

    }


}
