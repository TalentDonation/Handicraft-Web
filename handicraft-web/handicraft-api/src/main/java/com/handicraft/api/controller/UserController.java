package com.handicraft.api.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.handicraft.core.dto.User;
import com.handicraft.core.service.UserService;

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
