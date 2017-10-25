package com.example.bit_user.a5_29_restart;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by bit-user on 2017-06-07.
 */
class MyViewAdapter extends ArrayAdapter<BgImageList> { // 수정확인    <안에 타입 변경>
    Context context;
    int resourceId;
    public MyViewAdapter(Context context, int resourceId, List<BgImageList> li) { // 수정확인  <안에 타입 변경>
        super(context, resourceId, li);
        this.context = context;
        this.resourceId = resourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = mInflater.inflate(resourceId, null);

        // 코드수정부분 시작
        ((ImageView) convertView.findViewById(R.id.bgimg)).setImageResource(getItem(position).getId());  //Image추가 단
        // 코드수정부분 끝
        return convertView;
    }

}
