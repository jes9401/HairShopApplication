package com.example.hairapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class MainFragment extends Fragment {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(Bundle b){
        super.onActivityCreated(b);



    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        // findViewById함수 사용 위해 필요
        View view = inflater.inflate(R.layout.fragment_main, container, false); // Fragment onCreateView 함수에서 View 객체에 현재 View에 가져와 그 View에서 원하는 컴포넌트를 찾음


        return view;
    }


}
