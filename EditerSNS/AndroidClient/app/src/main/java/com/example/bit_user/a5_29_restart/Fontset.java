package com.example.bit_user.a5_29_restart;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.bit_user.a5_29_restart.txtsetting.FontList;
import com.example.bit_user.a5_29_restart.txtsetting.ViewAdapter;

import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by bit-user on 2017-06-09.
 */

public class Fontset implements View.OnClickListener {
    static Context mMain;
    static Activity act;
    DialogInterface alertdig;
    ListView fontlv;

    public Fontset(Context mMain, Activity act){
        this.mMain = mMain;
        this.act = act;
    }

    @Override
    public void onClick(View v) {
        LinearLayout txtdwset = (LinearLayout) ((Activity)mMain).findViewById(R.id.txtdwset);
        LinearLayout txtset = (LinearLayout) ((Activity)mMain).findViewById(R.id.txtset);
        LinearLayout bgset = (LinearLayout) ((Activity)mMain).findViewById(R.id.bgset);
        LinearLayout sketchset = (LinearLayout) ((Activity) mMain).findViewById(R.id.sketchset);
        LinearLayout main_btn = (LinearLayout) ((Activity) mMain).findViewById(R.id.main_btn);
        switch (v.getId()){
            case R.id.txtfont:{
                LinkedList<FontList> fontli= fontSelectList();
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(mMain);
                LayoutInflater inflater = act.getLayoutInflater();
                View convertView = inflater.inflate(R.layout.font_viewlist, null);
                alertDialog.setView(convertView);
                alertDialog.setTitle("폰트 선택");
                fontlv = (ListView) convertView.findViewById(R.id.fontviewlist);
                ViewAdapter adapter=new ViewAdapter(mMain, R.layout.font_list, fontli);
                fontlv.setAdapter(adapter);
                alertDialog.setCancelable(true);
                fontlv.setOnItemClickListener(fontsel);
                alertdig = alertDialog.show();
            }break;
            case R.id.txtloc:{
                txtset.setVisibility(View.GONE);
                txtset.startAnimation(AnimationUtils.loadAnimation(act, R.anim.slide_right));
                txtdwset.setVisibility(View.VISIBLE);
                txtdwset.startAnimation(AnimationUtils.loadAnimation(act, R.anim.slide_right2));
            }break;
            case R.id.backbtn4:{

                txtset.setVisibility(View.GONE);
                txtset.startAnimation(AnimationUtils.loadAnimation(act, R.anim.slide_left2));
                main_btn.setVisibility(View.VISIBLE);
                main_btn.startAnimation(AnimationUtils.loadAnimation(act, R.anim.slide_left));
            }
        }
        txtset.setVisibility(View.GONE);
    }



    /* 폰트 선택 적용 event  */
    AdapterView.OnItemClickListener fontsel= new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            EditText edit_view = (EditText) ((Activity)mMain).findViewById(R.id.edit_view);
            LinearLayout mainbtn = (LinearLayout) ((Activity)mMain).findViewById(R.id.main_btn);
            fontlv = (ListView) parent;
            FontList item = (FontList) fontlv.getItemAtPosition(position);
            edit_view.setTypeface(Typeface.createFromAsset(mMain.getAssets(), "fonts/"+item.getPath()));
            mainbtn.setVisibility(View.VISIBLE);
            alertdig.dismiss();
        }
    };




    /* Font폴더 리스트 가져오기 */
    public LinkedList<FontList> fontSelectList(){
        LinkedList<FontList> fontli = new LinkedList<>();
        try{
            AssetManager asset=mMain.getAssets();
            String fontlist[] = asset.list("fonts");
            for(int a = 0; a < fontlist.length; a++){
                fontli.add(new FontList(fontlist[a],fontlist[a]));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return fontli;
    }


}
