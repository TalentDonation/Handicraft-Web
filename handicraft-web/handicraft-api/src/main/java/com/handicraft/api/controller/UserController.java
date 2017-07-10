package com.handicraft.api.controller;


import com.handicraft.api.util.ResponseStatus;
import com.handicraft.core.dto.User;
import com.handicraft.core.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Api(value = "/user" , description = "USER API")
public class UserController {
	
	@Autowired
	UserService userService;

	// SELETE

	@GetMapping(value="/{u_id}")
	@org.springframework.web.bind.annotation.ResponseStatus(value = HttpStatus.OK)
	@ApiOperation(value = "" , notes = " {u_id}에 대한 한명의 유저 정보")
	public Object getByUser(@PathVariable("u_id") int u_id )
	{
		return new ResponseStatus().responseSuccess(userService.getByUser(u_id));
	}

	@GetMapping(value="/all")
	@ApiOperation(value = "" , notes = "모든 유저 정보")
	public Object getByUserAll()
	{
		return userService.getByUserAll();
	}

	@PostMapping(value = "/insert")
	@ApiOperation(value = "" , notes = "유저 생성")
	public User insertToUser(@ModelAttribute User user)
	{
		return userService.insertToUser(user);
	}



}
