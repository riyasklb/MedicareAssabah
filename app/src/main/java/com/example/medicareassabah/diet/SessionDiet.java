package com.example.medicareassabah.diet;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.HashMap;

public class SessionDiet {

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "Diet";


    // Constructor
    public SessionDiet(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createSession(String breakfast, String lunch, String snacks, String dinner,
                              String requiredCal, String totalCal){

        // Storing name in pref
        if (!TextUtils.isEmpty(breakfast)) {
            editor.putString("breakfast", breakfast);
        }
        if (!TextUtils.isEmpty(lunch)) {
            editor.putString("lunch", lunch);
        }
        if (!TextUtils.isEmpty(snacks)) {
            editor.putString("snacks", snacks);
        }
        if (!TextUtils.isEmpty(dinner)) {
            editor.putString("dinner", dinner);
        }
        editor.putString("requiredCal", requiredCal);
        if (!TextUtils.isEmpty(totalCal)) {
            editor.putString("totalCal", totalCal);
        }

        /*if (!TextUtils.isEmpty(totalCal)) {
            editor.putString("remainingCal", remainingCal);
        }*/

        editor.commit();
    }


    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){

        HashMap<String, String> user = new HashMap<>();
        user.put("breakfast", pref.getString("breakfast", null));
        user.put("lunch", pref.getString("lunch", null));
        user.put("snacks", pref.getString("snacks", null));
        user.put("dinner", pref.getString("dinner", null));
        user.put("requiredCal", pref.getString("requiredCal", "0"));
        user.put("totalCal", pref.getString("totalCal", "0"));
//        user.put("remainingCal", pref.getString("remainingCal", "0"));

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

}
