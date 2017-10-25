package com.example.bit_user.a5_29_restart;


import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

/**
 * Created by bit-user on 2017-06-09.
 */

public class Main_Btnev implements View.OnClickListener {
    static Context mMain;
    static Activity act;

    public Main_Btnev(Context mMain,Activity act){
        this.mMain = mMain;
        this.act = act;
    }


    /* 텍스트 설정 배경설정 버튼 이벤트 */

    @Override
    public void onClick(View v) {
        LinearLayout bgset = (LinearLayout) ((Activity) mMain).findViewById(R.id.bgset);
        LinearLayout txtset = (LinearLayout) ((Activity) mMain).findViewById(R.id.txtset);
        LinearLayout txtdwset = (LinearLayout) ((Activity) mMain).findViewById(R.id.txtdwset);
        LinearLayout sketchset = (LinearLayout) ((Activity) mMain).findViewById(R.id.sketchset);
        LinearLayout skcontain = (LinearLayout) ((Activity)mMain).findViewById(R.id.skcontain);
        LinearLayout main_btn = (LinearLayout)((Activity)mMain).findViewById(R.id.main_btn);

        LinearLayout sw = null;

        switch (v.getId()) {
            case R.id.bgbtn: sw = bgset;
                break;

            case R.id.write_txt_btn: sw = txtset;
                break;

            /*case R.id.sketchbtn: sw = sketchset;
                break;*/


        }

        if(sw.getVisibility() == View.GONE){
            bgset.setVisibility(View.GONE);
            txtset.setVisibility(View.GONE);
            sketchset.setVisibility(View.GONE);
            txtdwset.setVisibility(View.GONE);
            main_btn.setVisibility(View.GONE);
            main_btn.startAnimation(AnimationUtils.loadAnimation(act, R.anim.slide_right));
            sw.setVisibility(View.VISIBLE);
            sw.startAnimation(AnimationUtils.loadAnimation(act, R.anim.slide_right2));
        }else{
            sw.setVisibility(View.GONE);
        }
        skcontain.setVisibility(View.INVISIBLE);
    }
}
