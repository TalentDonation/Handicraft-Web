package com.handicraft.api.controller;


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


	@GetMapping(value="/{uid}")
	@ApiOperation(value = "" , notes = " {u_id}에 대한 한명의 유저 정보")
	public ResponseEntity<?> getByUser(@PathVariable("uid") int uidParam )
	{
		User user = userService.getByUser(uidParam);

		if(user == null)
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<User>(user , HttpStatus.OK);

	}

	@GetMapping(value="/all")
	@ApiOperation(value = "" , notes = "모든 유저 정보")
	public ResponseEntity<?> getByUserAll()
	{
		return new ResponseEntity<List<User>>(userService.getByUserAll() , HttpStatus.OK);
	}

	@PostMapping(value = "/insert")
	@ApiOperation(value = "" , notes = "유저 생성")
	public ResponseEntity<?> insertToUser(@ModelAttribute User userParams)
	{
		return new ResponseEntity<User>(userService.insertToUser(userParams) , HttpStatus.CREATED);
	}

	@PostMapping(value = "/update")
	@ApiOperation(value = "" , notes = "유저 수정")
	public ResponseEntity<?> updateToUser(@ModelAttribute User userParams)
	{
		User user = userService.updateToUser(userParams);

		if(user == null)	return new ResponseEntity(HttpStatus.BAD_REQUEST);

		return new ResponseEntity<User>(user , HttpStatus.CREATED);
	}

}
