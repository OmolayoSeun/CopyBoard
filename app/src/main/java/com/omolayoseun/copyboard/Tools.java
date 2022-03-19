package com.omolayoseun.copyboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

public class Tools {
    public static String dirPath = Environment.getExternalStorageDirectory() + "/Copy Board/";
    public static String fileToView = null;
    private static final String[] str = {null, null};
    private static boolean check = false;

    private static final String[] keys = {
            "boolean-copy-board",
            "text-name-copy-board",
            "text-string-copy-board"
    };
    private static SharedPreferences sharedPreferences;
    @SuppressLint("StaticFieldLeak")
    static Context context;


    // will return String array
    public static String getUnsavedTextName(){
        return str[0];
    }
    public static String getUnsavedTextString(){
        return str[1];
    }

    public static void checkForSavedText(Context context){
        Tools.context = context;
        String SHAREDPREFS = "COPY-BOARD";
        sharedPreferences = context.getSharedPreferences(SHAREDPREFS, Context.MODE_PRIVATE);
        if((check = sharedPreferences.getBoolean(keys[0], false))){
            str[0] = sharedPreferences.getString(keys[1], null);
            str[1] = sharedPreferences.getString(keys[2], null);
        }
    }

    public static void saveUnfinishedWork(String name,String text){
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (!text.equals("") && !text.equals(" ") && !text.equals("\n")){
            editor.putBoolean(keys[0], true);
            editor.putString(keys[1], name);
            editor.putString(keys[2], text);
        }
        else{
            editor.putBoolean(keys[0], false);
            editor.putString(keys[1], null);
            editor.putString(keys[2], null);
        }
        editor.apply();
    }

    public static void clear(){
        str[0] = null;
        str[1] = null;
        check = false;
    }
    public static boolean thereIsSaved(){
        return check;
    }
}
