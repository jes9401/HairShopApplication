package com.example.hairapplication;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class PleaseListAdapter extends BaseAdapter {
    private Context context;
    private List<Please> pleaseList;

    public PleaseListAdapter(Context context, List<Please> pleaseList) {
        this.context = context;
        this.pleaseList = pleaseList; }
    @Override
    public int getCount() {
        return pleaseList.size();
    } // 세팅할 아이템의 개수

    @Override
    public Object getItem(int i) {
        return pleaseList.get(i);
    } // i 번째 아이템 정보 가져옴

    @Override
    public long getItemId(int i) {
        return i;
    } // 아이템의 인덱스를 가져옴

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final View v = View.inflate(context, R.layout.please, null); // 레이아웃 참조
        TextView pleaseText = (TextView)v.findViewById(R.id.pleaseText);
        TextView nameText = (TextView)v.findViewById(R.id.nameText);
        TextView dateText = (TextView)v.findViewById(R.id.dateText);

        pleaseText.setText(pleaseList.get(i).getPlease());
        nameText.setText(pleaseList.get(i).getName());
        dateText.setText(pleaseList.get(i).getDate());

        v.setTag(pleaseList.get(i).getPlease());

        return v;
    }

}
