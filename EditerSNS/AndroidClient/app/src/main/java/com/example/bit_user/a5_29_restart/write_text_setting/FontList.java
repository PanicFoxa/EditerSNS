package com.example.bit_user.a5_29_restart.write_text_setting;

/**
 * Created by bit-user on 2017-06-02.
 */

public class FontList{
    String fontname;
    String path;

    public FontList(String fontname, String path) {
        int namecount = fontname.indexOf(".");
        String realname=fontname.substring(0,namecount);
        this.fontname = realname;
        this.path = path;
    }
    public String getFontname() { return fontname; }
    public void setFontname(String fontname) {this.fontname = fontname; }
    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }

}
