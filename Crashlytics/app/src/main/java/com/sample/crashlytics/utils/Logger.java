package com.sample.crashlytics.utils;

import android.util.Log;

/**
 * Created by manishdewan on 21/05/16.
 */

/**
 * Logger Class to print logs
 */
public class Logger {

    /**
     * @param tag
     * @param msg
     * @return method to print logs with particular tag and message
     */
    public static void DEBUG(String tag, String msg, Throwable th) {
        Log.d(tag, msg, th);
    }

    /**
     * @param tag
     * @param msg
     * @return method to print logs with particular tag and message
     */
    public static void DEBUG(String tag, String msg) {
        Log.d(tag, msg);
    }
}
