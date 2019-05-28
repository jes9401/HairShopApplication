package com.example.hairapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.List;

public class ReviewListAdapter extends BaseAdapter {
    private Context context;
    private List<Review> reviewList;
    private Fragment parent;
    private Activity activity;
    AlertDialog dialog;

    public ReviewListAdapter(Context context, List<Review> reviewList, Fragment parent) {
        this.context = context;
        this.reviewList = reviewList;
        this.parent = parent;
    }

    public ReviewListAdapter(Context context, List<Review> reviewList, Activity activity) {
        this.context = context;
        this.reviewList = reviewList;
        this.activity = activity;
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

        TextView deleteButton = (TextView)v.findViewById(R.id.delete);
        TextView reviseButton = (TextView)v.findViewById(R.id.revise);


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (MainActivity.nickname.equals(reviewList.get(i).getName())) {

                    Response.Listener<String> responseListener = new Response.Listener<String>(){
                        @Override
                        public void onResponse(String response) {
                            try{
                                Log.e("nickname = "+ MainActivity.nickname, ", reviewName = "+reviewList.get(i).getName());


                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                if (success) { // 게시글 삭제에 성공했을 경우 성공 알림창 출력

                                    if (activity == null) {

                                        AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                                        dialog = builder.setMessage("게시글이 삭제되었습니다.")
                                                .setPositiveButton("확인", null)
                                                .create();
                                        dialog.show();

                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                                        dialog = builder.setMessage("게시글이 삭제되었습니다.")
                                                .setPositiveButton("확인", null)
                                                .create();
                                        dialog.show();
                                    }
                                    reviewList.remove(i);
                                    notifyDataSetChanged();

                                } else { // 게시글 삭제에 실패한 경우 실패 알림창 출력
                                    AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                                    dialog = builder.setMessage("삭제에 실패하였습니다.")
                                            .setNegativeButton("확인", null)
                                            .create();
                                    dialog.show();
                                }

                            }
                            catch (Exception e){
                                e.printStackTrace(); // 오류 출력
                            }

                        }
                    } ;

                    ReviewDeleteRequest reviewdeleteRequest = new ReviewDeleteRequest(reviewList.get(i).getReview(), reviewList.get(i).getName() , responseListener);
                    RequestQueue queue1 = Volley.newRequestQueue(context);
                    queue1.add(reviewdeleteRequest);
                    Log.e("reviewName = "+reviewList.get(i).getName(), "reviewreview = "+ reviewList.get(i).getReview());

                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                    dialog = builder.setMessage("자신이 작성한 글만 삭제할 수 있습니다.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();

                }

            }

        });


        reviseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (MainActivity.nickname.equals(reviewList.get(i).getName())) {

                    Intent intent = new Intent(v.getContext(), ReviewReviseActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("Num", reviewList.get(i).num);
                    v.getContext().startActivity(intent);

                }else {
                    if (activity == null) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                        dialog = builder.setMessage("자신이 작성한 글만 수정할 수 있습니다.")
                                .setPositiveButton("확인", null)
                                .create();
                        dialog.show();

                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        dialog = builder.setMessage("자신이 작성한 글만 수정할 수 있습니다.")
                                .setPositiveButton("확인", null)
                                .create();
                        dialog.show();
                    }
                }

            }
        });



        reviewText.setText(reviewList.get(i).getReview());
        nameText.setText(reviewList.get(i).getName());
        dateText.setText(reviewList.get(i).getDate());
        Log.e("reviewRating --- = "+reviewList.get(i).getRate(), "reviewRating");
        reviewRating.setRating(reviewList.get(i).getRate());





        v.setTag(reviewList.get(i).getReview());

        return v;
    }



}
