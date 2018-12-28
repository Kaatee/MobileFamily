package com.example.kasia.mobilefamily;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class EventPhotosActivity extends AppCompatActivity {


    private static final int PICK_PHOTO_FOR_AVATAR = 0;
    private int eventId;
   // private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_photos);

        //getting valuesfrom bundle
        Bundle extras = getIntent().getExtras();
        eventId = extras.getInt("eventId");

        TextView textView = findViewById(R.id.textView7);
        textView.setText(showEventDetails(eventId));


    }

    public void addPhoto( View view){

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);
    }

    public void saveImgDatabase(Bitmap bmp){
        SQLiteOpenHelper familyDataBaseHelper = new FamilyDataBaseHelper(this);
        SQLiteDatabase db =familyDataBaseHelper.getReadableDatabase();
        ContentValues imageValues = new ContentValues();
        imageValues.put("event_id", eventId);
        imageValues.put("place", "test");
        imageValues.put("date","test");

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] bArray = bos.toByteArray();
        imageValues.put("content",bArray);
        db.insert("photo", null, imageValues);
        Toast.makeText(this,"Dodano obraz do bazy",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PHOTO_FOR_AVATAR && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }
            try {
                InputStream inputStream = this.getContentResolver().openInputStream(data.getData());
                Bitmap bmp = BitmapFactory.decodeStream(inputStream);
//                BitmapDrawable ob = new BitmapDrawable(getResources(), bmp);
//                ImageView img = findViewById(R.id.imageView);
//                img.setBackground(ob);
                saveImgDatabase(bmp);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            //Now you can do whatever you want with your inpustream, save it as file, upload to a server, decode a bitmap...
        }
    }

    public void showFromDB(View view){
        ImageView img = findViewById(R.id.imageView);
        byte[] bitmapdata=null;
        //img.setBackground(ob);
        SQLiteOpenHelper familyDataBaseHelper = new FamilyDataBaseHelper(this);
        SQLiteDatabase db =familyDataBaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM  photo where event_id is  " + eventId, null);
        if (cursor !=null) {
            if (cursor.moveToFirst()) {
                do {
                    Log.d("-----------------------------jestem", "hiih");
                    bitmapdata = cursor.getBlob(cursor.getColumnIndex("content"));

                    // do what ever you want here
                } while (cursor.moveToNext());
            }
        }
        cursor.close();

        Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
        BitmapDrawable ob = new BitmapDrawable(getResources(), bitmap);
        img.setBackground(ob);
    }

    private String showEventDetails(int eventID){
        String result="Zdjęcia: ";
        SQLiteOpenHelper familyDataBaseHelper = new FamilyDataBaseHelper(this);
        SQLiteDatabase db =familyDataBaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM event where _id is " + String.valueOf(eventID), null);

        if (cursor.moveToFirst()){
            do{
                result += cursor.getString(cursor.getColumnIndex("name"));
                result +=", data: " + cursor.getString(cursor.getColumnIndex("date"));
                // do what ever you want here
            }while(cursor.moveToNext());
        }
        cursor.close();

        return result;
    }
}
