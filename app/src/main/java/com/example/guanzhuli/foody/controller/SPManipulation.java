package com.example.guanzhuli.foody.controller;
// Done by Lily.

// Xiao: Implemented clear function.

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Guanzhu Li on 1/13/2017.
 */
public class SPManipulation {
    public static final String PREFS_NAME = "USER_INFO";
    public static final String PREFS_KEY_NAME = "NAME";
    public static final String PREFS_KEY_MOBILE = "MOBILE";
    public static final String PREFS_KEY_PWD = "PWD";
    public static final String PREFS_KEY_EMAIL = "EMAIL";
    public static final String PREFS_KEY_ADDRESS = "ADDRESS";
    Context mContext;
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    private static SPManipulation mInstance = null;

    // constructor
    private SPManipulation(Context context) {
        this.mContext = context;
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1
    }

    public static SPManipulation getInstance(Context context) {
        if(mInstance == null) {
            mInstance = new SPManipulation(context);
        }
        return mInstance;
    }

    public boolean hasUserLoggedIn() {
        return settings.contains(PREFS_KEY_MOBILE);
    }

    // using shared preference to store the user mobile and user name
    public void setPwd(String text) {
        editor = settings.edit(); //2
        editor.putString(PREFS_KEY_PWD, text); //3
        editor.commit(); //4
    }

    public void setEmail(String text) {
        editor = settings.edit(); //2
        editor.putString(PREFS_KEY_EMAIL, text); //3
        editor.commit(); //4
    }
    public void setAddress(String text) {
        editor = settings.edit(); //2
        editor.putString(PREFS_KEY_ADDRESS, text); //3
        editor.commit(); //4
    }

    public void setName(String text) {
        editor = settings.edit(); //2
        editor.putString(PREFS_KEY_NAME, text); //3
        editor.commit(); //4
    }

    public void setMobile(String text) {
        editor = settings.edit(); //2
        editor.putString(PREFS_KEY_MOBILE, text); //3
        editor.commit(); //4
    }

    public String getMobile() {
        return settings.getString(PREFS_KEY_MOBILE, null);
    }

    public String getEmail() {
        return settings.getString(PREFS_KEY_EMAIL, null);
    }

    public String getAddress() {
        return settings.getString(PREFS_KEY_ADDRESS, null);
    }

    public String getName() {
        return settings.getString(PREFS_KEY_NAME, null);
    }

    public String getPwd() {
        return settings.getString(PREFS_KEY_PWD, null);
    }

    public void clearSharedPreference() {
        editor = settings.edit();
        editor.clear();
        editor.commit();
    }

    public void removeValue(String prefs_key) {
        editor = settings.edit();
        editor.remove(prefs_key);
        editor.commit();
    }

}
