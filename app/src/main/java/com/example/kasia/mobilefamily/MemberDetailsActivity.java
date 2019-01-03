package com.example.kasia.mobilefamily;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class MemberDetailsActivity extends AppCompatActivity {
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_details);

        Bundle extras = getIntent().getExtras();
        id = extras.getInt("id");


        SQLiteOpenHelper familyDataBaseHelper = new FamilyDataBaseHelper(this);
        SQLiteDatabase db  =familyDataBaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM person WHERE _id IS "+String.valueOf(id), null);

        showPhoto(id);

        TextView name =  findViewById(R.id.nameTextView);
        TextView birthdate =  findViewById(R.id.birthdateTextView);
        TextView namedate =  findViewById(R.id.namedateTextView);
        TextView phoneNumbeText = findViewById(R.id.phoneNumberTextView);
        TextView emailText = findViewById(R.id.emailTextView);
        TextView weddingDateText = findViewById(R.id.weddingDateTextView);
        TextView addressText = findViewById(R.id.addressTextView);
        TextView workPlaceText = findViewById(R.id.workplaceTextView);
        TextView dateOfDeathText = findViewById(R.id.dateOfDeathTextView);
        TextView familyNameText = findViewById(R.id.familyNameTextView);


        if(cursor.moveToNext()) {
            name.setText(cursor.getString(cursor.getColumnIndexOrThrow("name")) + " " + cursor.getString(cursor.getColumnIndexOrThrow("surname")));
            birthdate.setText(cursor.getString(cursor.getColumnIndexOrThrow("date_of_birth")));
            namedate.setText(cursor.getString(cursor.getColumnIndexOrThrow("date_of_name_day")));
            phoneNumbeText.setText(cursor.getString(cursor.getColumnIndexOrThrow("telephone_number")));
            emailText.setText(cursor.getString(cursor.getColumnIndexOrThrow("email")));
            weddingDateText.setText(cursor.getString(cursor.getColumnIndexOrThrow("wedding_date")));
            familyNameText.setText(cursor.getString(cursor.getColumnIndexOrThrow("family_name")));
            addressText.setText(cursor.getString(cursor.getColumnIndexOrThrow("address")));
            workPlaceText.setText(cursor.getString(cursor.getColumnIndexOrThrow("place_of_work")));
            dateOfDeathText.setText(cursor.getString(cursor.getColumnIndexOrThrow("date_of_death")));
        }

        Button addMemberButton =  findViewById(R.id.button3);
        addMemberButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                openAddFamilyMemberActivity();
            }
        });


        //fetch all relatives list
        Cursor relativesCursor = db.rawQuery("SELECT  * FROM relationship WHERE person1_id = " + id, null);
        MemberDetailsActivity.MyCursorAdapter myAdapter;
        ListView listView = findViewById(R.id.relativesList);

        ListView list = findViewById(R.id.relativesList);
        int listLen = relativesCursor.getCount();

        final float scale = getResources().getDisplayMetrics().density;
        int listHeight = (int) ((90 * listLen*scale)+0.5f);

        ViewGroup.LayoutParams params = list.getLayoutParams();
        params.height = listHeight;

        if(relativesCursor!=null) {
            Log.d("---","Tworze adapter");
            myAdapter = new MemberDetailsActivity.MyCursorAdapter(this, relativesCursor);
            listView.setAdapter(myAdapter);
        }
    }

    public void openAddFamilyMemberActivity(){
        Intent intent = new Intent(this, AddRelativeActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    public void showPhoto(int memberId){
        ImageView img = findViewById(R.id.memberPhotoImageView);

        SQLiteOpenHelper familyDataBaseHelper = new FamilyDataBaseHelper(this);
        SQLiteDatabase db =familyDataBaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM  photo where member_id is  " + memberId, null);

        if (cursor !=null ) {
            if (cursor.moveToFirst()) {
                do {
                    try{

                        InputStream inputStream = this.getContentResolver().openInputStream(Uri.parse(cursor.getString(cursor.getColumnIndexOrThrow("uri"))));
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

        cursor.close();
        db.close();


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
            //create personId - nameOfRelationship pair
            Pair<Integer, String> pair ;
            Integer id1 = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("person2_id")));
            String name1 =cursor.getString(cursor.getColumnIndexOrThrow("name")) ;
            Log.d("---para",name1 + " id:"+String.valueOf(id1));
            pair = new Pair(id1, name1);

            //fetch person whose id is person2_id data (name,surname, photo)
            SQLiteOpenHelper familyDataBaseHelper = new FamilyDataBaseHelper(context);
            SQLiteDatabase db =familyDataBaseHelper.getReadableDatabase();
            Cursor cursor2 = db.rawQuery("SELECT  * FROM  person where _id = " + pair.first, null);

            TextView nameText = view.findViewById(R.id.nameTextView);
            cursor2.moveToNext();
            String name = cursor2.getString(cursor2.getColumnIndexOrThrow("name"));
            String surname = cursor2.getString(cursor2.getColumnIndexOrThrow("surname"));
            nameText.setText(name + " " + surname);


            TextView extraText = view.findViewById(R.id.extraTextView);
            extraText.setText("pokrewienstwo: " + pair.second);

            //MemberDetailsActivity.showPhoto(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("_id"))));
            ImageView img = view.findViewById(R.id.memberPhotoImageView);

            Cursor cursor3 = db.rawQuery("SELECT  * FROM  photo where member_id is  " + pair.first, null);

            if (cursor3 !=null ) {
                if (cursor3.moveToFirst()) {
                    do {
                        try{
                            InputStream inputStream = context.getContentResolver().openInputStream(Uri.parse(cursor3.getString(cursor3.getColumnIndexOrThrow("uri"))));
                            Bitmap bmp = BitmapFactory.decodeStream(inputStream);
                            BitmapDrawable ob = new BitmapDrawable(getResources(), bmp);
                            img.setBackground(ob);
                        }
                        catch (FileNotFoundException e){
                            e.printStackTrace();
                        }
                    } while (cursor3.moveToNext());
                }
            }
            cursor2.close();
            cursor3.close();
            db.close();
        }

    }

}
