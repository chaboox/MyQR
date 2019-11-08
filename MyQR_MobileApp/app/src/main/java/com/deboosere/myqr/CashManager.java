package com.deboosere.myqr;

import android.content.SharedPreferences;
import android.preference.Preference;
import android.util.Log;

import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;

public class CashManager {
    public static void SaveScan(User scan, SharedPreferences preference){
        SharedPreferences mPrefs = preference;
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(scan);
        Log.d("SACAAN", "getScan: " + json);
        prefsEditor.putString("lastscan", json);
        prefsEditor.commit();
    }

    public static User getScan(SharedPreferences preference){
        SharedPreferences mPrefs = preference;
        Gson gson = new Gson();
        String json = mPrefs.getString("lastscan", "{\"actif\":true,\"address\":\"4 rue de l\\u0027industrie, Apparemment 4\",\"dateadd\":\"07/11/2019 09:03\",\"email\":\"chaboox@gmail.com\",\"lastedit\":\"07/11/2019 10:23\",\"nom\":\"Adam Deboosère\",\"sang\":\"o-\",\"telephone\":\"0782261523\"}");
        Log.d("SACAAN", "getScan: " + json);
        User obj = gson.fromJson(json, User.class);
        return obj;
    }
}
