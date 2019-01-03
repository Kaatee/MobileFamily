package com.example.kasia.mobilefamily;

import android.app.Activity;
import android.content.ContentValues;
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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class EventPhotosActivity extends AppCompatActivity {


    private static final int PICK_PHOTO_FOR_AVATAR = 0;
    private int eventId;

//    private final String image_titles[] = {
//            "Img1",
//            "Img2",
//            "Img3",
//            "Img4",
//    };

    private final Integer image_ids[] = {
            R.drawable.img1,
            R.drawable.img2,
            R.drawable.img3,
            R.drawable.img4,
    };


    private ArrayList<CreateList> prepareData(){

        ArrayList<CreateList> theimage = new ArrayList<>();
        for(int i = 0; i< image_ids.length; i++){
            CreateList createList = new CreateList();
            //createList.setImage_title(image_titles[i]);
            createList.setImage_ID(image_ids[i]);
            theimage.add(createList);
        }
        return theimage;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_photos);

        Bundle extras = getIntent().getExtras();
        eventId = extras.getInt("eventId");

        TextView textView = findViewById(R.id.textView7);
        textView.setText(showEventDetails(eventId));

       // setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.imagegallery);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<CreateList> createLists = prepareData();
        MyAdapter adapter = new MyAdapter(getApplicationContext(), createLists);
        recyclerView.setAdapter(adapter);


    }

//    public void addPhoto( View view){
//        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        intent.setType("*/*");
//        startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);
//    }
//
//    public void saveImgDatabase(String uri){
//        SQLiteOpenHelper familyDataBaseHelper = new FamilyDataBaseHelper(this);
//        SQLiteDatabase db =familyDataBaseHelper.getReadableDatabase();
//
//        ContentValues imageValues = new ContentValues();
//        imageValues.put("event_id", eventId);
//        imageValues.put("uri", uri);
//        db.insert("photo", null, imageValues);
//
//        Toast.makeText(this,"Dodano obraz do bazy",Toast.LENGTH_LONG).show();
//        db.close();
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == PICK_PHOTO_FOR_AVATAR && resultCode == Activity.RESULT_OK) {
//            if (data == null) {
//                //Display an error
//                return;
//            }
//            try {
//                String uri = data.getData().toString();
//                saveImgDatabase(uri);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//
//    public void showFromDB(View view){
//       // ImageView img = findViewById(R.id.imageView);
//        SQLiteOpenHelper familyDataBaseHelper = new FamilyDataBaseHelper(this);
//        SQLiteDatabase db =familyDataBaseHelper.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT  * FROM  photo where event_id is  " + eventId, null);
//
//        if (cursor !=null ) {
//            if (cursor.moveToFirst()) {
//                do {
//                    try{
//
//                        InputStream inputStream = this.getContentResolver().openInputStream(Uri.parse(cursor.getString(cursor.getColumnIndexOrThrow("uri"))));
//                        Bitmap bmp = BitmapFactory.decodeStream(inputStream);
//                        BitmapDrawable ob = new BitmapDrawable(getResources(), bmp);
//                      //  img.setBackground(ob);
//
//                    }
//                    catch (FileNotFoundException e){
//                        e.printStackTrace();
//                    }
//                } while (cursor.moveToNext());
//            }
//        }
//
//        cursor.close();
//        db.close();
//
//
//    }
//
    private String showEventDetails(int eventID){
        String result="Zdjęcia: ";
        SQLiteOpenHelper familyDataBaseHelper = new FamilyDataBaseHelper(this);
        SQLiteDatabase db =familyDataBaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM event where _id is " + String.valueOf(eventID), null);

        if (cursor.moveToFirst()){
            do{
                result += cursor.getString(cursor.getColumnIndex("name"));
                result +=", data: " + cursor.getString(cursor.getColumnIndex("date"));
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return result;
    }
}
