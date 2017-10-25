package com.example.mbtest.controller;



import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.mbtest.model.dto.BoardSketchVO;
import com.example.mbtest.model.dto.BoardVO;
import com.example.mbtest.model.dto.MemberVO;
import com.example.mbtest.service.BoardService;
import com.example.mbtest.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;



@Controller
@RequestMapping("board")
public class BoardController {
		//context 설정 bean 디렉토리 값 가져오기
		String pathSet;
	
		
	
		@Inject
		BoardService mService;
		@Inject
		MemberService mnService;
		

		@RequestMapping(value = "boardupload", method=RequestMethod.POST, consumes = "multipart/form-data")
		public ModelAndView boardUpload(MultipartFile file,HttpServletRequest request, ModelAndView mav, @CookieValue(value = "resultwrite1", defaultValue = "0") String resultwrite1){
			String filename="";
			pathSet = request.getSession().getServletContext().getRealPath("/resources/boardimages/");
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
			mav.setViewName("board/boardupload");
			return mav;
		}
		
		@RequestMapping(value="boardinsert", headers="Accept=application/json", method=RequestMethod.POST)
		public void boardinsert(@RequestBody String textjson, BoardVO boardvo, MemberVO mvo)throws Exception {
			String textslice;
			JSONObject[] slicetext = new JSONObject[4];
			
			for(int a = 0; a<slicetext.length;a++) {
				int startj= textjson.indexOf("{");
				int endj = textjson.indexOf("..");
				textslice = textjson.substring(startj, endj);
				textjson = textjson.replace(textslice,"");
				textjson = textjson.replaceFirst("..", "");
				JSONParser parser = new JSONParser();
				Object obj= parser.parse(textslice);
				JSONObject jsobj = (JSONObject) obj;
				slicetext[a]=jsobj;
			}					
			Map<String,Object> map =  new ObjectMapper().readValue(slicetext[0].toJSONString(), Map.class) ;
			Map<String,Object> map1 =  new ObjectMapper().readValue(slicetext[1].toJSONString(), Map.class) ;
			Map<String,Object> map2=  new ObjectMapper().readValue(slicetext[2].toJSONString(), Map.class) ;
			JSONArray sketchimgarray= (JSONArray)slicetext[3].get("sketchimgarray");
			String write_id = map.get("write_id").toString();
			String filepath = map1.get("filepath").toString();
			ArrayList<String> filepaths = new ArrayList<String>();			
			String filename=null;
			while(true) {
				int bmp= filepath.indexOf("bmp");
				if(bmp==-1) { break; }
				int comma = filepath.indexOf(",");
				filename = filepath.substring(bmp, comma);
				filepath = filepath.replace(filename,"");
				filepath = filepath.replaceFirst(",", "");
				filepaths.add(filename);
				
			}			

			mvo.setUser_id(write_id);
			int user_num= mnService.muserselect(mvo);
			boardvo.setWrite_user(user_num);
			boardvo.setRead_lat(Float.parseFloat(map.get("read_lat").toString()));
			boardvo.setRead_lont(Float.parseFloat(map.get("read_lont").toString()));
		
			boardvo.setTxt_color(map2.get("txt_color").toString());
			boardvo.setTxt_content(map2.get("txt_content").toString());
			boardvo.setTxt_font(map2.get("txt_font").toString());
			boardvo.setTxt_weight(map2.get("txt_weight").toString());
			boardvo.setTxt_size(Float.parseFloat(map2.get("txt_size").toString()));
			boardvo.setTxt_orit(Integer.parseInt(map2.get("txt_orit").toString()));
			boardvo.setTxt_location(map2.get("txt_location").toString());
			boardvo.setTxt_sort(map2.get("txt_sort").toString());
		
			int ct=0;
			Iterator<JSONObject> skit= sketchimgarray.iterator();
			ArrayList<BoardSketchVO> absvo = new ArrayList<BoardSketchVO>();
			
			while(skit.hasNext()) {
				BoardSketchVO sd = new BoardSketchVO();
				JSONObject row = skit.next();
				sd.setRs_img(filepaths.get(ct));
				sd.setRs_x(Float.parseFloat(row.get("imgX").toString()));
				sd.setRs_y(Float.parseFloat(row.get("imgY").toString()));
				sd.setRs_w(Integer.parseInt(row.get("imgW").toString()));
				sd.setRs_h(Integer.parseInt(row.get("imgH").toString()));
				absvo.add(sd);
				ct++;			
			}
			boardvo.setBoard_sketch(absvo);
			boardvo.setBg_img(filepaths.get(ct));
			mService.mboardinsert(boardvo);	
			
		}
		
		
		
		@RequestMapping("boardlist")
		public @ResponseBody JSONObject boardlist(){
			JSONObject result= mService.boardlist();
			return result;
		}
		
	
}
