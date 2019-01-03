package com.example.kasia.mobilefamily;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class FamilyDataBaseHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "MobileFamilyDB";

    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DB_EVENT_CREATE = "create table event( _id integer primary key AUTOINCREMENT,name text not null,place text not null, description text , date text not null)";
    private static final String DB_PHOTO_CREATE =  "create table photo( _id integer primary key AUTOINCREMENT, uri text not null  , event_id integer , member_id integer, FOREIGN KEY(event_id) REFERENCES event(_id))";
    private static final String DB_PERSON_CREATE= "create table person( _id integer primary key AUTOINCREMENT, name text not null , surname text not null , date_of_birth text , " +
            " date_of_death text, date_of_name_day text, telephone_number text , email text , address text , place_of_work text  , family_name text , wedding_date text)";
    private static final String DB_RELATIONSHIP_CREATE= "create table relationship(_id integer not null,name text not null , person1_id integer not null , person2_id integer not null," +
            "start_date text , description text , primary key(_id,person1_id,person2_id), foreign key (person1_id) references person(_id),foreign key (person2_id) references person(_id))";


    public FamilyDataBaseHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Method is called during creation of the database
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DB_EVENT_CREATE);
        database.execSQL(DB_PHOTO_CREATE);
        database.execSQL(DB_PERSON_CREATE);
        database.execSQL(DB_RELATIONSHIP_CREATE);
    }

    // Method is called during an upgrade of the database,
    @Override
    public void onUpgrade(SQLiteDatabase database,int oldVersion,int newVersion){
//        Log.w(FamilyDataBaseHelper .class.getName(),
//                "Upgrading database from version " + oldVersion + " to "
//                        + newVersion + ", which will destroy all old data");
        // database.execSQL("DROP TABLE IF EXISTS MyEmployees");
        onCreate(database);
    }
}