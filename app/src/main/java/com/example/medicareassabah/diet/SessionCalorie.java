package com.example.medicareassabah.diet;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionCalorie {

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "Calorie";

    private static final String IS_SAVED = "IsSaved";


    // Constructor
    public SessionCalorie(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createSession(String age, String weight, String height, String cal){

        editor.putBoolean(IS_SAVED, true);

        // Storing name in pref
        editor.putString("age", age);
        editor.putString("weight", weight);
        editor.putString("height", height);
        editor.putString("cal", cal);

        editor.commit();
    }


    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){

        HashMap<String, String> user = new HashMap<>();
        user.put("age", pref.getString("age", null));
        user.put("weight", pref.getString("weight", null));
        user.put("height", pref.getString("height", null));
        user.put("cal", pref.getString("cal", "0"));

        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void clear(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
    }


    public boolean isSaved(){
        return pref.getBoolean(IS_SAVED, false);
    }

    public boolean checkSavedState(){
        // Check login status
        if(this.isSaved()) {
            return true;
        } else {
            return false;
        }
    }

}
