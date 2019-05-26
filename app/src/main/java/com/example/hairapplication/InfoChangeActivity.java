package com.example.hairapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class InfoChangeActivity extends AppCompatActivity {

     private String  userID, userPassword, userName, userNickname, userPhone, userHairshop ;
    EditText passwordText, nameText, nicknameText, phoneText, hairshopText;
    TextView idText;
    EditText confirmText;
    AlertDialog dialog;
    private String ID;
    private String password;
    private String name;
    private String nickname;
    private String phone;
    private String hairshop;
    private boolean validate = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_change);

        passwordText = (EditText) findViewById(R.id.passwordText);
        nameText = (EditText) findViewById(R.id.nameText);
        nicknameText = (EditText)findViewById(R.id.nicknameText);
        phoneText = (EditText) findViewById(R.id.phoneText);
        hairshopText = (EditText) findViewById(R.id.hairshopText);
        idText = (TextView) findViewById(R.id.idText);
        confirmText = (EditText)findViewById(R.id.confirmText);

        Button cancelButton = (Button)findViewById(R.id.cancelButton);
        Button completeButton = (Button)findViewById(R.id.completeButton);
        final Button confirmButton = (Button)findViewById(R.id.confirmButton);

        if(MainActivity.type.equals("designer")){
            hairshopText.setVisibility(View.VISIBLE);
        }

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(confirmText.getText().toString().equals(userPassword)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(InfoChangeActivity.this);
                    dialog = builder.setMessage("확인되었습니다.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                    validate = true;
                    idText.setEnabled(false);
                    idText.setBackgroundColor(getResources().getColor(R.color.colorGray));
                    confirmButton.setBackgroundColor(getResources().getColor(R.color.colorGray));
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(InfoChangeActivity.this);
                    dialog = builder.setMessage("비밀번호가 다릅니다.")
                            .setNegativeButton("다시 시도", null)
                            .create();
                    dialog.show();
                }

            }
        });



        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ID = idText.getText().toString();
                password = passwordText.getText().toString();
                name = nameText.getText().toString();
                nickname = nicknameText.getText().toString();
                phone = phoneText.getText().toString();
                hairshop = hairshopText.getText().toString();

                if(!validate){
                    AlertDialog.Builder builder = new AlertDialog.Builder(InfoChangeActivity.this);
                    dialog = builder.setMessage("먼저 비밀번호를 확인해주세요")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;

                }if(ID.equals("")||password.equals("")||name.equals("")||hairshop.equals("")||nickname.equals("")||phone.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(InfoChangeActivity.this);
                    dialog = builder.setMessage("빈 칸 없이 입력해주세요")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }
                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){ // 회원 정보 수정에 성공했을 경우 성공 알림창 출력
                                AlertDialog.Builder builder = new AlertDialog.Builder(InfoChangeActivity.this);
                                dialog = builder.setMessage("회원 정보 수정에 성공했습니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();

                                //                               finish();
                                Intent completeIntent = new Intent(getApplicationContext(), MyPageActivity.class); // 마이페이지 화면으로 돌아감
                                startActivity(completeIntent);
                                finish();
                            }else{ // 회원 정보 수정에 실패한 경우 실패 알림창 출력
                                AlertDialog.Builder builder = new AlertDialog.Builder(InfoChangeActivity.this);
                                dialog = builder.setMessage("회원 정보 수정에 실패했습니다.")
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
                RegisterRequest changeRequest = new RegisterRequest(ID, password, name, nickname, phone, hairshop, 0, responseListener);
                RequestQueue queue = Volley.newRequestQueue(InfoChangeActivity.this);
                queue.add(changeRequest);
            }
        });




        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        new BackgroundTask().execute();
    }

    class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute(){
            try{
                target = "http://kyu9341.cafe24.com/GetUserInfo.php?ID="+URLEncoder.encode(MainActivity.userID, "UTF-8"); // GET 방식으로 ID를 서버에 전송
                Log.e("userID change = "+MainActivity.userID,"userID");
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

                    userID = object.getString("userID");
                    userPassword = object.getString("userPassword");
                    userName = object.getString("userName");
                    userNickname = object.getString("userNickname");
                    userPhone = object.getString("userPhone");
                    userHairshop = object.getString("userHairshop");

                Log.e("userNickname = "+userNickname , "userNickname");
                Log.e("userPassword = "+userPassword , "userPassword");
                Log.e("userName = "+userName , "userName");
                Log.e("userPhone = "+userPhone , "userPhone");
                Log.e("userHairshop = "+userHairshop , "userHairshop");

                    idText.setText(userID);
                    passwordText.setText(userPassword);
                    nameText.setText(userName);
                    nicknameText.setText(userNickname);
                    phoneText.setText(userPhone);
                    hairshopText.setText(userHairshop);




            }catch (Exception e){
                e.printStackTrace();
            }
        }


    }

}
