package com.estsoft.mysite.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.estsoft.mysite.service.GuestbookService;
import com.estsoft.mysite.vo.GuestBookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestbookController {
	@Autowired
	private GuestbookService guestbookService;
	
	@RequestMapping(value={"","/index"})
	public String index(){
		return "/guestbook/ajax-main";
	}
	
	@RequestMapping("/insert")
	@ResponseBody
	public Map insert(@ModelAttribute GuestBookVo vo){
		Long no = guestbookService.insert(vo);
		GuestBookVo insertedVo = guestbookService.get(no);
		
		Map<String, Object> map = new HashMap<>();
		map.put("result", "success");
		map.put("data", insertedVo);
		
		return map;
	}
	
	@RequestMapping("/ajax-list")
	@ResponseBody
	public Map ajaxList(@RequestParam(value = "p", required=true, defaultValue="1") int page){
		List<GuestBookVo> list = guestbookService.list(page);
		
		Map<String, Object> map = new HashMap<>();
		map.put("result", "success");
		map.put("data", list);
		
		return map;
	}
	
	@RequestMapping("/ajax-delete")
	@ResponseBody
	public Map ajaxDelete(@ModelAttribute GuestBookVo vo){
		boolean isDelete = guestbookService.delete(vo);
		
		Map<String, Object> map = new HashMap<>();
		map.put("result", "successs");
		map.put("data", isDelete);
		
		return map;
	}
}
