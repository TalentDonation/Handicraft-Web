package com.handicraft.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ConfigController {

	@RequestMapping("/config")
	public String getMain()
	{
		return "config";
	}
	
}
