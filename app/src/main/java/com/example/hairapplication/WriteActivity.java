package com.example.hairapplication;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WriteActivity extends AppCompatActivity {

    private String pleaseTitle;
    private String pleaseName;
    private String pleaseDate;
    private String pleaseContents;


 //   final TextView writer = (TextView)findViewById(R.id.writer);
    private AlertDialog dialog; // 알림창


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        Button completeBtn = (Button)findViewById(R.id.completeBtn);
        final EditText titleText = (EditText)findViewById(R.id.titleText);
        final EditText contentsText = (EditText)findViewById(R.id.contentsText);
        final TextView writer = (TextView)findViewById(R.id.writer);
        final TextView dateText = (TextView)findViewById(R.id.dateText);
        final CheckBox pictureCheck = (CheckBox)findViewById(R.id.pictureCheck);
        final FrameLayout pleaseFrameLayout = (FrameLayout)findViewById(R.id.pleaseFrameLayout);

        pleaseFrameLayout.setVisibility(View.GONE);

        long now = System.currentTimeMillis();  // 현재 시간 받아오기
        Date date1 = new Date(now);
        SimpleDateFormat CurDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String strCurDate = CurDateFormat.format(date1);
        pleaseDate = strCurDate; // 현재 날짜 저장

        pleaseDate = strCurDate+""; // 현재 날짜 저장
        writer.setText(MainActivity.nickname);
        dateText.setText(pleaseDate);

        contentsText.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {
                if (view.getId() ==R.id.contentsText) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction()&MotionEvent.ACTION_MASK){
                        case MotionEvent.ACTION_UP:
                            view.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;
            }
        });

        pictureCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pictureCheck.isChecked()){

                    pleaseFrameLayout.setVisibility(View.VISIBLE);
                }else {
                    pleaseFrameLayout.setVisibility(View.GONE);
                }
            }
        });


        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pleaseTitle = titleText.getText().toString();
                pleaseName = MainActivity.nickname; // MainActivity에서 데이터베이스에서 받아온 nickname
        //        pleaseName = writer.getText().toString();
         //       pleaseDate = dateText.getText().toString();
                pleaseContents = contentsText.getText().toString();

                if(pleaseContents.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(WriteActivity.this);
                    dialog = builder.setMessage("내용을 입력해주세요.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }if(pleaseTitle.equals("")){
                    pleaseTitle = "제목 없음";
                }


                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){ // 작성에 성공했을 경우 성공 알림창 출력
                                AlertDialog.Builder builder = new AlertDialog.Builder(WriteActivity.this);
                                dialog = builder.setMessage("작성에 성공했습니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();


                            }else{ // 작성에 실패한 경우 실패 알림창 출력
                                AlertDialog.Builder builder = new AlertDialog.Builder(WriteActivity.this);
                                dialog = builder.setMessage("작성에 실패했습니다.")
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
                PleaseRequest pleaseRequest = new PleaseRequest(pleaseTitle, pleaseName, pleaseDate, pleaseContents, responseListener);
                RequestQueue queue = Volley.newRequestQueue(WriteActivity.this);
                queue.add(pleaseRequest);

                finish();
 //               Intent intent = new Intent(getApplicationContext(), PleaseFragment.class);
 //               startActivity(intent);
            }
        });


    }

    @Override
    protected void onStop() {
        super.onStop();
        if (dialog != null)
        {
            dialog.dismiss();
            dialog = null;
        }
    }
}
