package com.example.bit_user.a5_29_restart.write_text_setting;


import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.bit_user.a5_29_restart.R;

import java.util.List;

public class ViewAdapter extends ArrayAdapter<FontList> {
    Context context;
    int resourceId;

    public ViewAdapter(Context context, int resourceId, List<FontList> li) { // 수정확인  <안에 타입 변경>
        super(context, resourceId, li);
        this.context = context;
        this.resourceId = resourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = mInflater.inflate(resourceId, null);
        // 코드수정부분 시작

        ((TextView) convertView.findViewById(R.id.fontview)).setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/"+getItem(position).getPath()));
        ((TextView) convertView.findViewById(R.id.fontview)).setText( getItem(position).getFontname());
        // 코드수정부분 끝
        return convertView;
    }
}