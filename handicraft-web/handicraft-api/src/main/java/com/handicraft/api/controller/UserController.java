package com.handicraft.api.controller;


import com.handicraft.api.exception.NotFoundException;
import com.handicraft.core.dto.User;
import com.handicraft.core.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(value = "user" , description = "USER API")
public class UserController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	UserService userService;


	@GetMapping("/users")
	@ApiOperation(value = "" , notes = "Show users")
	public List<User> findByUsers()
	{
		return	userService.getByUserAll() ;
	}

	@PostMapping("/users")
	@ApiOperation(value = "" , notes = "Create one user")
	public ResponseEntity insertToUser(@ModelAttribute User userParams)
	{
		userService.insertToUser(userParams);

		return new ResponseEntity(HttpStatus.CREATED);
	}

	@PutMapping("/users")
	@ApiOperation(value = "" , notes = "Update users")
	public ResponseEntity updateToUsers(@ModelAttribute User userParams)
	{
//		User user = userService.updateToUser(userParams);
//
//		if(user == null)	throw new NotFoundException();
//
//		return new ResponseEntity(HttpStatus.OK);
		return  null;
	}

	@DeleteMapping("/users")
	@ApiOperation(value = "" , notes = "Delete users")
	public ResponseEntity deleteToUsers(@RequestParam("uid") int uid)
	{
//		Boolean resultByDelete = userService.deleteToUser(uid);
//
//		if(!resultByDelete)	throw new NotFoundException();
//
//		return new ResponseEntity(HttpStatus.OK);
		return null;
	}


	@GetMapping("/users/{uid}")
	@ApiOperation(value = "" , notes = "Show one user about user id")
	public User findUserById(@PathVariable("uid") int uid)
	{
		User user = userService.getByUser(uid);

		logger.info("Loading {}" , user);

		if(user == null)	throw new NotFoundException();

		return user;
	}

	@PutMapping("/users/{uid}")
	@ApiOperation(value="" , notes = "Update one user about user id")
	public ResponseEntity updateToUserById(@ModelAttribute User userParams)
	{
		User user = userService.updateToUser(userParams);

		if(user == null)	throw new NotFoundException();

		return new ResponseEntity(HttpStatus.OK);
	}

	@DeleteMapping("/users/{uid}")
	@ApiOperation(value = "" , notes = "Delete one user about user id")
	public ResponseEntity deleteToUserById(@RequestParam("uid") int uid)
	{
		Boolean resultByDelete = userService.deleteToUser(uid);

		if(!resultByDelete)	throw new NotFoundException();

		return new ResponseEntity(HttpStatus.OK);
	}

	/*
	* user - market
	* */

	@GetMapping("/users/{uid}/markets")
	@ApiOperation(value = "" , notes = "Show one user about user id")
	public User findUserAndMarketById(@PathVariable("uid") int uid)
	{
		User user = userService.getByUser(uid);

		logger.info("Loading {}" , user);

		if(user == null)	throw new NotFoundException();

		return user;
	}

	@PutMapping("/users/{uid}/markets")
	@ApiOperation(value="" , notes = "Update one user about user id")
	public ResponseEntity updateToUserAndMarketById(@ModelAttribute User userParams)
	{
		User user = userService.updateToUser(userParams);

		if(user == null)	throw new NotFoundException();

		return new ResponseEntity(HttpStatus.OK);
	}

	@DeleteMapping("/users/{uid}/markets")
	@ApiOperation(value = "" , notes = "Delete one user about user id")
	public ResponseEntity deleteToUserAndMarketById(@RequestParam("uid") int uid)
	{
		Boolean resultByDelete = userService.deleteToUser(uid);

		if(!resultByDelete)	throw new NotFoundException();

		return new ResponseEntity(HttpStatus.OK);
	}

	/*
	* user - market mid
	* */

	@GetMapping("/users/{uid}/markets/{mid}")
	@ApiOperation(value = "" , notes = "Show one user about user id")
	public User findUserAndMarketByIdAndMid(@PathVariable("uid") int uid)
	{
		User user = userService.getByUser(uid);

		logger.info("Loading {}" , user);

		if(user == null)	throw new NotFoundException();

		return user;
	}

	@PutMapping("/users/{uid}/markets/{mid}")
	@ApiOperation(value="" , notes = "Update one user about user id")
	public ResponseEntity updateToUserAndMarketByIdAndMid(@ModelAttribute User userParams)
	{
		User user = userService.updateToUser(userParams);

		if(user == null)	throw new NotFoundException();

		return new ResponseEntity(HttpStatus.OK);
	}

	@DeleteMapping("/users/{uid}/markets/{mid}")
	@ApiOperation(value = "" , notes = "Delete one user about user id")
	public ResponseEntity deleteToUserAndMarketByIdAndMid(@RequestParam("uid") int uid)
	{
		Boolean resultByDelete = userService.deleteToUser(uid);

		if(!resultByDelete)	throw new NotFoundException();

		return new ResponseEntity(HttpStatus.OK);
	}


}
