package com.sample.crashlytics.networkhelper;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


import android.content.Context;
import android.os.AsyncTask;

import com.sample.crashlytics.R;
import com.sample.crashlytics.utils.Logger;
import com.sample.crashlytics.utils.MessageHandler;
import com.sample.crashlytics.utils.Utils;


/**
 * Created by manishdewan on 21/05/16.
 * Helper class to handle every request and passing response to activity
 */
public class ApiHelper {

    private static ApiHelper client;

    private static final String TAG = "ApiHelper";

    //singletion object for Helper
    public static ApiHelper getClient() {
        if (client == null) {
            synchronized (ApiHelper.class) {
                if (client == null) {
                    client = new ApiHelper();
                }
            }
        }
        return client;
    }

    public void get(Context context,
                    APICallResponseHandler apiCallResponseHandler, String urlString) {
        Logger.DEBUG(TAG, "URL---" + urlString);
        if (Utils.isNetworkAvailable(context)) {
            MessageHandler.getInstance().showProgressDialog();
            new ExecuteGetRequest(urlString, apiCallResponseHandler).execute();
        } else {
            apiCallResponseHandler.apiFailureResponse(context
                    .getString(R.string.no_internet_connection));
        }
    }

    /*
    AsyncTask to handle each network request
     */
    private class ExecuteGetRequest extends AsyncTask<Void, Void, String> {

        private String urlString;
        private boolean isExceptionThrown = false;
        private APICallResponseHandler apiCallResponseHandler;

        public ExecuteGetRequest(String url,
                                 APICallResponseHandler apiCallResponseHandler) {
            this.urlString = url;
            this.apiCallResponseHandler = apiCallResponseHandler;
        }

        @Override
        protected String doInBackground(Void... params) {

            InputStream inputStream = null;
            HttpURLConnection urlConnection = null;
            try {
                /* forming th java.net.URL object */
                URL url = new URL(urlString);
                urlConnection = (HttpURLConnection) url.openConnection();

                 /* optional request header */
                urlConnection.setRequestProperty("Content-Type", "application/json");

                /* optional request header */
                urlConnection.setRequestProperty("Accept", "application/json");

                /* for Get request */
                urlConnection.setRequestMethod("GET");
                int statusCode = urlConnection.getResponseCode();

                /* 200 represents HTTP OK */
                if (statusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                } else {
                    //result = 0; //"Failed to fetch data!";
                }
            } catch (Exception e) {
                Logger.DEBUG(TAG, "Exception", e);
                isExceptionThrown = true;
                return e.getMessage();
            }
            return Utils.convertInputStreamToString(inputStream);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // if some exception is thrown then send the response through failure
            if (isExceptionThrown) {
                apiCallResponseHandler.apiFailureResponse(result);
            } else {
                apiCallResponseHandler.apiSuccessResponse(result);
            }
        }
    }
}
