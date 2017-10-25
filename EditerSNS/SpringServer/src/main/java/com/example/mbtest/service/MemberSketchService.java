package com.example.mbtest.service;


import org.json.simple.JSONObject;

import com.example.mbtest.model.dto.MemberSketchVO;

public interface MemberSketchService {
	
	public void fileUpload(MemberSketchVO vo);
	public int usersketchselect(String userid);
	public JSONObject sketchselect(String text);
}
