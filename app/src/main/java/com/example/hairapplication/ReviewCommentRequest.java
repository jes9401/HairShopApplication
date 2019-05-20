package com.example.hairapplication;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ReviewCommentRequest extends StringRequest {
    final static private String URL = "http://kyu9341.cafe24.com/ReviewCommentWrite.php";
    private Map<String, String> parameters;

    public ReviewCommentRequest(int reviewCommentIndex, String reviewCommentName, String reviewCommentDate, String reviewCommentContents, Response.Listener<String> listener){
        super(Method.POST, URL, listener,null); // 해당 URL에 POST 방식으로 전송
        parameters = new HashMap<>(); // HashMap으로 초기화
        parameters.put("reviewCommentIndex", reviewCommentIndex+"");
        parameters.put("reviewCommentName", reviewCommentName);
        parameters.put("reviewCommentDate", reviewCommentDate);
        parameters.put("reviewCommentContents", reviewCommentContents);

    }

    @Override
    public Map<String, String> getParams(){
        return parameters;

    }


}


