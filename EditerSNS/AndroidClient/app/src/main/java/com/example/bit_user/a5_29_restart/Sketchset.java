package com.example.bit_user.a5_29_restart;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by bit-user on 2017-06-09.
 */



public class Sketchset implements View.OnClickListener {
    static Context mMain;
    static Activity act;
    LinearLayout sw = null;
    Sketch sketch;
    DialogInterface alertdig;
    Penselecter penselecter;
    ImageView saveImage;
    SketchClick saveimg;
    String []imglist;
    LinkedList<Downimglist> imli = new LinkedList<>();
    DownimgAdapter adapter;
    GridView bmpgridview;
    JSONArray sketchimgarray = new JSONArray();


    public Sketchset(Context mMain, Activity act){
        this.mMain = mMain;
        this.act = act;
    }


    /* canvas 설정 하위 버튼 이벤트 */
    @Override
    public void onClick(View v) {

        LinearLayout skcanvas= (LinearLayout)((Activity)mMain).findViewById(R.id.skcanvas);
        LinearLayout sketchset = (LinearLayout)((Activity)mMain).findViewById(R.id.sketchset);
        LinearLayout skcontain = (LinearLayout) ((Activity)mMain).findViewById(R.id.skcontain);
        LinearLayout main_btn = (LinearLayout) ((Activity)mMain).findViewById(R.id.main_btn);
        LinearLayout sketchsel = (LinearLayout) ((Activity)mMain).findViewById(R.id.sketchsel);
        LinearLayout skcreate = (LinearLayout) ((Activity)mMain).findViewById(R.id.skcreate);
        LinearLayout canvasll = (LinearLayout) ((Activity)mMain).findViewById(R.id.canvasll);
        LinearLayout bgset = (LinearLayout) ((Activity)mMain).findViewById(R.id.bgset);
        FrameLayout canvasframe = (FrameLayout) (((Activity)mMain).findViewById(R.id.canvasframe));
        TextView skettxt = (TextView) ((Activity)mMain).findViewById(R.id.skettxt);
        Button skcreatebtn = (Button)((Activity)mMain).findViewById(R.id.skcreatebtn);
        Button sktdbtn = (Button)((Activity)mMain).findViewById(R.id.sktdbtn);
        LinearLayout userskli = (LinearLayout) ((Activity)mMain).findViewById(R.id.userskli);


        switch (v.getId()){
            case R.id.newsketch:
                canvasll.removeAllViews();
                userskli.setVisibility(View.GONE);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                sketch = new Sketch(act);
                sketch.setLayoutParams(params);
                sketch.setPadding(2, 2, 2, 2);
                canvasll.addView(sketch);
                sw = skcanvas;
                sktdbtn.setVisibility(View.VISIBLE);
                skcontain.setVisibility(View.VISIBLE);
                main_btn.setVisibility(View.GONE);
                main_btn.startAnimation(AnimationUtils.loadAnimation(act, R.anim.slide_right));
                sketchsel.setVisibility(View.VISIBLE);
                sketchsel.startAnimation(AnimationUtils.loadAnimation(act, R.anim.slide_right2));
                break;
            case R.id.sktdbtn:
                canvasll.removeAllViews();
                userskli.setVisibility(View.VISIBLE);
                skcontain.setVisibility(View.VISIBLE);
                sketchlist();
                break;
            case R.id.successbtn:{
                skettxt.setVisibility(View.VISIBLE);
                skcreate.setVisibility(View.VISIBLE);
                sketchsel.setVisibility(View.GONE);
                skcreatebtn.setVisibility(View.VISIBLE);
                saveImage = sketch.imageSave();
                sketch = null;
                canvasll.removeAllViews();
                saveimg = new SketchClick(mMain,act,saveImage);
                saveImage.setOnLongClickListener(saveimg);
            }break;
            case R.id.backbtn:{
                canvasll.removeAllViews();
                LinearLayout.LayoutParams paramss = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                sketch = new Sketch(act);
                sketch.setLayoutParams(paramss);
                sketch.setPadding(2, 2, 2, 2);
                canvasll.addView(sketch);
            }break;
            case R.id.backbtn2:{
                main_btn.setVisibility(View.VISIBLE);
                main_btn.startAnimation(AnimationUtils.loadAnimation(act, R.anim.slide_left));
                sketchsel.setVisibility(View.GONE);
                sketchsel.startAnimation(AnimationUtils.loadAnimation(act, R.anim.slide_left2));
                canvasll.removeAllViews();
                sktdbtn.setVisibility(View.INVISIBLE);
                skcontain.setVisibility(View.INVISIBLE);
            }break;

            case R.id.skcreatebtn:{
                if(skcreatebtn.getText().toString().equals("저장")){
                    saveimg.saveImg();
                }else{
                    skcreatebtn.setText("저장");
                    try {
                        JSONObject sketchimglist = new JSONObject();
                        sketchimglist.put("imgname",convert(((BitmapDrawable)saveImage.getDrawable()).getBitmap()));
                        sketchimglist.put("imgX",saveimg.getSketchsave().getX());
                        sketchimglist.put("imgY",saveimg.getSketchsave().getY());
                        sketchimglist.put("imgW",saveimg.getSketchsave().getW());
                        sketchimglist.put("imgH",saveimg.getSketchsave().getH());
                        sketchimgarray.put(sketchimglist);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                skcreatebtn.setVisibility(View.GONE);
                sktdbtn.setVisibility(View.INVISIBLE);
                main_btn.setVisibility(View.VISIBLE);
                main_btn.startAnimation(AnimationUtils.loadAnimation(act, R.anim.slide_left));
                skcreate.setVisibility(View.GONE);
                skcontain.setVisibility(View.INVISIBLE);
                skcreate.startAnimation(AnimationUtils.loadAnimation(act, R.anim.slide_left2));
                skettxt.setVisibility(View.GONE);

                canvasframe.removeView(saveImage);
                canvasll.removeAllViews();

            }break;
            case R.id.penbtn:{
                View innerView = act.getLayoutInflater().inflate(R.layout.pen_select, null);
                Button pen = (Button) innerView.findViewById(R.id.pen);
                Button bollpen = (Button) innerView.findViewById(R.id.bollpen);
                Button magic = (Button) innerView.findViewById(R.id.magic);
                Button boot = (Button) innerView.findViewById(R.id.boot);

                AlertDialog.Builder penal = new AlertDialog.Builder(mMain);
                penal.setView(innerView);
                penal
                        .setTitle("Pen 선택")
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                alertdig.dismiss();
                            }
                        });
                alertdig = penal.show();
                penselecter = new Penselecter(mMain,act,alertdig);
                pen.setOnClickListener(penselecter);
                bollpen.setOnClickListener(penselecter);
                magic.setOnClickListener(penselecter);
                boot.setOnClickListener(penselecter);
                Penselecter.listener = new BrushSelected() {
                    public void onBrushsel(float size, String Brush) {
                        sketch.updatebrush(size, Brush);
                    }
                };
            }break;
            case R.id.colorbtn:{
                ColorSel.listener = new OnColorSelectedListener() {
                    public void onColorSelected(int color) {
                        sketch.updatePaintProperty(color);
                    }
                };
                Intent intent = new Intent(act, ColorSel.class);
                intent.putExtra("color",sketch.paint.getColor());
                act.startActivity(intent);
            }break;
        }

