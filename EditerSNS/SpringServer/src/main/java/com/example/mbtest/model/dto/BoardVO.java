package com.example.mbtest.model.dto;

import java.util.ArrayList;

public class BoardVO {
	
	int read_num;
	int write_user;
	float read_lat;
	float read_lont;
	int share_whether;
	String bg_img;
	String txt_content;
	String txt_color;
	String txt_font;
	String txt_weight;
	Float txt_size;
	int txt_orit;
	String txt_location;
	String txt_sort;
	ArrayList<BoardSketchVO> board_sketch;
	
	public int getRead_num() { return read_num; }
	public void setRead_num(int read_num) { this.read_num = read_num; }
	public int getWrite_user() { return write_user; }
	public void setWrite_user(int write_user) { this.write_user = write_user; }
	public float getRead_lat() { return read_lat; }
	public void setRead_lat(float read_lat) { this.read_lat = read_lat; }
	public float getRead_lont() { return read_lont; }
	public void setRead_lont(float read_lont) { this.read_lont = read_lont; }
	public int getShare_whether() { return share_whether; }
	public void setShare_whether(int share_whether) { this.share_whether = share_whether; }
	public String getBg_img() { return bg_img; }
	public void setBg_img(String bg_img) { this.bg_img = bg_img; }
	public String getTxt_content() { return txt_content; }
	public void setTxt_content(String txt_content) { this.txt_content = txt_content; }
	public String getTxt_color() { return txt_color; }
	public void setTxt_color(String txt_color) { this.txt_color = txt_color; }
	public String getTxt_font() { return txt_font; }
	public void setTxt_font(String txt_font) { this.txt_font = txt_font; }
	public String getTxt_weight() { return txt_weight; }
	public void setTxt_weight(String txt_weight) { this.txt_weight = txt_weight; }
	public Float getTxt_size() { return txt_size; }
	public void setTxt_size(Float txt_size) { this.txt_size = txt_size; }
	public int getTxt_orit() { return txt_orit; }
	public void setTxt_orit(int txt_orit) { this.txt_orit = txt_orit; }
	public String getTxt_location() { return txt_location; }
	public void setTxt_location(String txt_location) { this.txt_location = txt_location; }
	public String getTxt_sort() { return txt_sort; }
	public void setTxt_sort(String txt_sort) { this.txt_sort = txt_sort; }
	public ArrayList<BoardSketchVO> getBoard_sketch() { return board_sketch; }
	public void setBoard_sketch(ArrayList<BoardSketchVO> board_sketch) { this.board_sketch = board_sketch; }
	
	

}
