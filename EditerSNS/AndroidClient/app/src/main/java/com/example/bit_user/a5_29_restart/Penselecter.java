package com.example.bit_user.a5_29_restart;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.bit_user.a5_29_restart.bold.BoldAdapter;
import com.example.bit_user.a5_29_restart.bold.BoldList;

import java.util.LinkedList;

/**
 * Created by bit-user on 2017-06-09.
 */

public class Penselecter implements View.OnClickListener {

    Context mMain;
    Activity act;
    DialogInterface alertdig;
    DialogInterface backalertdig;
    GridView penboldlist;
    public static BrushSelected listener;


    Penselecter(Context mMain, Activity act, DialogInterface al){
        this.mMain = mMain;
        this.act = act;
        this.backalertdig = al;
    }

    /* 스케치 하위 펜슬 선택 */

    @Override
    public void onClick(View v) {
        View innerView = act.getLayoutInflater().inflate(R.layout.pen_bold, null);
        AlertDialog.Builder penboldal = new AlertDialog.Builder(mMain);
        String type = null;
        switch(v.getId()){
            case R.id.pen :{ type = "연필"; listener.onBrushsel(2f,type); backalertdig.dismiss();} break;
            case R.id.bollpen : type = "볼펜";
                break;
            case R.id.magic : type = "매직";
                break;
            case R.id.boot : type = "붓";
                break;
        }
        penboldal.setTitle(type +" 두께 설정");
        if(!type.equals("연필")){
            LinkedList<BoldList> bllist = BoldSelectList(type);
            penboldlist = (GridView) innerView.findViewById(R.id.penboldlist);
            BoldAdapter adapter = new BoldAdapter(act, R.layout.pen_boldlist,bllist);
            penboldlist.setAdapter(adapter);
            penboldal.setCancelable(true);
            penboldal.setView(innerView);
            alertdig = penboldal.show();
            penboldlist.setOnItemClickListener(boldsel);
        }
    }

    public LinkedList<BoldList> BoldSelectList(String type){
        LinkedList<BoldList> blli = new LinkedList<BoldList>();
        for(int a = 0; a<10; a++){
            blli.add(new BoldList(6+a*2f, R.mipmap.ic_launcher,type));
        }
        return blli;
    }


    AdapterView.OnItemClickListener boldsel= new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            penboldlist = (GridView) parent;
            BoldList item = (BoldList) penboldlist.getItemAtPosition(position);
            float size = item.getSize();
            String type = item.getPtype();
            listener.onBrushsel(size,type);
            backalertdig.dismiss();
            alertdig.dismiss();
        }
    };
}
