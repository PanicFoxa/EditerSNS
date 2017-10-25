package com.example.bit_user.a5_29_restart;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.Stack;

/**
 * Created by bit-user on 2017-06-15.
 */

public class Sketch extends View{
    final Paint paint;
    private final Path path = new Path();
    Stack bmpst = new Stack();
    float lastX;
    float lastY;
    float movedX;
    float movedY;
    int devicew;
    int deviceh;
    FrameLayout canvasframe;


    public static int maxbmpar = 10;
    private int basicBorder = 10;
    static final float TOUCH_TOLERANCE = 8;
    private static final boolean DITHER_FLAG = true;
    private static final boolean RENDERING_ANTIALIAS = true;
    Bitmap bmp;
    Canvas mCanvas;

    public Sketch(Context context) {
        super(context);
        devicew = (((Activity)context).findViewById(R.id.canvasll)).getWidth();
        deviceh = (((Activity)context).findViewById(R.id.canvasll)).getHeight();
        canvasframe = (FrameLayout) (((Activity)context).findViewById(R.id.canvasframe));
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
//        DashPathEffect dpe = new DashPathEffect(new float[]{10f,10f},1);  // new float[]{파선의 길이, 파선사이의 간격}
//        paint.setPathEffect(dpe);
//        EmbossMaskFilter emboss = new EmbossMaskFilter(new float[] {10, 10, 10}, 0.2f, 6, 5);
//        paint.setMaskFilter(emboss);
        paint.setStrokeMiter(100f);
        paint.setStrokeWidth(10f);
        paint.setAntiAlias(RENDERING_ANTIALIAS);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setDither(DITHER_FLAG);
        lastX = -1;
        lastY = -1;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(bmp!=null){
            canvas.drawBitmap(bmp,0,0,null);
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                Rect rect = touchUp(event, false);
                if (rect != null) {
                    invalidate(rect);
                }
                path.rewind();
                return true;
            case MotionEvent.ACTION_DOWN:
                saveUndo();
                rect = touchDown(event);
                if (rect != null) {
                    invalidate(rect);
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                rect = touchMove(event);
                if (rect != null) {
                    invalidate(rect);
                }
                return true;
        }
        return false;
    }


    private Rect touchDown(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        lastX = x;
        lastY = y;
        Rect mInvalidRect = new Rect();
        path.moveTo(x, y);
        final int border = basicBorder;
        mInvalidRect.set((int) x - border, (int) y - border, (int) x + border, (int) y + border);
        movedX = x;
        movedY = y;
        mCanvas.drawPath(path, paint);
        return mInvalidRect;
    }

    private Rect touchMove(MotionEvent event) {
        Rect rect = processMove(event);
        return rect;
    }

    private Rect touchUp(MotionEvent event, boolean cancel) {
        Rect rect = processMove(event);
        return rect;
    }


    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (w > 0 && h > 0) {
            newImage(w, h);
        }else{
            newImage(devicew,deviceh);
        }
    }

    public void newImage(int width, int height) {

        bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas =  new Canvas(bmp);
        drawBackground(mCanvas);
        invalidate();
    }

/*
    public void setImageSize(int width, int height, Bitmap newImage) {

        if (bmp != null){
            if (width < bmp.getWidth()) width = bmp.getWidth();
            if (height < bmp.getHeight()) height = bmp.getHeight();
        }

        if (width < 1 || height < 1) return;
        Bitmap img = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas();
        drawBackground(canvas);

        if (newImage != null) {
            canvas.setBitmap(newImage);
        }

        if (bmp != null) {
            bmp.recycle();
        }

        bmp = img;
        mCanvas = canvas;
        clearUndo();
    }
*/

    public void undo() {
        Bitmap prev = null;
        try {
            while(!bmpst.empty()){
                prev = (Bitmap)bmpst.pop();
                if (prev != null){
                    drawBackground(mCanvas);
                    invalidate();
                    prev.recycle();
                }
            }
        } catch(Exception ex) {
            Log.v("undo Exception",ex.getMessage());
        }


    }


    public void clearUndo() {
        while(true) {
            Bitmap prev = (Bitmap)bmpst.pop();
            if (prev == null) return;
            prev.recycle();
        }
    }


    public void drawBackground(Canvas canvas) {
        if (canvas != null) {
//            canvas.drawColor(Color.WHITE);
        }
    }

    public void saveUndo() {
        if (bmp == null) return;
        while (bmpst.size() >= maxbmpar){
            Bitmap i = (Bitmap) bmpst.get(bmpst.size()-1);
            i.recycle();
            bmpst.remove(i);
        }
        Bitmap img = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(img);
        canvas.drawBitmap(bmp, 0, 0, paint);
        bmpst.push(img);
    }




    private Rect processMove(MotionEvent event) {

        final float x = event.getX();
        final float y = event.getY();

        final float dx = Math.abs(x - lastX);
        final float dy = Math.abs(y - lastY);

        Rect mInvalidRect = new Rect();
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {

            final int border = basicBorder;
            mInvalidRect.set((int) movedX - border, (int) movedY - border,
                    (int) movedX + border, (int) movedY + border);

            float cX = movedX = (x + lastX) / 2;
            float cY = movedY = (y + lastY) / 2;

            path.quadTo(lastX, lastY, cX, cY);

            mInvalidRect.union((int) lastX - border, (int) lastY - border,
                    (int) lastX + border, (int) lastY + border);

            mInvalidRect.union((int) cX - border, (int) cY - border,
                    (int) cX + border, (int) cY + border);
            lastX = x;
            lastY = y;

            mCanvas.drawPath(path, paint);
        }
        return mInvalidRect;
    }

    public void updatePaintProperty(int color) {
        paint.setColor(color);
    }


    public void updatebrush(float size, String type){
        paint.setStrokeWidth(size);
        paint.setMaskFilter(null);
        paint.setAntiAlias(false);

        switch(type){
            case "연필":{
            }break;
            case "볼펜":{
            }break;
            case "매직":{
                paint.setStrokeCap(Paint.Cap.BUTT);
                paint.setAntiAlias(true);
            }break;
            case "붓": {
                paint.setAntiAlias(true);
                paint.setMaskFilter(new BlurMaskFilter(size/2-2,BlurMaskFilter.Blur.NORMAL));
                paint.setStrokeCap(Paint.Cap.SQUARE);
                paint.setStrokeJoin(Paint.Join.BEVEL);
            }break;
        }
    }


    public ImageView imageSave(){
        ImageView saveImage = new ImageView(getContext());
        saveImage.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        saveImage.setImageResource(R.color.transparent);
        saveImage.setImageBitmap(bmp);
        canvasframe.addView(saveImage);
        return saveImage;
    }


}
