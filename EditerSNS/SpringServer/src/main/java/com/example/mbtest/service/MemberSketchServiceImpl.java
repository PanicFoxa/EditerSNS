package com.example.mbtest.service;


import java.util.List;

import javax.inject.Inject;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import com.example.mbtest.model.dao.MemberSketchDAO;
import com.example.mbtest.model.dto.MemberSketchVO;
import com.example.mbtest.model.dto.MemberVO;




@Service
public class MemberSketchServiceImpl implements MemberSketchService {

	@Inject
	MemberSketchDAO membersketchdao;
	
	public void fileUpload(MemberSketchVO vo){
		membersketchdao.fileUpload(vo);
	}

	@Override
	public int usersketchselect(String userid) {
		MemberVO vo = new MemberVO(0, userid, "");
		List<MemberVO> mlist= membersketchdao.usersketchselect(vo);
		int usernum= mlist.get(0).getUser_num();
		return usernum;
	}

	@Override
	public JSONObject sketchselect(String text) {
		String texts= text.replaceAll("%40", "@");
		texts = texts.replaceAll("user_id=", "");
		List<MemberVO> mlist = membersketchdao.usersketchselect(new MemberVO(0,texts,""));
		int usernum= mlist.get(0).getUser_num();
		List<MemberSketchVO> mlist_2 = membersketchdao.userfilelist(new MemberSketchVO(0,usernum,""));
		
		JSONArray jsondata = new JSONArray();
		JSONObject result = new JSONObject();
		for(int a = 0; a < mlist_2.size(); a++){
			JSONObject js = new JSONObject();
			String sk_img = mlist_2.get(a).getSk_img();
			js.put("sk_img", sk_img);
			jsondata.add(0,js);
			
		}
		result.put("sketchlist", jsondata);
		return result;
	}
	
	
	

}
