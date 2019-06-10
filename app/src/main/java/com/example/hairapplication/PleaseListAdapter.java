package com.example.hairapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.List;

public class PleaseListAdapter extends BaseAdapter {
    private Context context;
    private List<Please> pleaseList;
    AlertDialog dialog;
    private Fragment parent;
    private Activity activity;

    public PleaseListAdapter(Context context, List<Please> pleaseList, Fragment parent) {
        this.context = context;
        this.pleaseList = pleaseList;
        this.parent = parent;
    }

    public PleaseListAdapter(Context context, List<Please> pleaseList, Activity activity) {
        this.context = context;
        this.pleaseList = pleaseList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return pleaseList.size();
    } // 세팅할 아이템의 개수

    @Override
    public Object getItem(int i) {
        return pleaseList.get(i);
    } // i 번째 아이템 정보 가져옴

    @Override
    public long getItemId(int i) {
        return i;
    } // 아이템의 인덱스를 가져옴

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final View v = View.inflate(context, R.layout.please, null); // 레이아웃 참조
        final TextView pleaseText = (TextView)v.findViewById(R.id.pleaseText);
        TextView nameText = (TextView)v.findViewById(R.id.nameText);
        TextView dateText = (TextView)v.findViewById(R.id.dateText);

        TextView deleteButton = (TextView)v.findViewById(R.id.delete);
        TextView reviseButton = (TextView)v.findViewById(R.id.revise);

        TextView secretText = (TextView)v.findViewById(R.id.secret);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (MainActivity.nickname.equals(pleaseList.get(i).getName())) {

                    Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        try{
                                Log.e("nickname = "+ MainActivity.nickname, ", pleaseName = "+pleaseList.get(i).getName());


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
                                    pleaseList.remove(i);
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

                PleaseDeleteRequest pleasedeleteRequest = new PleaseDeleteRequest(pleaseList.get(i).getPlease(), pleaseList.get(i).getName() , responseListener);
                RequestQueue queue1 = Volley.newRequestQueue(context);
                queue1.add(pleasedeleteRequest);
                Log.e("pleaseName = "+pleaseList.get(i).getName(), "pleasePlease = "+ pleaseList.get(i).getPlease());

                }else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                        dialog = builder.setMessage("자신이 작성한 글만 삭제할 수 있습니다.")
                                .setPositiveButton("확인", null)
                                .create();
                        dialog.show();

                }


                /*
                PleaseDeleteRequest pleasedeleteRequest = new PleaseDeleteRequest(pleaseList.get(i).getPlease(), pleaseList.get(i).getName() , responseListener);
                RequestQueue queue = Volley.newRequestQueue(parent.getActivity());
                queue.add(pleasedeleteRequest);
                Log.e("pleaseName = "+pleaseList.get(i).getName(), "pleasePlease = "+ pleaseList.get(i).getPlease());
*/
            }

        });

        if(pleaseList.get(i).getAccess() == 1){
            secretText.setText("공개");
        }else{
            secretText.setText("비공개");
        }

        reviseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (MainActivity.nickname.equals(pleaseList.get(i).getName())) {

                Intent intent = new Intent(v.getContext(), PleaseReviseActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Num", pleaseList.get(i).num);
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


        pleaseText.setText(pleaseList.get(i).getPlease());
        nameText.setText(pleaseList.get(i).getName());
        dateText.setText(pleaseList.get(i).getDate());

        v.setTag(pleaseList.get(i).getPlease());

        return v;
    }

}
