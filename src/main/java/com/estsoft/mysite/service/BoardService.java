package com.estsoft.mysite.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estsoft.mysite.dao.BoardDao;
import com.estsoft.mysite.vo.BoardVo;

@Service
public class BoardService {
	@Autowired
	private BoardDao dao;
	private final int RANGE = 5;

	public List<BoardVo> getList(Integer page, String keyword) {
		return dao.getList(keyword, page);
	}

	public int getThisRange(Integer page, String keyword){
		return (page/RANGE) + ( (page%RANGE > 0) ? 1 : 0 );
	}

	public Integer getStartPageNo(Integer page, String keyword) {
		return (getThisRange(page,keyword) - 1) * RANGE + 1;
	}

	public Integer getEndPageNo(Integer page, String keyword) {
		Integer endPageNo = getThisRange(page,keyword) * RANGE;
		if(endPageNo > getPageCount(page,keyword)) endPageNo = getPageCount(page,keyword);
		return endPageNo;
	}

	public Integer getPageCount(Integer page, String keyword) {
		return dao.getPageCount(keyword);
	}

	public List<String> getNameList(Integer page, String keyword) {
		List<String> nameList = new ArrayList<>();
		
		for(BoardVo boardVo : dao.getList(keyword, page)){
			nameList.add(dao.getName( boardVo.getUser_no() ));
		}
		
		return nameList;
	}

	public Integer getLastPageBoardNum(Integer page, String keyword) {
		Integer lastPageBoardNum = dao.getList(keyword).size()%5;
		if(lastPageBoardNum.intValue() == 0) lastPageBoardNum = 5;
		return lastPageBoardNum;
	}
	
	public BoardVo getVo(Long no){
		BoardVo vo = null;
		vo = dao.getBoardVo(no);
		return vo;
	}

	public void hitUpdate(Long no) {
		dao.hitUpdate(no);
	}

	public void write(BoardVo vo, Long momNo) {
		//새글쓰기
		if(momNo == -1){
			dao.insert(vo);
		}
		//답글쓰기
		else{
			dao.insertAnswer(vo, momNo);
		}
	}

	public Long getMaxBoardNo() {
		return dao.getMaxBoardNo();
	}

	public void delete(Long no) {
		dao.delete(no);
	}

	public void modify(BoardVo vo) {
		dao.modify(vo);
	}
}
