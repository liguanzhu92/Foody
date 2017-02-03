package com.example.guanzhuli.foody.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.Image;
import android.util.Log;

import java.io.FileNotFoundException;

/**
 * Created by Guanzhu Li on 1/13/2017.
 */

//Id, Name, quantity, total, category, date, address, mobile, status
public class DBHelper extends SQLiteOpenHelper {
    public static final String TABLENAME = "cart";
    public static final String ITEMID = "id";
    public static final String ITEMNAME = "name";
    public static final String QUANTITY = "quantity";
    public static final String PRICE = "price";
    public static final String CATEGORY = "category";
    public static final String IMAGEURL = "image";
//    public static final String DATE = "date";
//    public static final String ADDRESS = "address";
//    public static final String MOBILE = "mobile";
//    public static final String STATUS = "status";
    public static final int VERSION = 1;
    public static final String DATABASE_NAME = "myDb";


    private Context ctx = null;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.ctx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.e("sqlite", "create table");
        String createTable = " CREATE TABLE " + TABLENAME + "("
                + ITEMID + " INTEGER PRIMARY KEY, " + ITEMNAME + " TEXT, "
                + QUANTITY + " INTEGER, " + PRICE + " DECIMAL(10,2), " + CATEGORY + " TEXT, "+ IMAGEURL + " TEXT)";
        Log.e("DB", "create table query: " + createTable);
//                + DATE + " TEXT, " + ADDRESS + " TEXT,"+ MOBILE + " INTEGER, "+ STATUS + " INTEGER)";
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.v(DBHelper.class.getName(),
                "upgrading database from version "+ i + " to "
                        + i1 + ", which will destroy all old data");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
        onCreate(sqLiteDatabase);
    }
}
