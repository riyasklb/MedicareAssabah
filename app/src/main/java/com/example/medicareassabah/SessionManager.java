package com.example.medicareassabah;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "Login";
    private static final String IS_LOGIN = "IsLoggedIn";

    public  SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
    /**
     * Create login session
     * */

    public void createLoginSession(String id,String username,String age,String gender,String phone,
                                   String disease,String blood,String email,String key,String location) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString("id", id);
        editor.putString("username", username);
        editor.putString("age", age);
        editor.putString("gender", gender);
        editor.putString("phone", phone);
        editor.putString("disease",disease);
        editor.putString("blood_grp", blood);
        editor.putString("mail", email);
        editor.putString("ekey",key);
        editor.putString("location",location);




        editor.commit();
    }
    public boolean checkLogin(){
        // Check login status
        if(this.isLoggedIn()) {
            return true;
        } else {
            return false;
            // Closing all the Activities
         //  i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
           // i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //Intent i = new Intent(_context, PatientRegistration.class);

          //  _context.startActivity(i);
        }

    }


    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<>();
        // user name
        user.put("id", pref.getString("id", null));
        user.put("username", pref.getString("username", null));
        user.put("age", pref.getString("age", null));
        user.put("gender", pref.getString("gender", null));
        user.put("phone", pref.getString("phone", null));
        user.put("disease", pref.getString("disease", null));
        user.put("blood_grp", pref.getString("blood_grp", null));
        user.put("mail", pref.getString("mail", null));
        user.put("ekey", pref.getString("ekey", null));
        user.put("location", pref.getString("location", null));


        // user email id

        // return user
        return (user);
    }
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
      //  Intent i = new Intent(_context, PatientRegistration.class);
        // Closing all the Activities
//        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }


    public void clearData() {
        editor.clear();
        editor.commit();
    }
}


    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
