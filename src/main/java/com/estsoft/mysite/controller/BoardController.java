package com.estsoft.mysite.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.estsoft.mysite.annotation.Auth;
import com.estsoft.mysite.service.BoardService;
import com.estsoft.mysite.vo.BoardVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	@RequestMapping(value={""})
	public ModelAndView index(@RequestParam( value="page", required=true, defaultValue="1" )  Integer page, @RequestParam( value="kwd", required=true, defaultValue="" )  String keyword){
		List<BoardVo> list = boardService.getList(page, keyword);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("thisRange", boardService.getThisRange(page, keyword));
		mav.addObject("startPageNo", boardService.getStartPageNo(page,keyword));
		mav.addObject("endPageNo", boardService.getEndPageNo(page,keyword));
		mav.addObject("page", page);
		mav.addObject("pageCount", boardService.getPageCount(page, keyword));
		mav.addObject("list", list);
		mav.addObject("nameList", boardService.getNameList(page,keyword));
		mav.addObject("lastPageBoardNum", boardService.getLastPageBoardNum(page,keyword));
		mav.setViewName("/board/list2");
		
		return mav;
	}
	
	@Auth
	@RequestMapping(value="writeform")
	public String writeform(HttpSession session){
		if (session.getAttribute("authUser") != null) {			
			return "/board/write";
		} else{
			return "/main/index";
		}
	}
	
	@Auth
	@RequestMapping(value="view")
	public ModelAndView view(@RequestParam(value="no", required=true, defaultValue="-1") Long no){
		boardService.hitUpdate(no);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("vo", boardService.getVo(no));
		mav.setViewName("/board/view");
		
		return mav;
	}
	
	@Auth
	@RequestMapping(value="write")
	public String write(
			@RequestParam(value="title", required=true, defaultValue="(제목없음)") String title,
			@RequestParam(value="content", required=true, defaultValue="(내용없음)") String content,
			@RequestParam(value="user_no", required=true, defaultValue="-1") Long authUserNo,
			@RequestParam(value="mom_no", required=true, defaultValue="-1") Long momNo){
		BoardVo vo = new BoardVo();
		vo.setTitle(title);
		vo.setContent(content);
		vo.setUser_no(authUserNo);
		
		boardService.write(vo, momNo);
		
		return "redirect:/board/view?no="+boardService.getMaxBoardNo();
	}
	
	@Auth
	@RequestMapping(value="delete")
	public String delete(@RequestParam(value="no", required=true, defaultValue="-1") Long no){
		boardService.delete(no);
		return "redirect:/board";
	}
	
	@Auth
	@RequestMapping(value="modifyform")
	public ModelAndView modifyform(@RequestParam(value="no", required=true, defaultValue="-1") Long no){
		ModelAndView mav = new ModelAndView();
		mav.addObject("vo", boardService.getVo(no));
		mav.setViewName("/board/modify");
		
		return mav;
	}
	
	@Auth
	@RequestMapping(value="modify")
	public String modify(@ModelAttribute BoardVo vo){
		boardService.modify(vo);
		return "redirect:/board/view?no="+vo.getNo();
	}
}
