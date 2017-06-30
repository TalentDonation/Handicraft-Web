package com.handicraft.admin.controller;

import com.handicraft.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.net.URLEncoder;

@Controller
public class MainController {

	@Autowired
	UserService userService;

	@RequestMapping("/")
	public ModelAndView getMain()
	{

		ModelAndView mv = new ModelAndView();
		mv.setViewName("main");
		mv.addObject("user",userService.getUser());
		return mv;
	}
}
