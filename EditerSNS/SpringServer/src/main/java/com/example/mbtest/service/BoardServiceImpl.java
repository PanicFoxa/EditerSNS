package com.example.mbtest.service;




import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import com.example.mbtest.model.dao.BoardDAO;
import com.example.mbtest.model.dto.BoardVO;

@Service
public class BoardServiceImpl implements BoardService {

	
	@Inject
	BoardDAO Boarddao;

	@Override
	public void boardUpload(BoardVO vo) {
		
	}

	@Override
	public void mboardinsert(BoardVO vo) {		
		Boarddao.mboardinsert(vo);
	}

	@Override
	public JSONObject boardlist() {
		List<BoardVO> boardlist= Boarddao.boardlist();
		Iterator<BoardVO> bl= boardlist.iterator();
		JSONObject resultob = new JSONObject();
		JSONArray resultar = new JSONArray();
		while(bl.hasNext()) {
			BoardVO result = bl.next();
			JSONObject haob = new JSONObject();
			haob.put("bg_img", result.getBg_img());
			haob.put("read_lat", result.getRead_lat());
			haob.put("read_lont", result.getRead_lont());
			haob.put("read_num", result.getRead_num());
			haob.put("share_whether", result.getShare_whether());
			haob.put("txt_color", result.getTxt_color());
			haob.put("txt_content", result.getTxt_content());
			haob.put("txt_font", result.getTxt_font());
			haob.put("txt_location", result.getTxt_location());
			haob.put("txt_orit", result.getTxt_orit());
			haob.put("txt_size", result.getTxt_size());
			haob.put("txt_sort", result.getTxt_sort());
			haob.put("txt_weight", result.getTxt_weight());
			haob.put("write_user", result.getWrite_user());
			haob.put("sketch_array", result.getBoard_sketch());
			resultar.add(haob);
			
		}
		resultob.put("boardlist", resultar);
		return resultob;
	}
	
	
}
