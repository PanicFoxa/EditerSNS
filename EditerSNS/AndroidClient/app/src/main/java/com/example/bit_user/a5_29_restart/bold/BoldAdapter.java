package com.example.bit_user.a5_29_restart.bold;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bit_user.a5_29_restart.R;

import java.util.List;

/**
 * Created by bit-user on 2017-06-30.
 */

public class BoldAdapter extends ArrayAdapter<BoldList> {
    Context context;
    int resourceId;

    public BoldAdapter(Context context, int resourceId, List<BoldList> li) { // 수정확인  <안에 타입 변경>
        super(context, resourceId, li);
        this.context = context;
        this.resourceId = resourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = mInflater.inflate(resourceId, null);
        // 코드수정부분 시작

        ((ImageView) convertView.findViewById(R.id.boldimg)).setImageResource(getItem(position).getImagesrc());  //Image추가 단
        ((TextView) convertView.findViewById(R.id.boldsize)).setText(""+getItem(position).getSize());  //Image추가 단

        // 코드수정부분 끝
        return convertView;
    }

}
