package com.example.hairapplication;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.List;

public class ReviewCommentListAdapter extends BaseAdapter {
    private Context context;
    private List<ReviewComment> reviewcommentList;
    AlertDialog dialog;
    Activity activity;

    public ReviewCommentListAdapter(Context context, List<ReviewComment> reviewcommentList, Activity activity) {
        this.context = context;
        this.reviewcommentList = reviewcommentList;
        this.activity = activity;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final View v = View.inflate(context, R.layout.reviewcomment, null); // 레이아웃 참조
        TextView commentText = (TextView)v.findViewById(R.id.commentText);
        TextView nameText = (TextView)v.findViewById(R.id.nameText);
        TextView dateText = (TextView)v.findViewById(R.id.dateText);


        TextView deleteButton = (TextView)v.findViewById(R.id.delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (MainActivity.nickname.equals(reviewcommentList.get(i).getName())) {

                    Response.Listener<String> responseListener = new Response.Listener<String>(){
                        @Override
                        public void onResponse(String response) {
                            try{
                                Log.e("nickname = "+ MainActivity.nickname, ", reviewcommentName = "+reviewcommentList.get(i).getName());


                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                if (success) { // 게시글 삭제에 성공했을 경우 성공 알림창 출력
                                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                                    dialog = builder.setMessage("게시글이 삭제되었습니다.")
                                            .setPositiveButton("확인", null)
                                            .create();
                                    dialog.show();

                                    reviewcommentList.remove(i);
                                    notifyDataSetChanged();

                                } else { // 게시글 삭제에 실패한 경우 실패 알림창 출력
                                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
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

                    ReviewCommentDeleteRequest reviewCommentdeleteRequest = new ReviewCommentDeleteRequest(reviewcommentList.get(i).getName(), reviewcommentList.get(i).getComment() , responseListener);
                    RequestQueue queue1 = Volley.newRequestQueue(context);
                    queue1.add(reviewCommentdeleteRequest);
                    Log.e("reviewcommentName = "+reviewcommentList.get(i).getName(), "reviewcommentContents = "+ reviewcommentList.get(i).getComment());

                }else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    dialog = builder.setMessage("자신이 작성한 글만 삭제할 수 있습니다.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();

                }

            }

        });


        commentText.setText(reviewcommentList.get(i).getComment());
        nameText.setText(reviewcommentList.get(i).getName());
        dateText.setText(reviewcommentList.get(i).getDate());

        v.setTag(reviewcommentList.get(i).getComment());

        return v;
    }



}
