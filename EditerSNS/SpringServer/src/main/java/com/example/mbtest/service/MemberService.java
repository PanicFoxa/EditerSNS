package com.example.mbtest.service;


import org.json.simple.JSONObject;

import com.example.mbtest.model.dto.MemberVO;

public interface MemberService {
	
	public JSONObject mlist(String test);
	public void minsert(MemberVO vo);
	public int muserselect(MemberVO id);
}
