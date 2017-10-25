package com.example.bit_user.a5_29_restart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.lang.reflect.Field;
import java.util.LinkedList;


public class BasicbgSelect extends AppCompatActivity {

    GridView bglist;
    MyViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basicbg_sel);

        bglist =(GridView) findViewById(R.id.bglist);
        LinkedList <BgImageList> li = bglistRaw();

        adapter=new MyViewAdapter(this, R.layout.basicbg_list, li);
        bglist.setAdapter(adapter);
        bglist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bglist = (GridView) parent;
                BgImageList item = (BgImageList) bglist.getItemAtPosition(position);
                Intent intent = new Intent();
                intent.putExtra("imgnum",item.getId());
                setResult(1000, intent);
                finish();
                overridePendingTransition(R.anim.slide_down2, R.anim.slide_up2);
            }
        });
    }

    private LinkedList<BgImageList> bglistRaw(){
        Field[] fields=R.drawable.class.getFields();
        LinkedList<BgImageList> li = new LinkedList<>();
        for(int count=0; count < fields.length; count++){
            if(fields[count].getName().contains("basicbg")){
                int imageResource = getResources().getIdentifier(fields[count].getName(), "drawable", getApplicationContext().getPackageName());
                li.add(new BgImageList(imageResource));
            }
        }
        return li;
    }

}
