package com.estsoft.mysite.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.estsoft.mysite.vo.BoardVo;

@Repository
public class BoardDao {
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private SqlSession sqlSession;
	
	private final int CONTENT_IN_PAGE = 5;
	
	public List<BoardVo> getList(){
		List<BoardVo> list = sqlSession.selectList("board.getList");
		return list;
	}

	public List<BoardVo> getList(String kwd, int page) {
		Map<String, Object> map = new HashMap<>();
		map.put("kwd", "%"+kwd+"%");
		map.put("startNo", (page-1)*CONTENT_IN_PAGE);
		map.put("num", CONTENT_IN_PAGE);
		
		List<BoardVo> list = sqlSession.selectList("board.getList_VALUE", map);
		return list;
	}
	
	public List<BoardVo> getList(String kwd) {
		List<BoardVo> list = sqlSession.selectList("board.getList_KEYWORD", kwd);
		return list;
	}
	
	public List<BoardVo> getList(int page){
		Map<String, Object> map = new HashMap<>();
		map.put("startNo", (page-1)*CONTENT_IN_PAGE);
		map.put("num", CONTENT_IN_PAGE);
		
		List<BoardVo> list = sqlSession.selectList("board.getList_PAGE", map);
		return list;
	}
	
	public int getPageCount(){
		int pageCount = 0;
		pageCount = sqlSession.selectOne("getPageCount");
		
		return pageCount;
	}
	
	public int getPageCount(String kwd){
		int pageCount = 0;
		pageCount = sqlSession.selectOne("board.getPageCount_KEYWORD", "%"+kwd+"%");
		
		return pageCount;
	}
	
	public int getPageCount(String kwd, int page){
		int pageCount = 0;
		
		Map<String, Object> map = new HashMap<>();
		map.put("kwd", "%"+kwd+"%");
		map.put("startNo", (page-1)*CONTENT_IN_PAGE);
		map.put("num", CONTENT_IN_PAGE);
		
		pageCount = sqlSession.selectOne("board.getPageCount_VALUE", map);
		return pageCount;
	}
	
	//아예 새로 글쓰기
	public void insert(BoardVo vo){
		sqlSession.insert("board.insert", vo);
	}
	
	//답글 쓰기
	public void insertAnswer(BoardVo vo, Long parentNo){
		BoardVo parentBoardVo = getBoardVo(parentNo);
		parentBoardVo.setOrder_no(parentBoardVo.getOrder_no()+1);
		parentBoardVo.setDepth(parentBoardVo.getDepth()+1);
		System.out.println(vo);
		System.out.println(parentBoardVo);
		
		Map<String, Object> map = new HashMap<>();
		map.put("vo", vo);
		map.put("parentVo", parentBoardVo);
		
		sqlSession.insert("board.insertAnswer", map);
		answerAfter(getMaxBoardNo());
	}
	
	public void answerAfter(Long answerBoardNo){
		BoardVo answerBoardVo = getBoardVo(answerBoardNo);
		
		sqlSession.update("answerAfter", answerBoardVo);
	}
	
	public BoardVo getBoardVo(Long no){
		return sqlSession.selectOne("board.getVo_NO", no);
	}
	
	public Long getMaxBoardNo(){
		return sqlSession.selectOne("board.getMaxBoardNo");
	}
	
	public void modify(BoardVo vo){
		sqlSession.update("board.modify", vo);
	}
	
	public void hitUpdate(Long no){
		sqlSession.update("board.hitUpdate", no);
	}
	
	public void delete(Long no){
		sqlSession.delete("board.delete", no);
	}
	
	public String getName(Long no){
		return sqlSession.selectOne("board.getName", no);
	}
}