package com.example.hairapplication;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PleaseDeleteRequest extends StringRequest {
    final static private String URL = "http://kyu9341.cafe24.com/PleaseDelete.php";
    private Map<String, String> parameters;

    public PleaseDeleteRequest(String pleaseTitle, String pleaseName, Response.Listener<String> listener){
        super(Method.POST, URL, listener,null); // 해당 URL에 POST 방식으로 전송
        parameters = new HashMap<>(); // HashMap으로 초기화
        parameters.put("pleaseTitle", pleaseTitle);
        parameters.put("pleaseName", pleaseName);

    }

    @Override
    public Map<String, String> getParams(){
        return parameters;

    }


}


