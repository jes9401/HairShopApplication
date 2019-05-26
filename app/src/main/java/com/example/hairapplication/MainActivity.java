package com.example.hairapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    public static String nickname;
    public static String type;
    public static String userID;
    private AlertDialog dialog;
    ImageButton homeButton, mypageButton, mapButton;

    TextView test;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Button buttonToday = (Button)findViewById(R.id.buttonToday);
        final Button buttonStyle = (Button)findViewById(R.id.buttonStyle);
        final Button buttonPlease = (Button)findViewById(R.id.buttonPlease);
        final Button buttonReview = (Button)findViewById(R.id.buttonReview);
        final ImageButton helpButton = (ImageButton)findViewById(R.id.helpButton);

        Intent intent = getIntent();
        userID = intent.getStringExtra("userID"); // 로그인한 사용자의 닉네임을 받아옴

        new MainActivity.BackgroundTask().execute(); // 데이터베이스 연동


        // 메인 화면 오늘의 참견으로 설정
        buttonToday.setBackgroundColor(getResources().getColor(R.color.colorMainDark));
        buttonStyle.setBackgroundColor(getResources().getColor(R.color.colorMain));
        buttonPlease.setBackgroundColor(getResources().getColor(R.color.colorMain));
        buttonReview.setBackgroundColor(getResources().getColor(R.color.colorMain));
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_conatiner, new MainFragment());
        fragmentTransaction.commit();



        // 스타일 화면으로 프레그먼트 전환
        buttonStyle.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                buttonToday.setBackgroundColor(getResources().getColor(R.color.colorMain));
                buttonStyle.setBackgroundColor(getResources().getColor(R.color.colorMainDark));
                buttonPlease.setBackgroundColor(getResources().getColor(R.color.colorMain));
                buttonReview.setBackgroundColor(getResources().getColor(R.color.colorMain));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_conatiner, new StyleFragment());
                fragmentTransaction.commit();

            }
        });
        // 참견해주세요 화면으로 프레그먼트 전환
        buttonPlease.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                buttonToday.setBackgroundColor(getResources().getColor(R.color.colorMain));
                buttonStyle.setBackgroundColor(getResources().getColor(R.color.colorMain));
                buttonPlease.setBackgroundColor(getResources().getColor(R.color.colorMainDark));
                buttonReview.setBackgroundColor(getResources().getColor(R.color.colorMain));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_conatiner, new PleaseFragment());
                fragmentTransaction.commit();
                Fragment fragment = new PleaseFragment(); // Fragment 생성
                Bundle bundle = new Bundle(1); // 파라미터는 전달할 데이터 개수
                bundle.putString("nickname", nickname); // key , value
                fragment.setArguments(bundle);


            }
        });
        // 후기 화면으로 프레그먼트 전환
        buttonReview.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                buttonToday.setBackgroundColor(getResources().getColor(R.color.colorMain));
                buttonStyle.setBackgroundColor(getResources().getColor(R.color.colorMain));
                buttonPlease.setBackgroundColor(getResources().getColor(R.color.colorMain));
                buttonReview.setBackgroundColor(getResources().getColor(R.color.colorMainDark));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_conatiner, new ReviewFragment());
                fragmentTransaction.commit();

            }
        });
        // 메인 화면으로 프레그먼트 전환
        buttonToday.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                buttonToday.setBackgroundColor(getResources().getColor(R.color.colorMainDark));
                buttonStyle.setBackgroundColor(getResources().getColor(R.color.colorMain));
                buttonPlease.setBackgroundColor(getResources().getColor(R.color.colorMain));
                buttonReview.setBackgroundColor(getResources().getColor(R.color.colorMain));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_conatiner, new MainFragment());
                fragmentTransaction.commit();





            }
        });

 //       new MainActivity.BackgroundTask().execute(); // 데이터베이스 연동


        //도움말 버튼
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                dialog = builder.setMessage("헤어의 참견을 소개합니다.\n" +
                        "\n" +
                        "- 오늘의 참견 : 요즘 핫한 헤어스타일이 궁금하다구요?\n" +
                        "매달 업로드되는 추천 헤어스타일을 볼 수 있습니다.\n" +
                        "\n" +
                        "- 스타일 : 어떤 헤어스타일들이 있는지 궁금하다구요?\n" +
                        "아이콘을 선택하여 원하는 헤어스타일을 볼 수 있습니다.\n" +
                        "\n" +
                        "- 참견해주세요 : 어떤 머리를 할 지 고민된다구요?\n" +
                        "참견해주세요에서 헤어디자이너의 추천 및 코멘트를 받아볼 수 있습니다.\n" +
                        "\n" +
                        "- 후기 : 추천 받은 헤어스타일은 어떠신가요?\n" +
                        "좋았던 점이나 아쉬웠던점을 후기로 올릴 수 있습니다.\n" +
                        "\n" +
                        "- 마이페이지 : 내가 썼던 글과 댓글을 쉽게 보고싶다구요?\n" +
                        "마이페이지에서 내가 쓴 글과 원하는 디자이너와 연락을 할 수 있습니다.\n" +
                        "\n" +
                        "- 주변 미용실찾기 : 내 주변 미용실이 궁금하다구요?\n" +
                        "주변 미용실 찾기에서 내 주변 미용실을 볼 수 있습니다.")
                        .setPositiveButton("확인", null)
                        .create();
                dialog.show();
            }
        });





        //하단바 이동

        homeButton = (ImageButton)findViewById(R.id.homeButton);
        mypageButton = (ImageButton)findViewById(R.id.mypageButton);
        mapButton = (ImageButton)findViewById(R.id.mapButton);

        homeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

            }
        });
        mypageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.e("nickname = "+nickname , "nickname");
                Intent intent = new Intent(getApplicationContext(), MyPageActivity.class);
                startActivity(intent);

            }
        });

            mapButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                    startActivity(intent);
                }
            });




        Log.e("nickname = "+nickname , "nickname");
    }


    class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute(){
            try{
                target = "http://kyu9341.cafe24.com/getNickname.php?userID="+URLEncoder.encode(userID, "UTF-8"); // GET 방식으로 ID를 서버에 전송
                Log.e("useID = "+userID,"");
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


                nickname = object.getString("nickname");
                Log.e("nickname = "+nickname , "nickname");
                type = object.getString("type");
                Log.e("nick type = "+type,"type");




            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }


}
