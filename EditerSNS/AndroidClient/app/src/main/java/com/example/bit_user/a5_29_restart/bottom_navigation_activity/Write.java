package com.example.bit_user.a5_29_restart.bottom_navigation_activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.bit_user.a5_29_restart.Bgset;
import com.example.bit_user.a5_29_restart.Common;
import com.example.bit_user.a5_29_restart.Fontdwset;
import com.example.bit_user.a5_29_restart.Fontset;
import com.example.bit_user.a5_29_restart.MainActivity;
import com.example.bit_user.a5_29_restart.Main_Btnev;
import com.example.bit_user.a5_29_restart.Main_togev;
import com.example.bit_user.a5_29_restart.R;
import com.example.bit_user.a5_29_restart.Sketchset;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 이 페이지는 바텀네비 '쓰기' 에 해당하는 클래스입니다
 */

public class Write extends AppCompatActivity
{

    Button X_Button; // 뒤로가기
    Button OK_Button; // 글 작성
    Button text_Button; // 텍스트
    Button bg_Button; // 배경
    Button sketch_Button; // 스케치
    Button basic_bg_Button; // 기본 배경
    Button album_Button; // 앨범 배경
    Button camera_Button; // 카메라 배경
    ImageView mainbg; // 이미지 뷰
    Button newSketch_Button; // 스케치 - 새로만들기
    Button SketchSuccess_Button; // 스케치 - 확인
    Button colorbtn;
    Button penbtn;
    Button backbtn;
    Button backbtn2;
    Button backbtn3;
    Button backbtn4;
    Button backbtn5;
    Button txtfont;
    Button txtloc;
    Button fontsize;
    Button txtgrb;
    EditText edit_view;
    ToggleButton txtcolor;
    ToggleButton txtweg;
    ToggleButton setgase;
    Button sktdbtn;
    Button skcreatebtn;
    LinearLayout main_btn;



    public static final int REQUEST_IMAGE_CAPTURE = 2001;
    public static final int PICK_FROM_ALBUM = 2002;
    public static final int CROP_FROM_CAMERA = 2003;
    public static final int RESULT_CROP_IMAGE = 2004;



