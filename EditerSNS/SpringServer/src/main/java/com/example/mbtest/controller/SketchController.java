package com.example.mbtest.controller;



import java.io.File;
import java.util.Set;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.mbtest.model.dto.MemberSketchVO;
import com.example.mbtest.service.MemberSketchService;



@Controller
@RequestMapping("sketch")
public class SketchController {
		//context 설정 bean 디렉토리 값 가져오기
		String pathSet;
	
		
	
		@Inject
		MemberSketchService mService;
		

		@RequestMapping(value = "sketchupload", method=RequestMethod.POST, consumes = "multipart/form-data")
		public ModelAndView fileUpload(MultipartFile file,HttpServletRequest request, ModelAndView mav, @CookieValue(value = "userid", defaultValue = "0") String userid){
			String filename="";
			pathSet = request.getSession().getServletContext().getRealPath("/resources/images/");
			if(file != null && !file.isEmpty()) {
				filename = file.getOriginalFilename();
				try {
					//디렉토리 생성
					new File(pathSet).mkdir();
					//지정된 경로로 저장
					file.transferTo(new File(pathSet+filename));
					mav.addObject("message","success");
				}catch(Exception e) {
					mav.addObject("message","fail");
				}
			}
			mav.setViewName("sketch/sketchupload");
			int usernum = mService.usersketchselect(userid);
			MemberSketchVO vo = new MemberSketchVO(0,usernum,filename);
			mService.fileUpload(vo);
			return mav;
		}		
		
		
		
		@RequestMapping("sketchselect")
		public @ResponseBody JSONObject sketchselect(@RequestBody String test){
			JSONObject result = mService.sketchselect(test);
			return result;
		}
		
	
}
