package com.example.mbtest.model.dto;

public class MemberVO {
//	  USER_NUM NUMBER NOT NULL PRIMARY KEY,
//	  USER_ID VARCHAR2(20),
//	  SHARE_ID VARCHAR2(20)

	int user_num;
	String user_id;
	String share_id;
	MemberVO(){
		
	}
	public MemberVO(int user_num, String user_id , String share_id){
		this.user_num = user_num;
		this.user_id = user_id;
		this.share_id = share_id;
	}
	
	public int getUser_num() { return user_num; }
	public void setUser_num(int user_num) { this.user_num = user_num; }
	public String getUser_id() { return user_id; }
	public void setUser_id(String user_id) { this.user_id = user_id; }
	public String getShare_id() { return share_id; }
	public void setShare_id(String share_id) { this.share_id = share_id; }	
}
