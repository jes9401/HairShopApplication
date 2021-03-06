package com.example.hairapplication;

import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

    public class RegisterRequest extends StringRequest {
        final static private String URL = "http://kyu9341.cafe24.com/UserRegister.php";
        final static private String designerURL = "http://kyu9341.cafe24.com/DesignerRegister.php";
        final static private String changeURL ="http://kyu9341.cafe24.com/InfoChange.php";

        private Map<String, String> parameters;

        public RegisterRequest(String ID, String password, String name, String gender, String nickname, String phone, Response.Listener<String> listener){
            super(Method.POST, URL, listener,null); // 해당 URL에 POST 방식으로 전송
            parameters = new HashMap<>(); // HashMap으로 초기화
            parameters.put("ID", ID);
            parameters.put("password", password);
            parameters.put("name", name);
            parameters.put("gender",gender);
            parameters.put("nickname", nickname);
            parameters.put("phone", phone);

        }
        public RegisterRequest(String ID, String password, String name, String gender, String nickname, String phone, String hairshop, String hairshopTelnum, Response.Listener<String> listener){
            super(Method.POST, designerURL, listener,null); // 해당 URL에 POST 방식으로 전송
            parameters = new HashMap<>(); // HashMap으로 초기화
            parameters.put("ID", ID);
            parameters.put("password", password);
            parameters.put("name", name);
            parameters.put("gender",gender);
            parameters.put("nickname", nickname);
            parameters.put("phone", phone);
            parameters.put("hairshop", hairshop);
            parameters.put("hairshopTelnum", hairshopTelnum);

        }


        public RegisterRequest(String ID, String password, String name, String nickname, String phone, String hairshop, int temp, Response.Listener<String> listener){
            super(Method.POST, changeURL, listener,null); // 해당 URL에 POST 방식으로 전송
            parameters = new HashMap<>(); // HashMap으로 초기화
            parameters.put("ID", ID);
            parameters.put("password", password);
            parameters.put("name", name);
            parameters.put("nickname", nickname);
            parameters.put("phone", phone);
            parameters.put("hairshop", hairshop);
            parameters.put("temp", temp+"");
        }



        @Override
        public Map<String, String> getParams(){
            return parameters;
        }
    }


