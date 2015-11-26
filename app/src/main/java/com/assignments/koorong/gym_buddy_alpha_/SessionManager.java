package com.assignments.koorong.gym_buddy_alpha_;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by Peter on 08/11/2015.
 */
public class SessionManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    final int PRIVATE_MODE = 0;
    public static final String PREF_NAME = "LoginSession";
    public static final String IS_LOGGED_IN = "IsLoggedIn";
    public static final String KEY_NAME = "FirstName";
    public static final String KEY_EMAIL = "Email";
    public static final String KEY_LOCATION = "location";
    public static final String KEY_GENDER = "Gender";
    public static final String KEY_AGE = "Age";
    public static final String KEY_GENDERPREF = "genderPref";
    public static final String KEY_FREQUENCY = "frequency";
    public static final String KEY_EXPERIENCE = "experience";
    public static final String KEY_AGEPREF = "AgePref";


    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createLoginSession(String name,String email, String location){
        editor.putBoolean(IS_LOGGED_IN, true)
                .putString(KEY_NAME, name)
                .putString(KEY_EMAIL, email)
                .putString(KEY_LOCATION, location)
                .commit();
    }
    public void setUpProfile(int Age, boolean gender)
    {
        editor.putInt(KEY_AGE, Age)
                .putBoolean(KEY_GENDER, gender)
                .commit();
    }

    public void setUpGenderPref(int genderPref)
    {
        editor.putInt(KEY_GENDERPREF, genderPref)
                .commit();
    }

    public void setUpAgePref(int agePref)
    {
        editor.putInt(KEY_AGEPREF, agePref)
                .commit();
    }

    public void setUpExpPref(int experience)
    {
        editor.putInt(KEY_EXPERIENCE, experience)
                .commit();
    }

    public void checkLogin(){
        if(!this.isLoggedIn()){
            Intent i = new Intent(_context, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);
        }
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGGED_IN, false);
    }

    public User getUserDetails(){
        User user = new User();
        user.setName(pref.getString(KEY_NAME, ""));
        user.setEmail(pref.getString(KEY_EMAIL, ""));
        user.setLocation(pref.getString(KEY_LOCATION, ""));
        user.setAge(pref.getInt(KEY_AGE, 0));
        user.setAgePref(pref.getInt(KEY_AGEPREF, 0));
        user.setGender(pref.getBoolean(KEY_GENDER, false));
        user.setgenderPref(pref.getInt(KEY_GENDERPREF, 0));
        user.setexperience(pref.getInt(KEY_EXPERIENCE, 0));
        user.setfrequency(pref.getInt(KEY_FREQUENCY, 0));
        return user;
    }

    public void logout(){
        pref.edit().clear().commit();
    }

}
