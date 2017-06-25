package com.handicraft.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CalenderController {
	
	@RequestMapping("/calender")
	public String getCalender()
	{
		return "calender";
	}

}
