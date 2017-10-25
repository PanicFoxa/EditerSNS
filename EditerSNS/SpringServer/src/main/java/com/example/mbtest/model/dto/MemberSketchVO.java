package com.example.mbtest.model.dto;


public class MemberSketchVO {
	
	int sk_num;
	int skuser_num;
	String sk_img;
	
	MemberSketchVO(){} 
	public MemberSketchVO(int sk_num, int skuser_num, String sk_img) { super(); this.sk_num = sk_num; this.skuser_num = skuser_num; this.sk_img = sk_img; }
	public int getSk_num() { return sk_num; }
	public void setSk_num(int sk_num) { this.sk_num = sk_num; }
	public int getSkuser_num() { return skuser_num; }
	public void setSkuser_num(int skuser_num) { this.skuser_num = skuser_num; }
	public String getSk_img() { return sk_img; }
	public void setSk_img(String sk_img) { this.sk_img = sk_img; }
	
}
