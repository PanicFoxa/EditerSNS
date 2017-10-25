package com.example.mbtest.model.dao;

import java.util.List;

import com.example.mbtest.model.dto.MemberSketchVO;
import com.example.mbtest.model.dto.MemberVO;

public interface MemberSketchDAO {
	public void fileUpload(MemberSketchVO vo);
	public List<MemberVO> usersketchselect(MemberVO vo);
	public List<MemberSketchVO> userfilelist(MemberSketchVO vo);
}
