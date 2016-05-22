package com.sample.crashlytics.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

/**
 * Created by manishdewan on 21/05/16.
 */
//Utils Class have static Utility methods
public class Utils {
    private static final String TAG = "TextHelper";

    /**
     * @param context
     * @return method which returns true if network is available and false if network is not available
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param in
     * @return method which converts input stream response to String response
     */
    public static String convertInputStreamToString(InputStream in) {
        String text = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            text = sb.toString();
        } catch (Exception ex) {
            Logger.DEBUG(TAG, ex.getMessage(), ex);
        } finally {
            try {
                in.close();
            } catch (Exception ex) {
                Logger.DEBUG(TAG, ex.getMessage(), ex);
            }
        }
        return text;
    }

    /**
     * @param json
     * @param type
     * @param <T>
     * @return method which help in converting String response to model or pojo
     */
    public static <T> T parseJson(String json, Type type) {
        return new Gson().fromJson(json, type);
    }

    /**
     * @param context
     * @param message
     * @return method to show Toast message
     */
    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * @param input
     * @param charactes
     * @return method to get first n characters of a String
     */
    public static String getStringSubset(String input, int charactes) {
        return input.substring(0, Math.min(input.length(), charactes));
    }
}
