package com.example.mbtest.model.dao;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.example.mbtest.model.dto.BoardSketchVO;
import com.example.mbtest.model.dto.BoardVO;



@Repository
public class Boardimpl implements BoardDAO {
	
	@Inject
	SqlSession sqlSession;

	@Override
	public void mboardinsert(BoardVO vo) {
		sqlSession.insert("board.boardinsert",vo);
		int rownum= sqlSession.selectOne("board.boardrownum");
		vo.setRead_num(rownum);
		sqlSession.insert("board.boardbginsert",vo);
		sqlSession.insert("board.boardtxtinsert",vo);
		ArrayList<BoardSketchVO> boardSketch= vo.getBoard_sketch();
		Iterator<BoardSketchVO> bsit= boardSketch.iterator();
		while(bsit.hasNext()) {
			BoardSketchVO bsv= bsit.next();
			bsv.setRsread_num(rownum);
			sqlSession.insert("board.boardskeinsert",bsv);
		}
	}

	@Override
	public List<BoardVO> boardlist() {
		List<BoardSketchVO> boardskList = sqlSession.selectList("board.boardskselect");
		Iterator<BoardSketchVO> boardskli = boardskList.iterator();
		List<BoardVO> boardList= sqlSession.selectList("board.boardselect");

			ArrayList<BoardSketchVO> boardArray = new ArrayList<BoardSketchVO>();
			int beforeboardnum=boardList.get(0).getRead_num();
			BoardSketchVO result =null;
			BoardVO result2=null;
			
			while(boardskli.hasNext()) {
				result = boardskli.next();
				if(beforeboardnum == result.getRsread_num()) {
					if(result != null) {
						boardArray.add(result);
					}
				}else{
					Iterator<BoardVO> boardli = boardList.iterator();
					while(boardli.hasNext()) {
						result2 = boardli.next();
						if(result2.getRead_num() == beforeboardnum) {
							result2.setBoard_sketch(boardArray);
						}
					}
					beforeboardnum = result.getRsread_num();
					boardArray = new ArrayList<BoardSketchVO>();
					boardArray.add(result);
				};
			}
		
			result2.setBoard_sketch(boardArray);	
		
		return boardList;
	}
	
	

}
