package com.example.hairapplication;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private String ID;
    private String password;
    private String gender;
    private String name;
    private String nickname;
    private String phone;
    private AlertDialog dialog; // 알림창
    private boolean validate = false; // 사용할 수 있는 ID인지 체크해주는 변수


    RadioGroup genderGroup;
    RadioButton designer, user;
    Button nextBtn;
    Button completeBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText idText = (EditText)findViewById(R.id.idText);
        final EditText passwordText = (EditText)findViewById(R.id.passwordText);
        final EditText nameText = (EditText)findViewById(R.id.nameText);
        final EditText nicknameText = (EditText)findViewById(R.id.nicknameText);
        final EditText phoneText = (EditText)findViewById(R.id.phoneText);


        genderGroup = (RadioGroup)findViewById(R.id.genderGroup);
        designer = (RadioButton)findViewById(R.id.designer);
        user = (RadioButton)findViewById(R.id.user);

        int genderGroupID = genderGroup.getCheckedRadioButtonId();
        gender = ((RadioButton)findViewById(genderGroupID)).getText().toString(); // 성별 받아오기

        nextBtn = (Button) findViewById(R.id.nextBtn);
        completeBtn = (Button)findViewById(R.id.completeBtn);

        nextBtn.setVisibility(View.INVISIBLE);

        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { // 성별 라디오 버튼에 대한 이벤트처리
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton genderButton = (RadioButton)findViewById(i); // 현재 선택된 라디오버튼 받아옴
                gender = genderButton.getText().toString();
            }
        });

        final Button validateBtn = (Button)findViewById(R.id.validateBtn);
        validateBtn.setOnClickListener(new View.OnClickListener() {  // 중복확인 시 이벤트 처리
            @Override
            public void onClick(View view) {
                String ID = idText.getText().toString();
                if(validate){ // 중복확인이 이루어져 있다면
                    return;   //함수 종료
                }
                if(ID.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("아이디는 빈 칸일 수 없습니다.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("사용할 수 있는 아이디입니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                                idText.setEnabled(false);
                                validate = true;
                                idText.setBackgroundColor(getResources().getColor(R.color.colorGray));
                                validateBtn.setBackgroundColor(getResources().getColor(R.color.colorGray));
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("사용할 수 없는 아이디입니다.")
                                        .setNegativeButton("확인", null)
                                        .create();
                                dialog.show();
                            }

                        }

                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                ValidateRequest validateRequest = new ValidateRequest(ID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(validateRequest);
            }
        });



        user.setOnClickListener(new View.OnClickListener(){  // 라디오버튼 사용자 선택시

            @Override
            public void onClick(View view) {

                completeBtn.setVisibility(View.VISIBLE);
                nextBtn.setVisibility(View.INVISIBLE);
            }
        });

        designer.setOnClickListener(new View.OnClickListener(){  // 라디오버튼 디자이너 선택시

            @Override
            public void onClick(View view) {
                completeBtn.setVisibility(View.INVISIBLE);
                nextBtn.setVisibility(View.VISIBLE);
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() { // 디자이너 선택 시 다음 디자이너 세부사항 페이지로 이동

            @Override
            public void onClick(View view) {
                ID = idText.getText().toString();
                password = passwordText.getText().toString();
                name = nameText.getText().toString();
                nickname = nicknameText.getText().toString();
                phone = phoneText.getText().toString();


                Intent completeIntent = new Intent(getApplicationContext(), RegisterDesignerActivity.class);
                completeIntent.putExtra("ID", ID);
                completeIntent.putExtra("password", password);
                completeIntent.putExtra("name", name);
                completeIntent.putExtra("nickname", nickname);
                completeIntent.putExtra("phone", phone);
                completeIntent.putExtra("gender", gender);
                startActivity(completeIntent);
            }
        });

        completeBtn.setOnClickListener(new View.OnClickListener() { // 사용자 가입 시 회원가입 후 로그인페이지로 이동
            @Override
            public void onClick(View view) {
                ID = idText.getText().toString();
                password = passwordText.getText().toString();
                name = nameText.getText().toString();
                nickname = nicknameText.getText().toString();
                phone = phoneText.getText().toString();

                if(!validate){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("먼저 중복체크를 해주세요")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;

                }if(ID.equals("")||password.equals("")||name.equals("")||gender.equals("")||nickname.equals("")||phone.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
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
                                if(success){ // 회원 등록에 성공했을 경우 성공 알림창 출력
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                    dialog = builder.setMessage("회원 등록에 성공했습니다.")
                                            .setPositiveButton("확인", null)
                                            .create();
                                    dialog.show();

             //                               finish();
                                    Intent completeIntent = new Intent(getApplicationContext(), LoginActivity.class); // 로그인 화면으로 돌아감
                                    startActivity(completeIntent);
                                }else{ // 회원 등록에 실패한 경우 실패 알림창 출력
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                    dialog = builder.setMessage("회원 등록에 실패했습니다.")
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
                RegisterRequest registerRequest = new RegisterRequest(ID, password, name, gender, nickname, phone, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });



    }

    @Override
    protected void onStop(){ // 회원등록창이 꺼지면 실행
        super.onStop();
        if(dialog != null)
        {
            dialog.dismiss();
            dialog = null;
        }

    }

}
