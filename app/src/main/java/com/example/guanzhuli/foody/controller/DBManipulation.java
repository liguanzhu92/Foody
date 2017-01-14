package com.example.guanzhuli.foody.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Guanzhu Li on 1/13/2017.
 */
public class DBManipulation {
    private DBHelper mDBHelper;
    private SQLiteDatabase mSQLiteDatabase;
    private Context mContext;
    private static String mDBName = "";
    private static DBManipulation mInstance;

    public DBManipulation(Context context, String dbname) {
        mDBName = dbname;
        this.mContext = context;
        mDBHelper = new DBHelper(context, dbname);
        mSQLiteDatabase = mDBHelper.getWritableDatabase();
    }

    public static DBManipulation getInstance(Context context, String dbname) {
        if (mInstance == null || !mDBName.equals(dbname)) {
            mInstance = new DBManipulation(context, dbname);
        }
        return mInstance;
    }


}
