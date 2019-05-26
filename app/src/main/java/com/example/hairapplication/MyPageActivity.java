package com.example.hairapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
public class MyPageActivity extends AppCompatActivity {

    EditText designerText; // 디자이너의 닉네임을 받아올 EditText
    private String designerPhone; // 서버에서 받아온 디자이너의 번호를 저장할 변수
    TextView phoneNumber; // 서버에서 받아온 디자이너의 번호를 표시해줄 TextView
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        Button myWriting = (Button)findViewById(R.id.mywriting);
        Button btnDial = (Button)findViewById(R.id.btnDial);
        Button btnSms = (Button)findViewById(R.id.btnSms);
        Button choiceButton = (Button)findViewById(R.id.choice);
        designerText = (EditText)findViewById(R.id.designerText);
        phoneNumber = (TextView)findViewById(R.id.phoneNumber);
        TextView userNickname = (TextView)findViewById(R.id.userNickname);
        Button infochangeButton = (Button)findViewById(R.id.infochange);

        userNickname.setText(MainActivity.nickname+ "님");

        myWriting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyWritingActivity.class);
                startActivity(intent);
            }
        });

        infochangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InfoChangeActivity.class);
                startActivity(intent);
            }
        });


        final String designer = designerText.getContext().toString();

        choiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MyPageActivity.BackgroundTask().execute(); // 데이터베이스 연동

                //                Toast.makeText(getApplicationContext(), "출력할 문자열", Toast.LENGTH_LONG).show();
      //          Toast.makeText(getApplicationContext(),designer+" 디자이너 선택" + designerPhone, Toast.LENGTH_LONG).show();


            }
        });





        btnDial.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Uri uri = Uri.parse("tel:"+designerPhone);
                Intent intent = new Intent(Intent.ACTION_DIAL,uri);
                startActivity(intent);
            }
        });//전화걸기


        btnSms.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.putExtra("sms_body", "문의드려요");
                intent.setData(Uri.parse("smsto:"+Uri.encode(designerPhone)));
                startActivity(intent);        } }); //문자보내기

        final Button logout = (Button) findViewById(R.id.logout);


        //로그아웃
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
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
                finish();
            }
        });
        mypageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

            }
        });

            mapButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Intent intent = new Intent(getApplicationContext(),MapActivity.class);
                    startActivity(intent);
                }
            });

    }

    class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute(){
            try{
                target = "http://kyu9341.cafe24.com/GetDesignerPhone.php?nickname="+URLEncoder.encode(designerText.getText().toString(), "UTF-8"); // GET 방식으로 ID를 서버에 전송
                Log.e("nickname = "+designerText.getText().toString(),"nickname try Phone");
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

                designerPhone = object.getString("phone");
                Log.e("designerPhone = "+designerPhone , "designerPhone Back");

                phoneNumber.setText(designerPhone);

            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

}

