package com.estsoft.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estsoft.mysite.dao.GuestBookDao;
import com.estsoft.mysite.vo.GuestBookVo;

@Service
public class GuestbookService {
	@Autowired
	private GuestBookDao dao;

	public Long insert(GuestBookVo vo) {
		return dao.insert2(vo);
	}

	public List<GuestBookVo> list(int page){
		return dao.getList(page);
	}
	
	public GuestBookVo get(Long no){
		return dao.getVo(no);
	}

	public boolean delete(GuestBookVo vo) {
		return dao.delete(vo);
	}
}