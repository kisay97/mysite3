package com.estsoft.mysite.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	
	private static final Log LOG = LogFactory.getLog(MainController.class);
	
	@RequestMapping(value={"/main",""})
	public String index(){
		LOG.debug("/ex1");
		return "/main/index";
	}
}