package com.example.hairapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReviewReviseActivity extends AppCompatActivity {
    TextView writerText;
    EditText titleText;
    TextView dateText;
    EditText contentsText;
    private String writer, title, date, contents;
    private String secret;
    AlertDialog dialog;
    private int reviewNum;
    private int access;
    private int num;
    private String reviewTitle, reviewContents, reviseDate;
    private float reviewRate;
    RatingBar reviewRating;
    RadioButton open;
    RadioButton notopen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_revise);

        writerText = (TextView) findViewById(R.id.writer);
        titleText = (EditText) findViewById(R.id.titleText);
        dateText = (TextView) findViewById(R.id.dateText);
        contentsText = (EditText)findViewById(R.id.contentsText);
        RadioGroup secretGroup = (RadioGroup)findViewById(R.id.secretGroup);
        open = (RadioButton)findViewById(R.id.open);
        notopen = (RadioButton)findViewById(R.id.notopen);
        Button completeButton = (Button)findViewById(R.id.completeButton);
        final CheckBox pictureCheck = (CheckBox)findViewById(R.id.pictureCheck);
        final FrameLayout reviewFrameLayout = (FrameLayout)findViewById(R.id.reviewFrameLayout);
        reviewRating = (RatingBar)findViewById(R.id.reviewRating);

        long now = System.currentTimeMillis();  // 현재 시간 받아오기
        Date date1 = new Date(now);
        SimpleDateFormat CurDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        reviseDate = CurDateFormat.format(date1);
        dateText.setText(reviseDate);
        reviseDate = reviseDate+" 수정됨";
        Intent intent = getIntent();
        reviewNum = intent.getIntExtra("Num", 0);
        Log.e("reviewNum = "+reviewNum, "reviewNum");

        reviewFrameLayout.setVisibility(View.GONE);

        pictureCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pictureCheck.isChecked()){

                    reviewFrameLayout.setVisibility(View.VISIBLE);
                }else {
                    reviewFrameLayout.setVisibility(View.GONE);
                }
            }
        });

        new ReviewReviseActivity.BackgroundTask().execute();

        reviewRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                reviewRate = reviewRating.getRating();
                Log.e("reviewRate revise = "+reviewRate, "revise");
            }
        });
        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                reviewTitle = titleText.getText().toString();
                reviewContents = contentsText.getText().toString();

                if (reviewContents.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ReviewReviseActivity.this);
                    dialog = builder.setMessage("내용을 입력해주세요.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }
                if (reviewTitle.equals("")) {
                    reviewTitle = "제목 없음";
                }


                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) { // 작성에 성공했을 경우 성공 알림창 출력
                                AlertDialog.Builder builder = new AlertDialog.Builder(ReviewReviseActivity.this);
                                dialog = builder.setMessage("수정에 성공했습니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();


                            } else { // 작성에 실패한 경우 실패 알림창 출력
                                AlertDialog.Builder builder = new AlertDialog.Builder(ReviewReviseActivity.this);
                                dialog = builder.setMessage("수정에 실패했습니다.")
                                        .setNegativeButton("확인", null)
                                        .create();
                                dialog.show();

                            }
                        } catch (Exception e) {
                            e.printStackTrace(); // 오류 출력
                        }

                    }
                };
                ReviewRequest reviseRequest = new ReviewRequest(num, reviewTitle, reviseDate, reviewContents, reviewRate, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ReviewReviseActivity.this);
                queue.add(reviseRequest);
                Log.e("access = " + access, "access");
                finish();
            }

        });



    }






    class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute(){
            try{
                target = "http://kyu9341.cafe24.com/ReviewRevise.php?reviewNum="+ URLEncoder.encode(reviewNum+""); // GET 방식으로 ID를 서버에 전송
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream(); // 넘어오는 결과값들을 저장
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream)); // 해당 inputstream에 있는 내용들을 버퍼에 담아 읽을수 있도록 함.
                String temp;
                StringBuilder stringBuilder = new StringBuilder(); // 문자열 형태로 저장
                while ((temp = bufferedReader.readLine()) != null){  // 버퍼에서 받아오는 값을 한줄씩 읽으면 temp에 저장
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        public void onProgressUpdate(Void... values){
            super.onProgressUpdate();
        }

        @Override
        public void onPostExecute(String result){ // 해당 결과 처리
            try{
                JSONObject jsonObject = new JSONObject(result); // 응답 부분 처리
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                JSONObject object = jsonArray.getJSONObject(0); // 현재 배열의 원소값


                writer = object.getString("reviewName");
                title = object.getString("reviewTitle");
                date = object.getString("reviewDate");
                contents = object.getString("reviewContents");
                reviewRate = object.getInt("reviewRate");
                num = object.getInt("reviewNum");

                Log.e("writer = "+writer , "writer");
                Log.e("title = "+title , "title");
                Log.e("date = "+date , "date");
                Log.e("contents = "+contents , "contents");

                writerText.setText(writer);
                titleText.setText(title);
                contentsText.setText(contents);

                reviewRating.setRating(reviewRate);


            }catch (Exception e){
                e.printStackTrace();
            }
        }


    }
}
