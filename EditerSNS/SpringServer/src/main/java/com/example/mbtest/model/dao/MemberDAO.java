package com.example.mbtest.model.dao;

import java.util.List;

import com.example.mbtest.model.dto.MemberVO;

public interface MemberDAO {
	public List<MemberVO> mlist();
	public void minsert(MemberVO vo);
	public int muserselect(MemberVO id);
}
