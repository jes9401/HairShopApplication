package com.example.hairapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MyWritingActivity extends AppCompatActivity {

    private ListView pleaseListView;
    private PleaseListAdapter adapter;
    private List<Please> pleaseList;

    private ListView reviewListView;
    private ReviewListAdapter adapter1;
    private List<Review> reviewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_writing);


        pleaseListView = (ListView)findViewById(R.id.pleaseListView);
        pleaseList = new ArrayList<Please>(); // 배열에 넣어줌
        adapter = new PleaseListAdapter(getApplicationContext(), pleaseList, this);
        pleaseListView.setAdapter(adapter); //리스트 뷰에 어댑터 매칭

       new MyWritingActivity.BackgroundTask_please().execute(); // 데이터베이스 연동

        pleaseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                Intent intent = new Intent(getApplicationContext() ,PleaseContentsActivity.class);

                intent.putExtra("Title", pleaseList.get(i).please);  // 인텐트로 정보를 넘겨줌
                intent.putExtra("Name", pleaseList.get(i).name);
                intent.putExtra("Date", pleaseList.get(i).date);
                intent.putExtra("Contents", pleaseList.get(i).contents);
                intent.putExtra("Index", pleaseList.get(i).num);

                startActivity(intent);


            }
        });


        reviewListView = (ListView)findViewById(R.id.reviewListView);
        reviewList = new ArrayList<Review>(); // 배열에 넣어줌
        adapter1 = new ReviewListAdapter(getApplicationContext(), reviewList, this);
        reviewListView.setAdapter(adapter1); //리스트 뷰에 어댑터 매칭

        new MyWritingActivity.BackgroundTask_review().execute(); // 데이터베이스 연동

        reviewListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //               reviewListView.getItemAtPosition(i);


                Intent intent = new Intent(getApplicationContext(), ReviewContentsActivity.class);

                intent.putExtra("Title", reviewList.get(i).review);  // 인텐트로 정보를 넘겨줌
                intent.putExtra("Name", reviewList.get(i).name);
                intent.putExtra("Date", reviewList.get(i).date);
                intent.putExtra("Contents", reviewList.get(i).contents);
                intent.putExtra("Index", reviewList.get(i).num);
                startActivity(intent);
            }
        });




        //하단바 이동
        ImageButton homeButton, mypageButton, mapButton;

        homeButton = (ImageButton)findViewById(R.id.homeButton);
        mypageButton = (ImageButton)findViewById(R.id.mypageButton);
        mapButton = (ImageButton)findViewById(R.id.mapButton);

        homeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        mypageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), MyPageActivity.class);
                startActivity(intent);
            }
        });

            mapButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            });

    }

    class BackgroundTask_please extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute(){
            target = "http://kyu9341.cafe24.com/PleaseList.php";
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
                int count = 0;
                String  pleaseTitle, pleaseName, pleaseDate, pleaseContents; // 변수 선언
                int pleaseNum;
                while(count < jsonArray.length())
                {
                    JSONObject object = jsonArray.getJSONObject(count); // 현재 배열의 원소값
                    pleaseTitle = object.getString("pleaseTitle");
                    pleaseName = object.getString("pleaseName");
                    pleaseDate = object.getString("pleaseDate");
                    pleaseContents = object.getString("pleaseContents");
                    pleaseNum = object.getInt("pleaseNum");

                    if(pleaseName.equals(MainActivity.nickname)) { // 현재 로그인한 사용자의 닉네임과 작성자의 닉네임이 같은 경우일때

                        Please please = new Please(pleaseNum, pleaseTitle, pleaseName, pleaseDate, pleaseContents); // 객체 생성 (생성자)
                        pleaseList.add(please); // 리스트에 추가
                        adapter.notifyDataSetChanged();
                    }
                    count++;
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }


    }

    class BackgroundTask_review extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute(){
            target = "http://kyu9341.cafe24.com/ReviewList.php";
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
                int count = 0;
                String  reviewTitle, reviewName, reviewDate, reviewContents; // 변수 선언
                int reviewNum;
                float reviewRate;
                while(count < jsonArray.length())
                {
                    JSONObject object = jsonArray.getJSONObject(count); // 현재 배열의 원소값
                    reviewTitle = object.getString("reviewTitle");
                    reviewName = object.getString("reviewName");
                    reviewDate = object.getString("reviewDate");
                    reviewContents = object.getString("reviewContents");
                    reviewNum = object.getInt("reviewNum");
                    reviewRate = object.getInt("reviewRate");

                    if(reviewName.equals(MainActivity.nickname)) {
                        Review review = new Review(reviewNum, reviewTitle, reviewName, reviewDate, reviewContents, reviewRate); // 객체 생성 (생성자)
                        reviewList.add(review); // 리스트에 추가
                        adapter1.notifyDataSetChanged();
                    }
                    count++;



                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }


    }


}

