package com.example.hairapplication;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PleaseRequest extends StringRequest {
    final static private String URL = "http://kyu9341.cafe24.com/PleaseWrite.php";
    final static private String pleasereviseURL = "http://kyu9341.cafe24.com/PleaseReviseWrite.php";
    private Map<String, String> parameters;

    public PleaseRequest(String pleaseTitle, String pleaseName, String pleaseDate, String pleaseContents, String pleaseImage, int access, Response.Listener<String> listener){
        super(Method.POST, URL, listener,null); // 해당 URL에 POST 방식으로 전송
        parameters = new HashMap<>(); // HashMap으로 초기화
        parameters.put("pleaseTitle", pleaseTitle);
        parameters.put("pleaseName", pleaseName);
        parameters.put("pleaseDate", pleaseDate);
        parameters.put("pleaseContents", pleaseContents);
        parameters.put("pleaseImage", pleaseImage);
        parameters.put("access", access+"");
    }

    public PleaseRequest(int pleaseNum, String pleaseTitle, String pleaseDate, String pleaseContents, String pleaseImage, int access, Response.Listener<String> listener){
        super(Method.POST, pleasereviseURL, listener,null); // 해당 URL에 POST 방식으로 전송
        parameters = new HashMap<>(); // HashMap으로 초기화
        parameters.put("pleaseNum", pleaseNum+"");
        parameters.put("pleaseTitle", pleaseTitle);
        parameters.put("pleaseDate", pleaseDate);
        parameters.put("pleaseContents", pleaseContents);
        parameters.put("pleaseImage", pleaseImage);
        parameters.put("access", access+"");
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;

    }


}


