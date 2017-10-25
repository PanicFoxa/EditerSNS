package com.example.mbtest.model.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.example.mbtest.model.dto.MemberVO;


@Repository
public class MemberImpl implements MemberDAO {
	
	@Inject
	SqlSession sqlSession;
	
	public List<MemberVO> mlist(){
		List<MemberVO> mlist = sqlSession.selectList("member.memberlist");
		return mlist;
	}

	

	public void minsert(MemberVO vo) {
		sqlSession.insert("member.memberInsert", vo);	
	}
	
	@Override
	public int muserselect(MemberVO id) {
		MemberVO user_num= sqlSession.selectOne("member.muserselect", id);
		return user_num.getUser_num();
	}

}
