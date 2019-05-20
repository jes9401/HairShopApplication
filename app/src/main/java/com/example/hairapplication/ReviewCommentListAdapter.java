package com.example.hairapplication;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ReviewCommentListAdapter extends BaseAdapter {
    private Context context;
    private List<ReviewComment> reviewcommentList;

    public ReviewCommentListAdapter(Context context, List<ReviewComment> reviewcommentList) {
        this.context = context;
        this.reviewcommentList = reviewcommentList;
    }

    @Override
    public int getCount() {
        return reviewcommentList.size();
    }

    @Override
    public Object getItem(int i) {
        return reviewcommentList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final View v = View.inflate(context, R.layout.reviewcomment, null); // 레이아웃 참조
        TextView commentText = (TextView)v.findViewById(R.id.commentText);
        TextView nameText = (TextView)v.findViewById(R.id.nameText);
        TextView dateText = (TextView)v.findViewById(R.id.dateText);

        commentText.setText(reviewcommentList.get(i).getComment());
        nameText.setText(reviewcommentList.get(i).getName());
        dateText.setText(reviewcommentList.get(i).getDate());



        v.setTag(reviewcommentList.get(i).getComment());

        return v;
    }



}
