package com.example.mbtest.service;


import java.util.List;

import javax.inject.Inject;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import com.example.mbtest.model.dao.MemberDAO;
import com.example.mbtest.model.dto.MemberVO;

@Service
public class MemberServiceImpl implements MemberService {

	@Inject
	MemberDAO memberdao;
	
	@SuppressWarnings("unchecked")
	public JSONObject mlist(String test){
		List<MemberVO> mlist = memberdao.mlist();
		String tests= test.replaceAll("%40", "@");
		tests = tests.replaceAll("user_id=", "");
		JSONArray jsondata = new JSONArray();
		JSONObject result = new JSONObject();
		for(int a = 0; a < mlist.size(); a++){
			JSONObject js = new JSONObject();
			String id = mlist.get(a).getUser_id();
			if(id.equals(tests)) {
				js.put("user_id", id);
				jsondata.add(0,js);
			}
		}
		result.put("checklist", jsondata);
		return result;
	}
	
	public void minsert(MemberVO vo) {
		memberdao.minsert(vo);
	}

	@Override
	public int muserselect(MemberVO id) {
		int user_num = memberdao.muserselect(id);
		return user_num;
	}

	
}
