package com.sample.crashlytics.utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.Window;
import android.widget.ListView;
import android.widget.Toast;

import com.sample.crashlytics.R;
import com.sample.crashlytics.adapter.CommentListAdapter;
import com.sample.crashlytics.models.Comments;

import java.util.ArrayList;

/**
 * Created by manishdewan on 21/05/16.
 */

/**
 * Message Handler class to show Dialogs and Toast in Application
 */
public class MessageHandler {
    private Dialog mDialog;
    private static MessageHandler mMessageHandler;
    private ProgressDialog mProgressDialog;

    /**
     * private Constructor of Singleton Class
     */
    private MessageHandler() {

    }

    /**
     * @return method to get Single Instance of this class
     */
    public static MessageHandler getInstance() {
        if (mMessageHandler == null) {
            mMessageHandler = new MessageHandler();
        }
        return mMessageHandler;
    }

    /**
     * @param context
     * @param comments
     * @return method to show Dialog having list of comments
     */
    public Dialog showDialogwithComments(Context context, ArrayList<Comments> comments) {
        try {
            if (((Activity) context).isFinishing())
                return null;
        } catch (Exception e) {
        }
        dismissAlertDialog();
        mDialog = new Dialog(context);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.comments_dialog_layout);
        ListView listviewComments = (ListView) mDialog.findViewById(R.id.listview_comments_dialog);
        CommentListAdapter commentListAdapter = new CommentListAdapter(context, comments);
        listviewComments.setAdapter(commentListAdapter);
        mDialog.show();
        return mDialog;
    }

    /**
     * method to dismiss dialog if visible
     */
    public void dismissAlertDialog() {
        if (mDialog != null && mDialog.isShowing())
            mDialog.dismiss();
    }

    /**
     * @param context
     * @param message
     * @return method to show Toast message
     */
    public void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * @param context
     * @return method to initialize the ProgressDialog
     */
    public void createProgresDialog(Context context) {
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage(context.getString(R.string.loading_text));
    }

    /**
     * method to show Progress Dialog
     */
    public void showProgressDialog() {
        if (mProgressDialog != null)
            mProgressDialog.show();
    }

    /**
     * method to hide Progress Dialog
     */
    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }
}
