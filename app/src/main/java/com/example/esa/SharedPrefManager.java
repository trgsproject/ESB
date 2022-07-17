package com.example.esa;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    private static SharedPrefManager mInstance;
    private static Context mCtx;

    //change string name
    private static final String SHARED_PREF_NAME = "mysharedpref12";
    private static final String KEY_USER_ID = "userid";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_USER_FULLNAME ="userfullname";
    private static final String KEY_USER_ROLE = "userrole";
    private static final String KEY_USER_SUBSPECIALITY ="usersubspeciality";
    private static final String KEY_USER_EMAIL = "useremail";
    private static final String KEY_USER_PHONE ="userphone";

    private SharedPrefManager(Context context) {
        mCtx = context;

    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }


    public boolean userLogin( String username,String name, String role, String subspeciality, String email, String phone){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_USER_FULLNAME,name);
        editor.putString(KEY_USER_ROLE ,role);
        editor.putString(KEY_USER_SUBSPECIALITY,subspeciality);
        editor.putString(KEY_USER_EMAIL, email);
        editor.putString(KEY_USER_PHONE,phone);

        editor.apply();

        return true;
    }

    //get logged in
    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if(sharedPreferences.getString(KEY_USERNAME, null) != null){

            return true;
        }
        return false;
    }


    //logout
    public boolean logout(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }

    //getUsername
    public String getUsername(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null);
    }

    //getUserEmail
    public String getUserEmail(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_EMAIL, null);
    }

    //getUserFullName
    public String getUserFullname(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_FULLNAME, null);
    }

    //getUserLevel
    public String getUserRole(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_ROLE, null);
    }

    //getUserPhone
    public String getUserPhone(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_PHONE, null);
    }

    //getUserSubSpeciality
    public String getUserSubspeciality(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_SUBSPECIALITY, null);
    }

}
