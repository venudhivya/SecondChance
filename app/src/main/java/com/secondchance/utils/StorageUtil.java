package com.secondchance.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class StorageUtil {
    private static StorageUtil store;
    private SharedPreferences preferences;

    private StorageUtil(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static StorageUtil getInstance(Context context) {
        if (store == null) {
            store = new StorageUtil(context);
        }
        return store;
    }

    public void setString(String name, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(name, value);
        editor.apply();
    }

    public String getString(String name) {
        return preferences.getString(name, "");
    }

    public void setInt(String name, int value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(name, value);
        editor.apply();
    }

    public int getInt(String name) {
        return preferences.getInt(name, 0);
    }

    public void setBoolean(String name, boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(name, value);
        editor.apply();
    }

    public boolean isKeyAvailable(String name) {
        return preferences.contains(name);
    }

    public boolean getBoolean(String name) {
        return preferences.contains(name) && preferences.getBoolean(name, false);
    }

    public boolean getBoolean(String name, boolean defaultValue) {
        return preferences.getBoolean(name, defaultValue);
    }

    public void putListString(String key, List<String> stringList) {
        String[] myStringList = stringList.toArray(new String[stringList.size()]);
        preferences.edit().putString(key, TextUtils.join("‚‗‚", myStringList)).apply();
    }


    public List<String> getListString(String key) {
        return new ArrayList<String>(Arrays.asList(TextUtils.split(preferences.getString(key, ""), "‚‗‚")));
    }


    public void putObject(String key, Object obj) {
        checkForNullKey(key);
        Gson gson = new Gson();
        setString(key, gson.toJson(obj));
    }

    public void checkForNullKey(String key) {
        if (key == null) {
            throw new NullPointerException();
        }
    }


}

