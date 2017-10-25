package com.example.mbtest.model.dao;

import java.util.List;

import com.example.mbtest.model.dto.BoardVO;

public interface BoardDAO {
	public void mboardinsert(BoardVO vo);
	public List<BoardVO> boardlist();
}
