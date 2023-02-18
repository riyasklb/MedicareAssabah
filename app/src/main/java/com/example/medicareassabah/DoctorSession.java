package com.example.medicareassabah;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class DoctorSession {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "Login";
    private static final String IS_LOGIN = "IsLoggedIn";

    public DoctorSession(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
    /**
     * Create login session
     * */

    public void createLoginSession(String id,String username, String gender,String department,String qualification,
                                   String experience,String hospital,String phone,String email, String working_time,String available_days) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString("id", id);
        editor.putString("username", username);
        editor.putString("gender", gender);
        editor.putString("department",department);
        editor.putString("qualification", qualification);
        editor.putString("experience", experience);
        editor.putString("hospital", hospital);
        editor.putString("phone", phone);
        editor.putString("mail", email);
        editor.putString("working_time", working_time);
        editor.putString("available_day", available_days);




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
        user.put("gender", pref.getString("gender", null));
        user.put("department", pref.getString("department", null));
        user.put("qualification", pref.getString("qualification", null));
        user.put("experience", pref.getString("experience", null));
        user.put("hospital",pref.getString("hospital",null));
        user.put("phone", pref.getString("phone", null));
        user.put("mail", pref.getString("mail", null));
        user.put("working_time", pref.getString("working_time", null));
        user.put("available_day", pref.getString("available_day", null));

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


