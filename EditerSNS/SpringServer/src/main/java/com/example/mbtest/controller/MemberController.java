package com.example.mbtest.controller;


import javax.inject.Inject;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.mbtest.model.dto.MemberVO;
import com.example.mbtest.service.MemberService;


@Controller
@RequestMapping("member")
public class MemberController {

	@Inject
	MemberService mService;
	
	@RequestMapping("logincheck")
	public @ResponseBody JSONObject mlist(@RequestBody String test){
		JSONObject result = mService.mlist(test);
		return result;
	}
	
	
	@RequestMapping(value="insert", headers="Accept=application/json", produces= {MediaType.APPLICATION_JSON_VALUE}, method=RequestMethod.POST)
	public void insert(@RequestBody String testTxt,MemberVO vo)throws Exception {
		JSONParser parser = new JSONParser();
		Object obj= parser.parse(testTxt);
		JSONObject userobj = (JSONObject) obj;
		String user_id = (String) userobj.get("user_id");
		vo.setUser_id(user_id);
		mService.minsert(vo);
	}
	

	
}
