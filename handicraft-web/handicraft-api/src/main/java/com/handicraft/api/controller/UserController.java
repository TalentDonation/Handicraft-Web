package com.handicraft.api.controller;


import com.handicraft.api.exception.BadRequestException;
import com.handicraft.api.exception.NotFoundException;
import com.handicraft.api.util.ResponseStatus;
import com.handicraft.core.dto.User;
import com.handicraft.core.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Api(value = "/user" , description = "USER API")
public class UserController {

	@Autowired
	UserService userService;


	@GetMapping
	@ApiOperation(value = "" , notes = "모든 유저 정보")
	public List<User> getByUserAll()
	{
		return	userService.getByUserAll() ;
	}

	@PostMapping
	@ApiOperation(value = "" , notes = "유저 생성")
	public ResponseEntity insertToUser(@ModelAttribute User userParams)
	{
		userService.insertToUser(userParams);

		return new ResponseEntity(HttpStatus.CREATED);
	}

	@PutMapping
	@ApiOperation(value = "" , notes = "유저 수정")
	public ResponseEntity updateToUser(@ModelAttribute User userParams)
	{
		User user = userService.updateToUser(userParams);

		if(user == null)	throw new NotFoundException();

		return new ResponseEntity(HttpStatus.OK);
	}

	@DeleteMapping
	@ApiOperation(value = "" , notes = "유저 삭제")
	public ResponseEntity deleteToUser(@RequestParam("uid") int uid)
	{
		Boolean resultByDelete = userService.deleteToUser(uid);

		if(!resultByDelete)	throw new NotFoundException();

		return new ResponseEntity(HttpStatus.OK);
	}


	@GetMapping(value="/{uid}")
	@ApiOperation(value = "" , notes = " {u_id}에 대한 한명의 유저 정보")
	public User getByUser(@PathVariable("uid") int uid)
	{
		User user = userService.getByUser(uid);

		if(user == null)	throw new NotFoundException();

		return user;
	}


}
