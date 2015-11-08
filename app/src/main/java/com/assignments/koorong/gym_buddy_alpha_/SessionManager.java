package com.assignments.koorong.gym_buddy_alpha_;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by Peter on 08/11/2015.
 */
public class SessionManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    final int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "LoginSession";
    private static final String IS_LOGGED_IN = "IsLoggedIn";
    public static final String KEY_FNAME = "FirstName";
    public static final String KEY_LNAME = "LastName";
    public static final String KEY_EMAIL = "Email";

    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createLoginSession(String fname, String lname, String email){
        editor.putBoolean(IS_LOGGED_IN, true)
                .putString(KEY_FNAME, fname)
                .putString(KEY_LNAME, lname)
                .putString(KEY_EMAIL, email)
                .commit();
    }

    public void checkLogin(){
        if(!this.isLoggedIn()){
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGGED_IN, false);
    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_FNAME, pref.getString(KEY_FNAME, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        return user;
    }

}
