package com.handicraft.api.controller;



import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.handicraft.core.dto.User;
import com.handicraft.core.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	UserService userSv;
	
	@RequestMapping(value ="/user" , method = RequestMethod.GET)
	@ResponseBody
	public User getUser()
	{


		return userSv.getUser();
	}

	@RequestMapping(value ="/users" , method = RequestMethod.GET)
	@ResponseBody
	public User getUsers()
	{


		return userSv.getUser();
	}

}
