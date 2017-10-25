package com.example.mbtest.model.dao;


import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.example.mbtest.model.dto.MemberSketchVO;
import com.example.mbtest.model.dto.MemberVO;



@Repository
public class MemberSketchImpl implements MemberSketchDAO {
	
	@Inject
	SqlSession sqlSession;

	@Override
	public void fileUpload(MemberSketchVO vo) {
		sqlSession.insert("membersketch.sketchInsert", vo);
	}

	@Override
	public List<MemberVO> usersketchselect(MemberVO vo) {
		List<MemberVO> mlist = sqlSession.selectList("membersketch.usersketchselect",vo);
		return mlist;
	}

	@Override
	public List<MemberSketchVO> userfilelist(MemberSketchVO vo) {
		List<MemberSketchVO> mlist = sqlSession.selectList("membersketch.userfilelist",vo);
		return mlist;
	}
	
	
	
}
