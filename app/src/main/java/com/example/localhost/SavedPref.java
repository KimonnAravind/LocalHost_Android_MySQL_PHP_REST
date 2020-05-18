package com.example.localhost;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class SavedPref {
    private static SavedPref instance;
    private static Context ctx;

    private static final String SHAREDPREFNAME = "SIMPLE";
    private static final String USERNAME = "Username";
    private static final String EMAILID = "EmailID";
    private static final String USERID = "UserID";

    private SavedPref(Context context) {
        ctx = context;
    }

    public static synchronized SavedPref getInstance(Context context) {
        if (instance == null) {
            instance = new SavedPref(context);
        }
        return instance;
    }

    public boolean userlogin(int id, String name, String mailid) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHAREDPREFNAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(USERID, id);
        editor.putString(USERNAME, name);
        editor.putString(EMAILID, mailid);

        editor.apply();
        return true;
    }

    public boolean LoggedIn() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHAREDPREFNAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(USERNAME, null) != null)
        {
            return true;
        }
        return false;
    }
    public boolean logout()
    {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHAREDPREFNAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear();
        editor.apply();
        return true;
    }

    public String username()
    {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHAREDPREFNAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USERNAME,null);
    }

    public String mailid()
    {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHAREDPREFNAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(EMAILID,null);
    }


}