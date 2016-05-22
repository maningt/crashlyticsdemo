package com.sample.crashlytics.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sample.crashlytics.R;
import com.sample.crashlytics.models.Comments;

import java.util.ArrayList;

/**
 * Created by manishdewan on 21/05/16.
 * Comments List Adapter
 */
public class CommentListAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Comments> mComments;

    public CommentListAdapter(Context context, ArrayList<Comments> comments) {
        mContext = context;
        mComments = comments;
    }

    @Override
    public int getCount() {
        return mComments.size();
    }

    @Override
    public Object getItem(int position) {
        return mComments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // create a new view
        View rowView = convertView;
        final ViewHolder viewHolder;
        //recycling the view to optimize memory and reuse view objects
        if (rowView == null) {
            rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
            viewHolder = new ViewHolder(rowView);
            rowView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) rowView.getTag();
        }
        viewHolder.textViewUserName.setText(mComments.get(position).getUser().getLogin() + " " + mContext.getString(R.string.commented_text));
        viewHolder.textViewCommentBody.setText(mComments.get(position).getBody());
        return rowView;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    class ViewHolder {
        // each data item is just a string in this case
        public TextView textViewUserName;
        public TextView textViewCommentBody;

        public ViewHolder(View v) {
            textViewUserName = (TextView) v.findViewById(R.id.user_name);
            textViewCommentBody = (TextView) v.findViewById(R.id.comment_body);
        }
    }
}
