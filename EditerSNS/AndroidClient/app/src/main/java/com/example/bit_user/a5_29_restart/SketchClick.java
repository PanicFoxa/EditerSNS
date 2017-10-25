package com.example.bit_user.a5_29_restart;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Environment;
import android.util.Base64;
import android.view.DragEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

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
 * Created by bit-user on 2017-07-07.
 */


public class SketchClick implements View.OnLongClickListener {
    Context mMain;
    Activity act;
    ImageView saveimg;
    FrameLayout canvasframe;
    FrameLayout mainFrame;
    RelativeLayout rel1;
    Button skcreatebtn;
    SketchSave sketchsave;
    File file = null;



    SketchClick(Context mMain, Activity act, ImageView saveimg) {
        this.mMain = mMain;
        this.act = act;
        this.saveimg = saveimg;
    }

    public SketchSave getSketchsave() {
        return sketchsave;
    }

    public void setSketchsave(SketchSave sketchsave) {
        this.sketchsave = sketchsave;
    }

    @Override
    public boolean onLongClick(View v) {
        canvasframe = (FrameLayout) ((Activity) mMain).findViewById(R.id.canvasframe);
        skcreatebtn = (Button) ((Activity) mMain).findViewById(R.id.skcreatebtn);
        mainFrame = (FrameLayout) ((Activity) mMain).findViewById(R.id.mainframe);
        rel1 = (RelativeLayout)((Activity)mMain).findViewById(R.id.rel1);

        SketchSave sketchsave = new SketchSave(mMain,act,saveimg);
        this.sketchsave= sketchsave;
        canvasframe.setOnDragListener(sketchsave);
        rel1.setOnDragListener(sketchsave);

        ClipData.Item item = new ClipData.Item((CharSequence) v.getTag());
        String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };
        ClipData data = new ClipData(v.getTag()+"", mimeTypes, item);
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            v.startDragAndDrop(data,shadowBuilder,v,0);
        } else {
            v.startDrag(data,shadowBuilder,v,0);
        }
        return true;
    }

    public void saveImg() {
        saveimgth.start();
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


    Thread saveimgth = new Thread(new Runnable() {
        @Override
        public void run() {
            try{
                    Bitmap bm = ((BitmapDrawable)saveimg.getDrawable()).getBitmap();
                    File storageDir = Environment.getExternalStorageDirectory();
                    File tempFile = null;
                    String fileName = "bmp" + String.valueOf(System.currentTimeMillis()) + ".JPEG";  // 파일이름은 마음대로!
                    if (!storageDir.exists()) {
                        storageDir.mkdirs();
                        tempFile = new File(storageDir, fileName);
                    } else {
                        tempFile = new File(storageDir, fileName);
                    }
                    try {
                        tempFile.createNewFile();  // 파일을 생성해주고
                        FileOutputStream out = new FileOutputStream(tempFile);
                        BufferedOutputStream bos = new BufferedOutputStream(out);
                        bm.compress(Bitmap.CompressFormat.JPEG, 90, bos);  // 넘거 받은 bitmap을 jpeg(손실압축)으로 저장해줌
                        file = tempFile;
                        out.close(); // 마무리로 닫아줍니다.
                        bos.close();
                        saveimgth2.start();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    });



    Thread saveimgth2 = new Thread(new Runnable() {
        String str;
        @Override
        public void run() {
                String page = Common.server_url+"/mbtest/sketch/sketchupload";
                try {
                    String userid = MainActivity.useremail;
                    FileInputStream fis = new FileInputStream(file.toString());

                    URL url = new URL(page);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setUseCaches(false);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Cookie","userid=\""+userid+"\"");
                    conn.setRequestProperty("Connection","Keep-Alive");
                    conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=files");
                    DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
                    dos.writeBytes("--files\r\n");
                    dos.writeBytes("Content-Disposition: form-data;name=\"file\";filename=\""+file+"\"" + "\r\n");
                    dos.writeBytes("\r\n");
                    int bytes = fis.available();
                    int maxBuffersize = 1024;
                    int bufferSize = Math.min(bytes, maxBuffersize);
                    byte[] buffer = new byte[bufferSize];
                    int read = fis.read(buffer, 0, bufferSize);

                    while (read > 0) {
                        //서버 업로드
                        dos.write(buffer, 0, bufferSize);
                        bytes = fis.available();
                        bufferSize = Math.min(bytes, maxBuffersize);
                        //읽은 바이트수
                        read = fis.read(buffer, 0, bufferSize);
                    }

                    dos.writeBytes("\r\n");
                    dos.writeBytes("--files--\r\n");

                    fis.close();
                    dos.flush();
                    dos.close();

                    file.delete();

                    int ch;
                    InputStream is = conn.getInputStream();
                    StringBuffer sb = new StringBuffer();
                    while ((ch = is.read()) != -1) {
                        sb.append((char) ch);
                    }

                    str = sb.toString();

                    if (str.equals("success")) {
                        str = "스케치 업로드가 완료되었습니다.";
                    } else if(str.equals("fail")){
                        str = "스케치 업로드가 실패하였습니다.";
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

        }
    });


    public class SketchSave implements View.OnDragListener {
        Context mMain;
        Activity act;
        ImageView saveimg;
        Float x;
        Float y;
        int w;
        int h;

        SketchSave(Context mMain, Activity act,ImageView saveimg) {
            this.mMain = mMain;
            this.act = act;
            this.saveimg = saveimg;
        }

        public ImageView getSaveimg() {
            return saveimg;
        }

        public void setSaveimg(ImageView saveimg) {
            this.saveimg = saveimg;
        }

        public Float getX() {
            return x;
        }

        public void setX(Float x) {
            this.x = x;
        }

        public Float getY() {
            return y;
        }

        public void setY(Float y) {
            this.y = y;
        }

        public int getW() {
            return w;
        }

        public void setW(int w) {
            this.w = w;
        }

        public int getH() {
            return h;
        }

        public void setH(int h) {
            this.h = h;
        }

        @Override
        public boolean onDrag(View v, DragEvent event) {
            float imgx = 0f;
            float imgy = 0f;
            int ad= 1;
            switch (event.getAction()) {
                //  드래그 시작
                case DragEvent.ACTION_DRAG_STARTED: {
                    imgx = event.getX();
                    imgy = event.getY();
                }return true;
                //  드래그 이미지가 해당 영역에 들어올때
                case DragEvent.ACTION_DRAG_ENTERED: {
                    imgx = event.getX();
                    imgy = event.getY();
                }return true;
                //  드래그 이미지가 해당 영역에서 나갈때
                case DragEvent.ACTION_DRAG_EXITED: {
                    imgx = event.getX();
                    imgy = event.getY();
                }return true;
                //  드래그 다운
                case DragEvent.ACTION_DROP: {
                    imgx = event.getX();
                    imgy = event.getY();
                    if(imgy <= rel1.getHeight()){
                        canvasframe.removeView(saveimg);
                        saveimg.setX(imgx-saveimg.getWidth()/2);
                        saveimg.setY(imgy-saveimg.getHeight()/2);
                        setX(imgx-saveimg.getWidth()/2);
                        setY(imgy-saveimg.getHeight()/2);
                        setW(saveimg.getHeight());
                        setH(saveimg.getHeight());
                        rel1.addView(saveimg);
                        rel1.requestLayout();
                        setSaveimg(saveimg);
                    }
                    skcreatebtn.setText("확인");
                }return true;
            }
            return false;
        }




    }
}


