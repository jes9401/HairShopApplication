package com.example.hairapplication;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

public class ReviewListAdapter extends BaseAdapter {
    private Context context;
    private List<Review> reviewList;


    public ReviewListAdapter(Context context, List<Review> reviewList) {
        this.context = context;
        this.reviewList = reviewList;
    }

    @Override
    public int getCount() {
        return reviewList.size();
    }

    @Override
    public Object getItem(int i) {
        return reviewList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final View v = View.inflate(context, R.layout.review, null); // 레이아웃 참조
        TextView reviewText = (TextView)v.findViewById(R.id.reviewText);
        TextView nameText = (TextView)v.findViewById(R.id.nameText);
        TextView dateText = (TextView)v.findViewById(R.id.dateText);
        final RatingBar reviewRating = (RatingBar)v.findViewById(R.id.reviewRating);

        reviewText.setText(reviewList.get(i).getReview());
        nameText.setText(reviewList.get(i).getName());
        dateText.setText(reviewList.get(i).getDate());
        Log.e("reviewRating --- = "+reviewList.get(i).getRate(), "reviewRating");
        reviewRating.setRating(reviewList.get(i).getRate());





        v.setTag(reviewList.get(i).getReview());

        return v;
    }



}
