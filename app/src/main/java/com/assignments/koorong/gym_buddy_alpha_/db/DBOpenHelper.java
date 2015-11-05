package com.assignments.koorong.gym_buddy_alpha_.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.assignments.koorong.gym_buddy_alpha_.User;

/**
 * Created by Peter Liu on 11/4/2015.
 */
public class DBOpenHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "Users.db";
    private static final int DATABASE_VERSION = 1;

    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(UserDataSource.CREATE_LOGINUSER_TABLE);
        sqLiteDatabase.execSQL(UserDataSource.CREATE_MATCHUSER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + UserDataSource.APPUSER_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + UserDataSource.MATCHES_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
