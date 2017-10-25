package com.example.bit_user.a5_29_restart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by bit-user on 2017-06-26.
 */

class Canvasmain extends View{

    public static Colinter collistener;
    Paint paint;
    Bitmap m_bitmap;
    int cacolor = Color.RED;
    Canvas m_canvas;



    public Canvasmain(Context context){
        super(context);
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        m_bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        m_canvas = new Canvas(m_bitmap);
        gradset(m_canvas);
        canvas.drawBitmap(m_bitmap,0,0,null);
    }

    protected void setColor(int color){
        this.cacolor = color;
    }

    protected int getColor(){
        return this.cacolor;
    }

    protected void gradset(Canvas ca){
        LinearGradient whitecol = new LinearGradient(1, 1, getWidth() - 1, 1, Color.WHITE, getColor(), Shader.TileMode.CLAMP);
        LinearGradient blackcol = new LinearGradient(1, 1, 1, getHeight() - 1, 0, Color.BLACK, Shader.TileMode.CLAMP);
        paint.setAntiAlias(true);
        paint.setShader(whitecol);
        ca.drawRect(1,1,getWidth(),getHeight(),paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DARKEN));
        paint.setShader(blackcol);
        ca.drawRect(1,1,getWidth(),getHeight(),paint);
        paint.setShader(null);
    }


    public int colorR(int parm_color) {
        return ((parm_color >> 16)&0xFF);
    }
    public int colorG(int parm_color) {
        return ((parm_color >> 8)&0xFF);
    }
    public int colorB(int parm_color) {
        return (parm_color&0xFF);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        Canvasmain.collistener = new Colinter() {
            @Override
            public void mainCol(int color) {
                ColorSel.listener.onColorSelected(color);
            }
        };
        LinearLayout lastcolor=(LinearLayout) ((Activity)getContext()).findViewById(R.id.lastcolor);
        TextView rColor=(TextView) ((Activity)getContext()).findViewById(R.id.Rcolor);
        TextView gColor=(TextView) ((Activity)getContext()).findViewById(R.id.Gcolor);
        TextView bColor=(TextView) ((Activity)getContext()).findViewById(R.id.Bcolor);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                int color = m_bitmap.getPixel((int) event.getX(), (int) event.getY());
                lastcolor.setBackgroundColor(color);
                rColor.setText("R:"+colorR(color));
                gColor.setText("G:"+colorG(color));
                bColor.setText("B:"+colorB(color));
                Canvasmain.collistener.mainCol(color);
                return true;
        }
        return false;
    }


}


class Colorcanvas extends View{


    Paint paint;
    Bitmap m_bitmap;
    int m_draw_y;
    int m_select_x = 1;
    Context m_parent;
    Canvasmain canvasmain = null;

    public Colorcanvas(Context context){
        super(context);
        paint = new Paint();

    }

    public Colorcanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        m_parent = (ColorSel)context;
    }

    public Colorcanvas(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        m_parent = (ColorSel)context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(m_bitmap,0,0,null);
    }



    protected void onSizeChanged (int w, int h, int oldw, int oldh) {
        LinearLayout canvasMain=(LinearLayout) ((Activity)getContext()).findViewById(R.id.canvasmain);
        if(w != oldw) {
            // 색상을 선택했다는 것을 삼각형으로 표시하기위해 뷰 크기의 하단 8Pixel 은 그라데이션이
            // 출력될 크기에서 뺀다.
            h -= 8;
            // 삼각형이 그려질 시작 위치를 저장한다.
            m_draw_y = h;

            // 비트맵을 생성한다.
            m_bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            // Canvas 객체를 생성한다.
            Canvas canvas = new Canvas(m_bitmap);

            // 그라데이션에 적용될 색상 배열과 상대적 위치값들을 정의한다.
            int[] colors = {Color.RED, Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE,
                    Color.MAGENTA, Color.RED};
            float[] pos = {0.0f, 0.17f, 0.34f, 0.51f, 0.68f, 0.85f, 1.0f};

            // Shader 를 적용할 Paint 객체를 생성한다.
            Paint shader_paint = new Paint();
            shader_paint.setAntiAlias(true);

            // 선형으로 그라데이션 Shader 를 생성하여 Paint 객체에 저장한다.
            shader_paint.setShader(new LinearGradient(0, 0, w, 0, colors, pos, Shader.TileMode.CLAMP));
            // Shader 가 적용된 Paint 객체를 이용하여 사각형을 그린다.
            canvas.drawRect(0, 0, w, h, shader_paint);
            // Paint 객체에서 Shader 를 제거한다.
            shader_paint.setShader(null);

            // 그라데이션에서 선택된 위치의 색상을 얻는다.
            int color = m_bitmap.getPixel(m_select_x, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            canvasmain = new Canvasmain(getContext());
            canvasmain.setLayoutParams(params);
            canvasMain.addView(canvasmain);
            // 뷰를 갱신한다.
            invalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                int color= m_bitmap.getPixel((int) event.getX(), (int) event.getY());
                canvasmain.setColor(color);
                canvasmain.invalidate();
                return true;
        }
        return false;
    }

}


public class ColorSel extends AppCompatActivity{

    public static OnColorSelectedListener  listener;

    int color;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.color_sel);
        this.setTitle("색상선택");
        Intent intent=getIntent();
        color= intent.getIntExtra("color",0);
    }




    public void selcol(View v){
        finish();
    }

    public void back(View v){
        finish();
    }

}
