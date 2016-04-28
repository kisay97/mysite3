package com.estsoft.mysite.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.estsoft.mysite.dao.UserDao;
import com.estsoft.mysite.service.UserService;
import com.estsoft.mysite.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/joinform")
	public String joinform(){
		return "/user/joinform";
	}
	
	@RequestMapping("/join")
	public String join(@ModelAttribute UserVo vo){
		userService.join(vo);
		return "redirect:/user/joinsuccess";
	}
	
	@RequestMapping("/joinsuccess")
	public String joinSuccess(){
		return "/user/joinsuccess";
	}
	
	@RequestMapping("/loginform")
	public String loginform(){
		return "user/loginform";
	}
	
//	@RequestMapping("/login")
//	public String login(@ModelAttribute UserVo vo, HttpSession session){
//		UserVo userVo = userService.login(vo);
//		if (userVo == null) { //로그인 실패
//			return "/user/loginform_fail";
//		}
//
//		session.setAttribute("authUser", userVo);
//		return "redirect:/main";
//	}
	
//	@RequestMapping("/logout")
//	public String logout(HttpSession session){
//		//인증유무 체크
//		UserVo authUser = (UserVo) session.getAttribute("authUser");
//		
//		if(authUser != null){
//			session.removeAttribute("authUser");
//			session.invalidate();
//		}
//		
//		return "redirect:/main";
//	}
	
	@RequestMapping("/checkemail")
	@ResponseBody
	public Object checkEmail(@RequestParam(value = "email", required=true, defaultValue="") String email){
		UserVo vo = userService.getUser(email);
		
		Map<String, Object> map = new HashMap<>();
		map.put("result", "success");
		map.put("data", vo==null);
		
		return map;
	}
	
	@RequestMapping("/hello")
	@ResponseBody
	public String hello(){
		return "안녕 안녕 안녕";
	}
	
	@RequestMapping("/modifyform")
	public ModelAndView modifyform(HttpSession session){
		ModelAndView mav = new ModelAndView();
		
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		UserVo userVo = userService.get(authUser.getNo());
		
		mav.addObject("UserVo", userVo);
		mav.setViewName("/user/modifyform");
		
		return mav;
	}
	
	@RequestMapping("/modify")
	public String modify(@ModelAttribute UserVo vo, HttpSession session){
		System.out.println("modify.vo : " + vo);
		userService.modify(vo);
		session.setAttribute("authUser", vo);
		return "redirect:/user/modifyform";
	}
}