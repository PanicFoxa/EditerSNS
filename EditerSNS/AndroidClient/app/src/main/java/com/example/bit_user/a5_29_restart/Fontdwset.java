package com.example.bit_user.a5_29_restart;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

/**
 * Created by bit-user on 2017-06-09.
 */

public class Fontdwset implements View.OnClickListener {
    static Context mMain;
    static Activity act;
    public DialogInterface alertdig;
    ListView fontlv;
    Spinner fontspinner;
    Integer fontsize;


    public Fontdwset(Context mMain, Activity act){
        this.mMain = mMain;
        this.act = act;
    }

    @Override
    public void onClick(View v) {
        View innerView = null;
        AlertDialog.Builder adialog = null;
        LinearLayout txt_loc_select;
        final EditText edit_view = (EditText) ((Activity)mMain).findViewById(R.id.edit_view);
        final LinearLayout txtdwset = (LinearLayout) ((Activity)mMain).findViewById(R.id.txtdwset);
        final LinearLayout txtset = (LinearLayout) ((Activity) mMain).findViewById(R.id.txtset);
        LinearLayout bgset = (LinearLayout) ((Activity)mMain).findViewById(R.id.bgset);

        LinearLayout sketchset = (LinearLayout) ((Activity) mMain).findViewById(R.id.sketchset);

        switch (v.getId()){
            case R.id.fontsize : {
                innerView = act.getLayoutInflater().inflate(R.layout.font_spinner, null);
                adialog= new AlertDialog.Builder(mMain);
                adialog.setView(innerView);
                fontspinner = (Spinner) innerView.findViewById(R.id.fontspinner);
                fontspinnerset();
                adialog.setTitle("fontsize설정");
                adialog.setPositiveButton("설정", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        edit_view.setTextSize(fontsize);
                        txtdwset.setVisibility(View.GONE);
                        txtdwset.startAnimation(AnimationUtils.loadAnimation(act, R.anim.slide_left2));
                        txtset.setVisibility(View.VISIBLE);
                        txtset.startAnimation(AnimationUtils.loadAnimation(act, R.anim.slide_left));
                    }
                });
                adialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertdig.dismiss();
                    }
                });
                alertdig = adialog.show();
            }break;
            case R.id.txtgrb : {
                innerView = act.getLayoutInflater().inflate(R.layout.font_positionsel, null);
                adialog= new AlertDialog.Builder(mMain);
                adialog.setView(innerView);
                txt_loc_select = (LinearLayout) innerView.findViewById(R.id.txt_loc_select);
                adialog.setTitle("위치 설정");
                alertdig = adialog.show();
            }break;
            case R.id.backbtn5:{

                txtdwset.setVisibility(View.GONE);
                txtdwset.startAnimation(AnimationUtils.loadAnimation(act, R.anim.slide_left2));
                txtset.setVisibility(View.VISIBLE);
                txtset.startAnimation(AnimationUtils.loadAnimation(act, R.anim.slide_left));
            }
        }
        txtdwset.setVisibility(View.GONE);
    }

    /* font size 스피너 설정 */
    private void fontspinnerset(){
        Integer [] ar = new Integer [10];
        for(int a = 0; a<ar.length; a++){
            ar[a] = a*2+14;
        }
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(act, android.R.layout.simple_spinner_item, ar);
        fontspinner.setAdapter(adapter);
        fontspinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fontsize = Integer.parseInt(""+parent.getSelectedItem());

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }


}
