package com.assignments.koorong.gym_buddy_alpha_.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.assignments.koorong.gym_buddy_alpha_.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter Liu on 11/4/2015.
 */
public class UserDataSource {

    private SQLiteDatabase db;
    private SQLiteOpenHelper dbHelper;

    public static final String APPUSER_TABLE_NAME = "Login";
    public static final String MATCHES_TABLE_NAME = "Matches";

    public static final String ID_COLUMN = "_id";
    public static final int ID_COLUMN_POSITION = 0;

    public static final String FIRSTNAME_COLUMN = "fName";
    public static final int FIRSTNAME_COLUMN_POSITION = 1;

    public static final String LASTNAME_COLUMN = "lName";
    public static final int LASTNAME_COLUMN_POSITION = 2;

    public static final String CREATE_LOGINUSER_TABLE =
            "CREATE TABLE " + APPUSER_TABLE_NAME + " (" +
                    ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    FIRSTNAME_COLUMN + " TEXT, " +
                    LASTNAME_COLUMN + " TEXT)";

    public static final String CREATE_MATCHUSER_TABLE =
            "CREATE TABLE " + MATCHES_TABLE_NAME + " (" +
                    ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    FIRSTNAME_COLUMN + " TEXT, " +
                    LASTNAME_COLUMN + " TEXT)";

    public UserDataSource(Context context){
        dbHelper = new DBOpenHelper(context);
    }

    public User getAppUser(){
        User appUser = new User();
        String select = "SELECT * FROM " + APPUSER_TABLE_NAME;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(select, null);
        if(cursor.moveToFirst()){
            do{
                appUser.setName(cursor.getString(1));
            }while (cursor.moveToNext());
        }
        return appUser;
    }

    public List<User> getMatches(){
        List<User> matchList = new ArrayList<>();
        String select = "SELECT * FROM " + MATCHES_TABLE_NAME;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(select, null);
        if(cursor.moveToFirst()){
            do{
                User match = new User();
                match.setName(cursor.getString(1));
                matchList.add(match);
            }while(cursor.moveToNext());
        }
        return matchList;
    }

    public void setAppUser(String fName, String lName){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String insert = "INSERT INTO " + APPUSER_TABLE_NAME + " ('fName', 'lName') VALUES ('" + fName + "', '" + lName + "')";
        db.rawQuery(insert, null);
    }

}
