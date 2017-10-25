package com.example.bit_user.a5_29_restart;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;
import java.io.IOException;

import static com.example.bit_user.a5_29_restart.bottom_navigation_activity.Write.CROP_FROM_CAMERA;
import static com.example.bit_user.a5_29_restart.bottom_navigation_activity.Write.PICK_FROM_ALBUM;
import static com.example.bit_user.a5_29_restart.bottom_navigation_activity.Write.REQUEST_IMAGE_CAPTURE;
import static com.example.bit_user.a5_29_restart.bottom_navigation_activity.Write.RESULT_CROP_IMAGE;


/**
 * Created by bit-user on 2017-06-09.
 */

public class Bgset implements View.OnClickListener {


    static Context mMain;
    static Activity act;
    File file;
    public Uri resultFile;



    public Bgset(Context mMain, Activity act){
        this.mMain = mMain;
        this.act = act;
    }


    /* 배경 설정 하위 버튼 이벤트 */
    @Override
    public void onClick(View v) {
        LinearLayout bgset = (LinearLayout) ((Activity)mMain).findViewById(R.id.bgset);
        LinearLayout txtset = (LinearLayout) ((Activity) mMain).findViewById(R.id.txtset);
        LinearLayout txtdwset = (LinearLayout) ((Activity) mMain).findViewById(R.id.txtdwset);
        LinearLayout sketchset = (LinearLayout) ((Activity) mMain).findViewById(R.id.sketchset);
        LinearLayout skcontain = (LinearLayout) ((Activity)mMain).findViewById(R.id.skcontain);
        LinearLayout main_btn = (LinearLayout)((Activity)mMain).findViewById(R.id.main_btn);
        LinearLayout sketchsel = (LinearLayout)((Activity)mMain).findViewById(R.id.sketchsel);


        switch (v.getId()){
            case R.id.basicbg:{
                Intent intent = new Intent(act,BasicbgSelect.class);
                act.startActivityForResult(intent, 101);
                act.overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
            }break;
            case R.id.albumbg:{
                getAlbum();
            }break;

            case R.id.backbtn3:{
                bgset.setVisibility(View.GONE);
                main_btn.setVisibility(View.VISIBLE);
                main_btn.startAnimation(AnimationUtils.loadAnimation(act, R.anim.slide_left));
                sketchsel.setVisibility(View.GONE);
                bgset.startAnimation(AnimationUtils.loadAnimation(act, R.anim.slide_left2));
            }break;
        }
        bgset.setVisibility(View.GONE);
    }



    /* 기기 카메라 존재 여부 체크 */
    public boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            return true;
        } else {
            return false;
        }
    }


    /* 카메라 촬영 함수 */
    public void captureCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file= null;
        try{
            file = createImageFile();
        }catch (IOException e){
            e.printStackTrace();
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file);
        act.startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    /* 앨범 사진 가져오기 */
    public void getAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        file= null;
        try{
            file = createImageFile();
        }catch (IOException e){
            e.printStackTrace();
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        act.startActivityForResult(intent, PICK_FROM_ALBUM);
    }


    /* 이미지 파일 생성 */
    public File createImageFile()throws IOException {
        String imageFileName ="tmp_"+ String.valueOf(System.currentTimeMillis()) + ".jpg";
        File storageDir = Environment.getExternalStorageDirectory();
        File curFile = new File(storageDir, imageFileName);
        resultFile = Uri.fromFile(curFile);
        return curFile;
    }




    /* 이미지 자르기(촬영 crop) */
    public void cropImage(){
        ImageView mainbg = (ImageView) ((Activity)mMain).findViewById(R.id.mainbg);
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        cropIntent.setDataAndType(resultFile,"image/*");
        cropIntent.putExtra("crop","true");
        cropIntent.putExtra("outputX",500);
        cropIntent.putExtra("outputY",500);
        cropIntent.putExtra("aspectX",mainbg.getWidth());
        cropIntent.putExtra("aspectY",mainbg.getHeight());
        cropIntent.putExtra("scale",true);
        cropIntent.putExtra("return-data", true);
        act.startActivityForResult(cropIntent, CROP_FROM_CAMERA);
    }

    /* 이미지 자르기(앨범 crop) */
    public void cropImage(Uri uri){
        ImageView mainbg = (ImageView) ((Activity)mMain).findViewById(R.id.mainbg);
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        cropIntent.setDataAndType(uri,"image/*");
        cropIntent.putExtra("crop","true");
        cropIntent.putExtra("outputX",500);
        cropIntent.putExtra("outputY",500);
        cropIntent.putExtra("aspectX",mainbg.getWidth());
        cropIntent.putExtra("aspectY",mainbg.getHeight());
        cropIntent.putExtra("scale",true);
        cropIntent.putExtra("return-data", true);
        act.startActivityForResult(cropIntent, RESULT_CROP_IMAGE);
    }


    /* 비트맵 이미지 회전 및 사이즈 조절 */
    public Bitmap GetRotatedBitmap(Bitmap bitmap, int degrees) {
        if (degrees != 0 && bitmap != null) {
            Matrix m = new Matrix();
            m.setRotate(degrees, (float) bitmap.getWidth()/2, (float) bitmap.getHeight()/2);
            try {
                Bitmap b2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
                if (bitmap != b2) {
                    bitmap.recycle();
                    bitmap = b2;
                }
            } catch (OutOfMemoryError e) {
                // 메모리 부족에러시, 원본을 반환
            }
        }
        return bitmap;
    }

}
