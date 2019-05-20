package com.example.hairapplication;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CommentListAdapter extends BaseAdapter {
    private Context context;
    private List<Comment> commentList;

    public CommentListAdapter(Context context, List<Comment> commentList) {
        this.context = context;
        this.commentList = commentList;
    }

    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public Object getItem(int i) {
        return commentList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final View v = View.inflate(context, R.layout.comment, null); // 레이아웃 참조
        TextView commentText = (TextView)v.findViewById(R.id.commentText);
        TextView nameText = (TextView)v.findViewById(R.id.nameText);
        TextView dateText = (TextView)v.findViewById(R.id.dateText);

        commentText.setText(commentList.get(i).getComment());
        nameText.setText(commentList.get(i).getName());
        dateText.setText(commentList.get(i).getDate());



        v.setTag(commentList.get(i).getComment());

        return v;
    }



}
