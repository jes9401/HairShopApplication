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

public class PleaseReviseActivity extends AppCompatActivity {

    TextView writerText;
    EditText titleText;
    TextView dateText;
    EditText contentsText;
    private String writer, title, date, contents;
    private String secret;
    AlertDialog dialog;
    private int pleaseNum;
    private int access;
    private int num;
    private String pleaseTitle, pleaseContents, reviseDate;

    RadioButton open;
    RadioButton notopen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_please_revise);

        writerText = (TextView) findViewById(R.id.writer);
        titleText = (EditText) findViewById(R.id.titleText);
        dateText = (TextView) findViewById(R.id.dateText);
        contentsText = (EditText)findViewById(R.id.contentsText);
        RadioGroup secretGroup = (RadioGroup)findViewById(R.id.secretGroup);
        open = (RadioButton)findViewById(R.id.open);
        notopen = (RadioButton)findViewById(R.id.notopen);
        Button completeButton = (Button)findViewById(R.id.completeButton);
        final CheckBox pictureCheck = (CheckBox)findViewById(R.id.pictureCheck);
        final FrameLayout pleaseFrameLayout = (FrameLayout)findViewById(R.id.pleaseFrameLayout);

        long now = System.currentTimeMillis();  // 현재 시간 받아오기
        Date date1 = new Date(now);
        SimpleDateFormat CurDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        reviseDate = CurDateFormat.format(date1);
        dateText.setText(reviseDate);
        reviseDate = reviseDate+" 수정됨";
        Intent intent = getIntent();
        pleaseNum = intent.getIntExtra("Num", 0);
        Log.e("pleaseNum = "+pleaseNum, "pleaseNum");

        pleaseFrameLayout.setVisibility(View.GONE);

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

         new BackgroundTask().execute();

        secretGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { // 성별 라디오 버튼에 대한 이벤트처리
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton secretButton = (RadioButton)findViewById(i); // 현재 선택된 라디오버튼 받아옴
                secret = secretButton.getText().toString();
                if(secret.equals("공개")){
                    access = 1;
                }else {
                    access = 0;
                }
                Log.e("access = "+ access, "access");
            }
        });

        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pleaseTitle = titleText.getText().toString();
                pleaseContents = contentsText.getText().toString();

                if (pleaseContents.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PleaseReviseActivity.this);
                    dialog = builder.setMessage("내용을 입력해주세요.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }
                if (pleaseTitle.equals("")) {
                    pleaseTitle = "제목 없음";
                }


                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) { // 작성에 성공했을 경우 성공 알림창 출력
                                AlertDialog.Builder builder = new AlertDialog.Builder(PleaseReviseActivity.this);
                                dialog = builder.setMessage("수정에 성공했습니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();


                            } else { // 작성에 실패한 경우 실패 알림창 출력
                                AlertDialog.Builder builder = new AlertDialog.Builder(PleaseReviseActivity.this);
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
                PleaseRequest reviseRequest = new PleaseRequest(num, pleaseTitle, reviseDate, pleaseContents, access, responseListener);
                RequestQueue queue = Volley.newRequestQueue(PleaseReviseActivity.this);
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
                target = "http://kyu9341.cafe24.com/PleaseRevise.php?pleaseNum="+URLEncoder.encode(pleaseNum+""); // GET 방식으로 ID를 서버에 전송
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


                writer = object.getString("pleaseName");
                title = object.getString("pleaseTitle");
                date = object.getString("pleaseDate");
                contents = object.getString("pleaseContents");
                access = object.getInt("access");
                num = object.getInt("pleaseNum");

                Log.e("writer = "+writer , "writer");
                Log.e("title = "+title , "title");
                Log.e("date = "+date , "date");
                Log.e("contents = "+contents , "contents");

                writerText.setText(writer);
                titleText.setText(title);
                contentsText.setText(contents);

                if(access == 1){
                    open.setChecked(true);
                }else{
                    notopen.setChecked(true);
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }


    }

}
