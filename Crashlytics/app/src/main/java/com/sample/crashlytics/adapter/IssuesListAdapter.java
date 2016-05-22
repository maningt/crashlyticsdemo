package com.sample.crashlytics.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sample.crashlytics.R;
import com.sample.crashlytics.models.RailIssue;
import com.sample.crashlytics.utils.Utils;

import java.util.ArrayList;

/**
 * Created by manishdewan on 21/05/16.
 * Issues List Adapter
 */
public class IssuesListAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<RailIssue> mRailIssues;

    public IssuesListAdapter(Context context, ArrayList<RailIssue> railIssues) {
        mContext = context;
        mRailIssues = railIssues;
    }

    @Override
    public int getCount() {
        return mRailIssues.size();
    }

    @Override
    public Object getItem(int position) {
        return mRailIssues.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        // create a new view
        View rowView = convertView;
        final ViewHolder viewHolder;
        //recycling the view to optimize memory and reuse view objects
        if (rowView == null) {
            rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_issue_layout, parent, false);
            viewHolder = new ViewHolder(rowView);
            rowView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) rowView.getTag();
        }
        viewHolder.textViewIssueTitle.setText(mRailIssues.get(position).getTitle());
        viewHolder.textViewIssueBody.setText(Utils.getStringSubset(mRailIssues.get(position).getBody(), 140));


        return rowView;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    class ViewHolder {
        // each data item is just a string in this case
        public TextView textViewIssueTitle;
        public TextView textViewIssueBody;

        public ViewHolder(View v) {
            textViewIssueTitle = (TextView) v.findViewById(R.id.tv_issue_title);
            textViewIssueBody = (TextView) v.findViewById(R.id.tv_issue_body);
        }
    }
}
