package com.sample.crashlytics.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.sample.crashlytics.R;
import com.sample.crashlytics.adapter.IssuesListAdapter;
import com.sample.crashlytics.models.Comments;
import com.sample.crashlytics.models.RailIssue;
import com.sample.crashlytics.networkhelper.APICallResponseHandler;
import com.sample.crashlytics.networkhelper.ApiHelper;
import com.sample.crashlytics.utils.MessageHandler;
import com.sample.crashlytics.utils.RequestUrls;
import com.sample.crashlytics.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/**
 * Starting Activity of application showing list of issues
 */
public class RailIssueActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ArrayList<RailIssue> mRailIssues;
    private ArrayList<Comments> mIssueComments;
    private ListView mListRailIssues;
    private IssuesListAdapter mIssuesListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        initListerenrs();
        sendIssuesRequest();
    }

    /**
     * initialize listeners
     */
    private void initListerenrs() {
        mListRailIssues.setOnItemClickListener(this);
    }

    /**
     * initialize UI elements
     */
    private void initUI() {
        mListRailIssues = (ListView) findViewById(R.id.list_issues_rails);
        MessageHandler.getInstance().createProgresDialog(this);
    }


    /**
     * @return method to call api for issues list
     */
    private void sendIssuesRequest() {
        ApiHelper.getClient().get(this, new APICallResponseHandler() {
            @Override
            public void apiSuccessResponse(String result) {
                //handling success case of api
                MessageHandler.getInstance().hideProgressDialog();
                mRailIssues = Utils.parseJson(result,
                        new TypeToken<ArrayList<RailIssue>>() {
                        }.getType());
                initData(mRailIssues);
            }

            @Override
            public void apiFailureResponse(String error) {
                //handling failure case of api
                MessageHandler.getInstance().hideProgressDialog();
                Utils.showToast(RailIssueActivity.this, error);
            }
        }, RequestUrls.ISSUES_URL);
    }

    /**
     * @param mRailIssues
     * @return fill the listview with data once we have it after success of api
     */
    private void initData(ArrayList<RailIssue> mRailIssues) {
        //sorting Arraylist with time such that recent is made first
        Collections.sort(mRailIssues, new Comparator<RailIssue>() {
            @Override
            public int compare(RailIssue lhs, RailIssue rhs) {
                return rhs.getUpdated_at().compareTo(lhs.getUpdated_at());
            }
        });
        mIssuesListAdapter = new IssuesListAdapter(this, mRailIssues);
        mListRailIssues.setAdapter(mIssuesListAdapter);
    }

    /**
     * @param parent
     * @param view
     * @param position
     * @param id
     * @return method to handle ListView onItemClick
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        sendCommentsRequest(mRailIssues.get(position).getComments_url());
    }

    /**
     * @param commentUrl
     * @return method to call comment list api
     */
    private void sendCommentsRequest(String commentUrl) {
        ApiHelper.getClient().get(this, new APICallResponseHandler() {
            @Override
            public void apiSuccessResponse(String result) {
                //handling success case of api
                MessageHandler.getInstance().hideProgressDialog();
                mIssueComments = Utils.parseJson(result,
                        new TypeToken<ArrayList<Comments>>() {
                        }.getType());
                showCommentsDialog(mIssueComments);
            }

            @Override
            public void apiFailureResponse(String error) {
                //hanlding error case of api
                MessageHandler.getInstance().hideProgressDialog();
                Utils.showToast(RailIssueActivity.this, error);
            }
        }, commentUrl);
    }

    /**
     * @param comments
     * @return method to show Dialog with comments
     */
    private void showCommentsDialog(ArrayList<Comments> comments) {
        if (comments != null && comments.size() > 0) {
            MessageHandler.getInstance().showDialogwithComments(this, comments);
        } else {
            MessageHandler.getInstance().showToast(this, getString(R.string.app_name));
        }
    }
}
