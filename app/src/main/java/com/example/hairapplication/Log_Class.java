package com.example.hairapplication;

import android.util.Log;

/**
 * Created by ddrwd on 2018-03-31.
 */

public class Log_Class {
    public static final void w(String TAG, String message) {
        if (BuildConfig.DEBUG){
            Log.w(TAG, message);
        }
    }

    public static final void i(String TAG, String message) {
        if (BuildConfig.DEBUG){
            Log.i(TAG, message);
        }
    }

    public static final void d(String TAG, String message) {
        if (BuildConfig.DEBUG){
            Log.d(TAG, message);
        }
    }

    public static final void v(String TAG, String message) {
        if (BuildConfig.DEBUG){
            Log.v(TAG, message);
        }
    }
}
