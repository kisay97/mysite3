package com.estsoft.mysite.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.estsoft.mysite.service.UserService;
import com.estsoft.mysite.vo.UserVo;

public class AuthLoginInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	UserService userService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		//Web Application Context(IoC Container 가져오기)
		//ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext( request.getServletContext() );
		//Bean
		//UserService userService = applicationContext.getBean( UserService.class );
		
		UserVo vo = new UserVo();
		vo.setEmail(email);
		vo.setPassword(password);
		
		UserVo authUser = userService.login(vo);
		if(authUser==null){
			response.sendRedirect(request.getContextPath() + "/user/loginform_fail");
			return false;
		}
		
		//로그인 처리
		HttpSession session = request.getSession(true);
		session.setAttribute("authUser", authUser);
		response.sendRedirect(request.getContextPath());
		
		return false;
	}
}