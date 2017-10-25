package com.example.mbtest.service;

import org.json.simple.JSONObject;

import com.example.mbtest.model.dto.BoardVO;

public interface BoardService {

	public void boardUpload(BoardVO vo);
	public void mboardinsert(BoardVO vo);
	public JSONObject boardlist();
}
