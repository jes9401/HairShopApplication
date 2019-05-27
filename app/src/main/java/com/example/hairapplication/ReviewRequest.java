package com.example.hairapplication;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ReviewRequest extends StringRequest {
    final static private String URL = "http://kyu9341.cafe24.com/ReviewWrite.php";
    private Map<String, String> parameters;

    public ReviewRequest(String reviewTitle, String reviewName, String reviewDate, String reviewContents, float reviewRate, Response.Listener<String> listener){
        super(Method.POST, URL, listener,null); // 해당 URL에 POST 방식으로 전송
        parameters = new HashMap<>(); // HashMap으로 초기화
        parameters.put("reviewTitle", reviewTitle);
        parameters.put("reviewName", reviewName);
        parameters.put("reviewDate", reviewDate);
        parameters.put("reviewContents", reviewContents);
        parameters.put("reviewRate", reviewRate+"");
    }
    @Override
    public Map<String, String> getParams(){
        return parameters;
    }

}