        if(sw.getVisibility() == View.GONE){
            skcanvas.setVisibility(View.GONE);
            sw.setVisibility(View.VISIBLE);
        }
        sketchset.setVisibility(View.GONE);
    }


    public JSONArray returnsketcharray(){
        return sketchimgarray;
    }

    public void sketchlist(){
        Thread th = new Thread(new Runnable() {
            public void run() {
                String filename;
                bmpgridview = (GridView) ((Activity)mMain).findViewById(R.id.bmpgridview);
                try {
                    String page = Common.server_url + "/mbtest/sketch/sketchselect";
                    HttpClient http = new DefaultHttpClient();
                    // post 방식으로 보낼 데이터
                    ArrayList<NameValuePair> postData = new ArrayList<NameValuePair>();
                    postData.add(new BasicNameValuePair("user_id", MainActivity.useremail));
                    UrlEncodedFormEntity request = new UrlEncodedFormEntity(postData, "utf-8");
                    HttpPost httpPost = new HttpPost(page);
                    httpPost.setEntity(request);
                    // app/libs에 httpcore-4.2.2.jar 추가
                    HttpResponse response = http.execute(httpPost);
                    String body = EntityUtils.toString(response.getEntity());
                    JSONObject jsonObj = new JSONObject(body);
                    JSONArray jArray = (JSONArray) jsonObj.get("sketchlist");
                    StringBuffer sb = new StringBuffer();
                    imglist = new String[jArray.length()];
                    for (int a = 0; a<jArray.length(); a++) {
                        JSONObject row = jArray.getJSONObject(a);
                        filename = row.getString("sk_img");
                        sb.append(filename);
                        imglist[a] = filename;
                    }
                    LinkedList<Downimglist> lists = downimg();
                    adapter = new DownimgAdapter(act,R.layout.sketchlist,lists);
                    act.runOnUiThread(new Runnable() {
                        public void run() {
                            bmpgridview.setAdapter(adapter);
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        th.start();
    }

    public static Bitmap convert(String base64Str) throws IllegalArgumentException {
        byte[] decodedBytes = Base64.decode(
                base64Str.substring(base64Str.indexOf(",")  + 1),
                Base64.DEFAULT
        );
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public static String convert(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }


    class DownimgAdapter extends ArrayAdapter<Downimglist> { // 수정확인    <안에 타입 변경>
        Context context;
        int resourceId;
        public DownimgAdapter(Context context, int resourceId, List<Downimglist> li) { // 수정확인  <안에 타입 변경>
            super(context, resourceId, li);
            this.context = context;
            this.resourceId = resourceId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(resourceId, null);

            // 코드수정부분 시작
            ((ImageView) convertView.findViewById(R.id.bmpmg)).setImageBitmap(getItem(position).getFilebmp());  //Image추가 단
            // 코드수정부분 끝
            return convertView;
        }

    }


    public LinkedList<Downimglist> downimg(){
        try {
            InputStream is = null;
            HttpURLConnection conn = null;
            for(int a = 0; a < imglist.length; a++){
                String page = Common.server_url+"/mbtest/resources/images/"+imglist[a];
                URL url = new URL(page);
                conn = (HttpURLConnection)url.openConnection();
                conn.connect();
                is = conn.getInputStream();
                Bitmap bm= BitmapFactory.decodeStream(is);
                imli.add(new Downimglist(a,bm));
                conn.disconnect();
                is.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imli;
    }



    class Downimglist{
        int filenum;
        Bitmap filebmp;
        public Downimglist(int filenum, Bitmap filebmp) { this.filenum = filenum; this.filebmp = filebmp; }
        public int getFilenum() { return filenum; }
        public void setFilenum(int filenum) { this.filenum = filenum; }
        public Bitmap getFilebmp() { return filebmp; }
        public void setFilebmp(Bitmap filebmp) { this.filebmp = filebmp; }
    }

}
