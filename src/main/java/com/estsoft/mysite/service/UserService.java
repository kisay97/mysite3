package com.estsoft.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estsoft.mysite.dao.UserDao;
import com.estsoft.mysite.vo.UserVo;

@Service
public class UserService {
	@Autowired
	private UserDao dao;
	
	public void join(UserVo vo){
		dao.insert(vo);
	}
	
	public UserVo login(UserVo vo){
		UserVo authUser = dao.get(vo);
		return authUser;
	}

	public UserVo getUser(String email) {
		return dao.get(email);
	}

	public void modify(UserVo vo) {
		dao.update(vo);
	}

	public UserVo get(Long no) {
		return dao.get(no);
	}
}