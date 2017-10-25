package com.example.bit_user.a5_29_restart;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ToggleButton;

import static com.example.bit_user.a5_29_restart.R.id.setgase;

/**
 * Created by bit-user on 2017-06-09.
 */

public class Main_togev implements ToggleButton.OnCheckedChangeListener {
    static Context mMain;
    int gaseset=0;



    public Main_togev(Context mMain){
        this.mMain = mMain;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        EditText edit_view = (EditText) ((Activity)mMain).findViewById(R.id.edit_view);
        ToggleButton setgasebtn = (ToggleButton) ((Activity)mMain).findViewById(setgase);
        switch(buttonView.getId()){
            case R.id.txtcolor:{
                if(isChecked){
                    edit_view.setTextColor(Color.WHITE);
                }else{
                    edit_view.setTextColor(Color.BLACK);
                }
            }break;

            case R.id.txtweg:{
                if(isChecked){
                    edit_view.setPaintFlags(edit_view.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
                }else{
                    edit_view.setPaintFlags(edit_view.getPaintFlags() &~ Paint.FAKE_BOLD_TEXT_FLAG);
                }
            }break;

            case R.id.setgase:{
                String setgase;

                if(isChecked){
                    setgase = (edit_view.getText()).toString();
                    String results = "";
                    for(int a = 0; a < setgase.length(); a++){
                        results += setgase.charAt(a)+"\n";
                    }
                    gaseset=1;
                    edit_view.setText(results);
                }else{
                    setgase = edit_view.getText().toString();
                    setgase = setgase.replaceAll("\n", "");
                    edit_view.setText(setgase);
                    gaseset=0;
                }
            }break;
        }
    }

    public int gaseset(){
        return gaseset;
    }

}
