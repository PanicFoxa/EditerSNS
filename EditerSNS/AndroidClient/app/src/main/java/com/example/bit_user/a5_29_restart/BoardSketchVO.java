package com.example.bit_user.a5_29_restart;

import android.graphics.Bitmap;

/**
 * Created by bit-user on 2017-07-24.
 */

public class BoardSketchVO {
    int rsread_num;
    Bitmap rs_img;
    float rs_x;
    float rs_y;
    float rs_w;
    float rs_h;

    public BoardSketchVO(int rsread_num, Bitmap rs_img, float rs_x, float rs_y, float rs_w, float rs_h) { this.rsread_num = rsread_num; this.rs_img = rs_img; this.rs_x = rs_x; this.rs_y = rs_y; this.rs_w = rs_w; this.rs_h = rs_h; }
    public int getRsread_num() { return rsread_num; }
    public void setRsread_num(int rsread_num) { this.rsread_num = rsread_num; }
    public Bitmap getRs_img() { return rs_img; }
    public void setRs_img(Bitmap rs_img) { this.rs_img = rs_img; }
    public float getRs_x() { return rs_x; }
    public void setRs_x(float rs_x) { this.rs_x = rs_x; }
    public float getRs_y() { return rs_y; }
    public void setRs_y(float rs_y) { this.rs_y = rs_y; }
    public float getRs_w() { return rs_w; }
    public void setRs_w(float rs_w) { this.rs_w = rs_w; }
    public float getRs_h() { return rs_h; }
    public void setRs_h(float rs_h) { this.rs_h = rs_h; }
}
