package com.estsoft.mysite.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.estsoft.mysite.exception.GuestbookGetListException;
import com.estsoft.mysite.vo.GuestBookVo;

@Repository
public class GuestBookDao {	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private SqlSession sqlSession;
	
	public boolean insert(GuestBookVo vo){
		int count = sqlSession.insert("guestbook.insert",vo);
		if(count > 0) return true;
		else return false;
	}
	
	public Long insert2( GuestBookVo vo ) throws GuestbookGetListException{
		int count = sqlSession.insert("guestbook.insert2", vo);
		System.out.println(count + " : " + vo.getNo());
		
		return vo.getNo();
	}
	
	public boolean delete( GuestBookVo vo ) {
		int countDelete = sqlSession.delete("guestbook.delete",vo);
		if(countDelete > 0) return true;
		else return false;
	}
	
	public boolean delete2(Long no, String password){
		Map<String , Object> map = new HashMap<>();
		map.put("no", no);
		map.put("password", password);
		int countDelete = sqlSession.delete("guestbook.delete2",map);
		if(countDelete > 0) return true;
		else return false;
	}
	
	public List<GuestBookVo> getList() {
		List<GuestBookVo> list = sqlSession.selectList("guestbook.selectList"); //.xml에 같은 id의 쿼리가 있으면 namespace를 붙여준다.
		return list;
	}
	
	public List<GuestBookVo> getList(int page) {
		Integer p = (page-1)*5;
		List<GuestBookVo> list = sqlSession.selectList("guestbook.selectList_PAGE", p);
		
		for (GuestBookVo guestBookVo : list) {
			System.out.println(guestBookVo);
		}
			
		return list;
	}
	
	public GuestBookVo getVo(Long no){
		GuestBookVo vo = sqlSession.selectOne("guestbook.selectByNo", no);
		return vo;
	}
}
