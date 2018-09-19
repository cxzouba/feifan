package com.feifandaiyu.feifan.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class PreferenceUtils {
    private final static String NAME = "feifan";

    private static SharedPreferences preferences;

    private static SharedPreferences getPreferences(Context context) {
        if (preferences == null) {
            preferences = context.getSharedPreferences(NAME,
                    Context.MODE_PRIVATE);
        }
        return preferences;
    }

    public static boolean getBoolean(Context context, String key) {
        return getBoolean(context, key, false);
    }

    public static boolean getBoolean(Context context, String key,
                                     boolean defValue) {
        SharedPreferences preferences = getPreferences(context);
        return preferences.getBoolean(key, defValue);
    }

    public static void setBoolean(Context context, String key, boolean value) {
        SharedPreferences preferences = getPreferences(context);
        Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static String getString(Context context, String key) {
        return getString(context, key, null);
    }

    public static String getString(Context context, String key, String defValue) {
        SharedPreferences preferences = getPreferences(context);
        return preferences.getString(key, defValue);
    }


    public static void setText(Context context, View view, String key) {
        getString(context, key);
        String value = getString(context, key, null);

        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            textView.setText(value);
        } else if (view instanceof EditText) {
            EditText editText = (EditText) view;
            editText.setText(value);
        }
    }


    public static void setString(Context context, String key, String value) {
        SharedPreferences preferences = getPreferences(context);
        Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void setString(Context context, View view, String key) {
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            String value = textView.getText().toString().trim();
            setString(context, key, value);
        } else if (view instanceof EditText) {
            EditText editText = (EditText) view;
            String value = editText.getText().toString().trim();
            setString(context, key, value);
        }

    }


    public static int getInt(Context context, String key) {
        return getInt(context, key, -1);
    }

    public static int getInt(Context context, String key, int defValue) {
        SharedPreferences preferences = getPreferences(context);
        return preferences.getInt(key, defValue);
    }

    public static void setInt(Context context, String key, int value) {
        SharedPreferences preferences = getPreferences(context);
        Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }
}
