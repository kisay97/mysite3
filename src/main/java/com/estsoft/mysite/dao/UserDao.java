package com.estsoft.mysite.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.estsoft.db.DBConnection;
import com.estsoft.db.MySQLWebDBConnection;
import com.estsoft.mysite.vo.UserVo;

@Repository
public class UserDao {
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private SqlSession sqlSession;
	
	//인증(Auth)
	public UserVo get(String email, String password){
		Map<String, Object> map = new HashMap<>();
		map.put("email", email);
		map.put("password", password);
		
		UserVo vo = sqlSession.selectOne("user.get_VALUE", map);
		System.out.println(vo);
		
		return vo;
	}
	
	public UserVo get(UserVo vo){
		UserVo returnVo = sqlSession.selectOne("user.get_VO", vo);
		System.out.println(vo);
		
		return returnVo;
	}
	
	public UserVo get(Long no){
		UserVo vo = sqlSession.selectOne("user.get_NO", no);
		
		return vo;
	}
	
	public UserVo get(String email){
		UserVo vo = sqlSession.selectOne("user.get_EMAIL", email);
		System.out.println(vo);
		
		return vo;
	}
	
	public boolean insert(UserVo vo){
		int count = sqlSession.insert("user.insert", vo);
		if(count>0) return true;
		else return false;
	}
	
	public boolean update(UserVo vo){
		int count = sqlSession.update("user.update", vo);
		if(count>0) return true;
		else return false;
	}
}