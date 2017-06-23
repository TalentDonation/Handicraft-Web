package com.handicraft.api.controller;

import com.handicraft.api.dto.User;
import com.handicraft.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class UserController {
	
	@Autowired
	UserService userSv;
	
	@RequestMapping("/")
	@ResponseBody
	public User getUser()
	{
		return userSv.getUser();
	}

}