    // *** 클릭 이벤트들 *** //
    Main_Btnev main_btnev = new Main_Btnev(this,Write.this);
    Bgset bgsl = new Bgset(this, Write.this);
    Sketchset sketchset = new Sketchset(this, Write.this);
    Fontset fontset = new Fontset(this,Write.this);
    Fontdwset fontdwset = new Fontdwset(this,Write.this);
    Main_togev main_togev = new Main_togev(Write.this);
    LinearLayout txtset;
    LinearLayout txtdwset;
    // *** 클릭 이벤트들 *** //

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bot_nav_write_activity);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE )!= PackageManager.PERMISSION_GRANTED){
                if(shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                    Toast.makeText(Write.this,"외부메모리 읽기 쓰기 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
                }
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            }
        }

        txtset = (LinearLayout)findViewById(R.id.txtset);
        txtdwset = (LinearLayout)findViewById(R.id.txtdwset);

        // ********** 뒤로가기(X), OK 버튼 ************ //
        X_Button = (Button)findViewById(R.id.write_X_button);
        OK_Button = (Button)findViewById(R.id.write_OK_button);

        X_Button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });

        // ********** 뒤로가기(X), OK 버튼 ************ //



        // ********** 텍스트에 관련된 편집 버튼 ******** //
        text_Button = (Button)findViewById(R.id.write_txt_btn);
        text_Button.setOnClickListener(main_btnev);
        // ********** 텍스트에 관련된 편집 버튼 ******** //

        // ********** 배경에 관련된 편집 버튼 ********** //
        bg_Button = (Button)findViewById(R.id.bgbtn);
        bg_Button.setOnClickListener(main_btnev);
        // ********** 배경에 관련된 편집 버튼 ********** //

        // ********** 스케치에 관련된 편집 버튼 ********* //
     /*   sketch_Button = (Button)findViewById(R.id.sketchbtn);
        sketch_Button.setOnClickListener(main_btnev);*/
        // ********** 스케치에 관련된 편집 버튼 ********* //

        // ********** 기본 배경 버튼 *********** //
        basic_bg_Button = (Button)findViewById(R.id.basicbg);
        basic_bg_Button.setOnClickListener(bgsl);
        // ********** 기본 배경 버튼 *********** //

        // ********** 앨범 배경 버튼 *********** //
        album_Button = (Button)findViewById(R.id.albumbg);
        album_Button.setOnClickListener(bgsl);
        // ********** 앨범 배경 버튼 *********** //

        // ********** 카메라 배경 버튼 *********** //

        // ********** 카메라 배경 버튼 *********** //

        // ********** 기본 배경 (이미지 뷰) 버튼 *********** //
        mainbg = (ImageView)findViewById(R.id.mainbg);
        // ********** 기본 배경 (이미지 뷰) 버튼 *********** //

        // ********** 스케치 버튼 - 새로 만들기 버튼 ********** //
        newSketch_Button = (Button)findViewById(R.id.newsketch);
        newSketch_Button.setOnClickListener(sketchset);
        // ********** 스케치 버튼 - 새로 만들기 버튼 ********** //

        // ********** 스케치 버튼 - 만들고 확인 버튼 ********** //
        SketchSuccess_Button = (Button)findViewById(R.id.successbtn);
        SketchSuccess_Button.setOnClickListener(sketchset);
        // ********** 스케치 버튼 - 만들고 확인 버튼 ********** //

        colorbtn = (Button) findViewById(R.id.colorbtn);
        colorbtn.setOnClickListener(sketchset);

        penbtn =(Button)findViewById(R.id.penbtn);
        penbtn.setOnClickListener(sketchset);


        backbtn =(Button)findViewById(R.id.backbtn);
        backbtn.setOnClickListener(sketchset);
        backbtn2=(Button)findViewById(R.id.backbtn2);
        backbtn2.setOnClickListener(sketchset);
        backbtn3 =(Button)findViewById(R.id.backbtn3);
        backbtn3.setOnClickListener(bgsl);
        backbtn4=(Button)findViewById(R.id.backbtn4);
        backbtn4.setOnClickListener(fontset);
        backbtn5 =(Button)findViewById(R.id.backbtn5);
        backbtn5.setOnClickListener(fontdwset);
        sktdbtn = (Button)findViewById(R.id.sktdbtn);
        sktdbtn.setOnClickListener(sketchset);

        txtfont =(Button)findViewById(R.id.txtfont);
        txtfont.setOnClickListener(fontset);
        txtloc =(Button)findViewById(R.id.txtloc);
        txtloc.setOnClickListener(fontset);


        fontsize = (Button)findViewById(R.id.fontsize);
        txtgrb = (Button)findViewById(R.id.txtgrb);
        fontsize.setOnClickListener(fontdwset);
        txtgrb.setOnClickListener(fontdwset);
        edit_view = (EditText) findViewById(R.id.edit_view);
        txtcolor = (ToggleButton)findViewById(R.id.txtcolor);
        txtweg = (ToggleButton)findViewById(R.id.txtweg);
        setgase = (ToggleButton) findViewById(R.id.setgase);
        txtcolor.setOnCheckedChangeListener(main_togev);
        txtweg.setOnCheckedChangeListener(main_togev);
        setgase.setOnCheckedChangeListener(main_togev);
        main_btn = (LinearLayout) findViewById(R.id.main_btn);
        skcreatebtn = (Button)findViewById(R.id.skcreatebtn);
        skcreatebtn.setOnClickListener(sketchset);

    }

    public void writeset(View v){
        writeth.start();
    }

    class Writenet extends AsyncTask<String, Void, Object> {

        private HttpClient client;
        private HttpPost post;
        JSONObject writeob;
        JSONObject readsketchimg;
        JSONObject textset;
        JSONObject filepathob;

        public Writenet(JSONObject writeob, JSONObject readsketchimg, JSONObject textset, JSONObject filepathob) {
            this.writeob = writeob;
            this.readsketchimg = readsketchimg;
            this.textset = textset;
            this.filepathob = filepathob;
        }





        @Override
        protected Void doInBackground(String... arg0) {
            try {

                client = new DefaultHttpClient();
                HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000);
                post = new HttpPost(arg0[0]);
                StringEntity entity = new StringEntity(writeob.toString()+".."+filepathob.toString()+".."+textset.toString()+".."+readsketchimg.toString()+"..", HTTP.UTF_8);
                entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                post.setEntity(entity);
                client.execute(post);
            } catch (Exception err) {
                err.printStackTrace();
            }
            return null;
        }
    }


    Thread writeth = new Thread(new Runnable() {
        String str;
        @TargetApi(Build.VERSION_CODES.M)
        @Override
        public void run() {
            String userid = MainActivity.useremail;
            try {
                JSONObject writeob = new JSONObject();
                JSONArray sketchsett= sketchset.returnsketcharray();
                JSONObject readsketchimg = new JSONObject();
                readsketchimg.put("sketchimgarray",sketchsett);
                JSONObject textset = textsetread();
                String mainbgdrawable = convert(((BitmapDrawable)mainbg.getBackground()).getBitmap());
                writeob.put("write_id",userid);
                JSONArray jsar=  (JSONArray) readsketchimg.get("sketchimgarray");
                File[] filepath = new File[jsar.length()+1];
                String filepaths =null;
                for (int a=0; a<jsar.length(); a++){
                    JSONObject row = jsar.getJSONObject(a);
                    String imgname=  row.getString("imgname");
                    filepath[a] = createimagefile(imgname);
                }
                filepath[filepath.length-1]=createimagefile(mainbgdrawable);

                for(int a = 0; a < filepath.length; a++){
                    filepaths += filepath[a]+",";
                }
                JSONObject filepathob = new JSONObject();
                filepathob.put("filepath",filepaths);

                String page = Common.server_url+"/mbtest/board/boardinsert";
                new Writenet(writeob,readsketchimg,textset,filepathob).execute(page);
                page = Common.server_url+"/mbtest/board/boardupload";
                URL url = new URL(page);
                for(int a = 0; a<filepath.length; a++){
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setUseCaches(false);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Connection","Keep-Alive");
                    conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=files"+a);
                    FileInputStream fis = new FileInputStream(filepath[a].toString());
                    DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
                    dos.writeBytes("--files"+a+"\r\n");
                    dos.writeBytes("Content-Disposition: form-data;name=\"file\";filename=\""+filepath[a]+"\"" + "\r\n");
                    dos.writeBytes("\r\n");
                    int bytes = fis.available();
                    int maxBuffersize = 1024;
                    int bufferSize = Math.min(bytes, maxBuffersize);
                    byte[] buffer = new byte[bufferSize];
                    int read = fis.read(buffer, 0, bufferSize);
                    while (read > 0) {
                        dos.write(buffer, 0, bufferSize);
                        bytes = fis.available();
                        bufferSize = Math.min(bytes, maxBuffersize);
                        read = fis.read(buffer, 0, bufferSize);
                    }
                    dos.writeBytes("\r\n");
                    dos.writeBytes("--files"+a+"--\r\n");
                    fis.close();
                    dos.flush();
                    dos.close();
                    filepath[a].delete();
                    int ch;
                    InputStream is = conn.getInputStream();
                    StringBuffer sb = new StringBuffer();
                    while ((ch = is.read()) != -1) {
                        sb.append((char) ch);
                    }

                    str = sb.toString();
                    if (str.equals("success")) {
                        str = "글 작성이 완료되었습니다.";
                    } else if(str.equals("fail")){
                        str = "글 작성이 실패하였습니다.";
                    }
                    conn.disconnect();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                          resulttxt.setText(str);
//                          basicimg.setImageURI(ur);
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                writeth.interrupt();
                finish();
            }
        }
    });


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


    @RequiresApi(api = Build.VERSION_CODES.M)
    public File createimagefile(String bitmapdrawable){
        if(checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE )!= PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            }
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        Bitmap bm = convert(bitmapdrawable);

        File file = null;
        File storageDir = Environment.getExternalStorageDirectory();
        File tempFile = null;
        String fileName = "bmp" + String.valueOf(System.currentTimeMillis()) + ".JPEG";  // 파일이름은 마음대로!
        if(!storageDir.exists()){
            storageDir.mkdirs();
            tempFile = new File(storageDir,fileName);
        }else{
            tempFile = new File(storageDir,fileName);
        }
        try{
            tempFile.createNewFile();  // 파일을 생성해주고
            FileOutputStream out = new FileOutputStream(tempFile);
            BufferedOutputStream bos = new BufferedOutputStream(out);
            bm.compress(Bitmap.CompressFormat.JPEG, 90 , bos);  // 넘거 받은 bitmap을 jpeg(손실압축)으로 저장해줌
            file = tempFile;
            out.close(); // 마무리로 닫아줍니다.
            bos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }



    public JSONObject textsetread(){
        JSONObject textsetlistresult = new JSONObject();
        try{
            textsetlistresult.put("txt_content",edit_view.getText());
            textsetlistresult.put("txt_color",edit_view.getTextColors());
            textsetlistresult.put("txt_font",edit_view.getTypeface());
            textsetlistresult.put("txt_weight",edit_view.getPaintFlags());
            textsetlistresult.put("txt_size",edit_view.getTextSize());
            textsetlistresult.put("txt_orit",main_togev.gaseset());
            textsetlistresult.put("txt_location",edit_view.getGravity());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                textsetlistresult.put("txt_sort",edit_view.getTextAlignment());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return textsetlistresult;
    }


    public void txtposel(View v){
        switch (v.getId()){
            case R.id.leftTop: edit_view.setGravity(Gravity.LEFT | Gravity.TOP);
                break;
            case R.id.centerTop: edit_view.setGravity(Gravity.CENTER | Gravity.TOP);
                break;
            case R.id.rightTop: edit_view.setGravity(Gravity.RIGHT | Gravity.TOP);
                break;
            case R.id.centerLeft: edit_view.setGravity(Gravity.CENTER | Gravity.LEFT);
                break;
            case R.id.center: edit_view.setGravity(Gravity.CENTER);
                break;
            case R.id.centerRight: edit_view.setGravity(Gravity.CENTER | Gravity.RIGHT);
                break;
            case R.id.bottomLeft: edit_view.setGravity(Gravity.BOTTOM | Gravity.LEFT);
                break;
            case R.id.bottomcenter: edit_view.setGravity(Gravity.BOTTOM | Gravity.CENTER);
                break;
            case R.id.bottomRight: edit_view.setGravity(Gravity.BOTTOM | Gravity.RIGHT);
                break;
        }
        txtdwset.setVisibility(View.GONE);
        txtdwset.startAnimation(AnimationUtils.loadAnimation(Write.this, R.anim.slide_left2));
        txtset.setVisibility(View.VISIBLE);
        txtset.startAnimation(AnimationUtils.loadAnimation(Write.this, R.anim.slide_left));
        fontdwset.alertdig.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        switch(requestCode){
            case REQUEST_IMAGE_CAPTURE:{
                bgsl.cropImage();
            }break;
            case PICK_FROM_ALBUM:{
                if(data!=null){
                    bgsl.cropImage(data.getData());
                }
            }break;
            case CROP_FROM_CAMERA:{
                if(data!=null) {
                    Bitmap cropPhoto = BitmapFactory.decodeFile(bgsl.resultFile.getPath());
                    cropPhoto = bgsl.GetRotatedBitmap(cropPhoto, 90);
                    mainbg.setBackgroundDrawable(new BitmapDrawable(getResources(), cropPhoto));
                }
            }break;
            case RESULT_CROP_IMAGE:{
                if(data!=null) {
                    final Bundle extras = data.getExtras();
                    Bitmap photo = (Bitmap) extras.get("data");
                    mainbg.setBackgroundDrawable(new BitmapDrawable(getResources(), photo));
                }
            }break;
        }
        if(requestCode == 101 && resultCode ==1000){
            int imgnum = data.getIntExtra("imgnum",0);
            mainbg.setBackgroundResource(imgnum);
            main_btn.setVisibility(View.VISIBLE);
        }
    }
}
