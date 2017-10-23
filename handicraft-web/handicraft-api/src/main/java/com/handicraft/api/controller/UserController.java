package com.handicraft.api.controller;


import com.handicraft.api.exception.BadRequestException;
import com.handicraft.api.exception.NotFoundException;
import com.handicraft.core.dto.Events.Event;
import com.handicraft.core.dto.Events.EventToUser;
import com.handicraft.core.dto.Users.User;
import com.handicraft.core.dto.Users.UserToEvent;
import com.handicraft.core.dto.Users.UserToImage;
import com.handicraft.core.service.Events.EventService;
import com.handicraft.core.service.Events.EventToUserService;
import com.handicraft.core.service.Users.UserService;
import com.handicraft.core.service.Users.UserToAuthorityService;
import com.handicraft.core.service.Users.UserToEventService;
import com.handicraft.core.service.Users.UserToImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

@RestController
@Slf4j
@Api(value = "user" , description = "USER API")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	EventService eventService;

	@Autowired
	UserToImageService userToImageService;

	@Autowired
	UserToAuthorityService userToAuthorityService;

	@Autowired
	UserToEventService userToEventService;

	@Autowired
	EventToUserService eventToUserService;

	@Value("${images-path}")
	String imagePath;


	@GetMapping("/users")
	@Transactional
	@ApiOperation(value = "" , notes = "Show one user about user id")
	@ApiImplicitParam(name = "authorization", value="authorization", dataType = "string", paramType = "header")
	public User findUser(@AuthenticationPrincipal Long uid)
	{
		UserToImage userToImage = userToImageService.findToUserToImage(uid);

		StringBuffer avatar = new StringBuffer();
		User user = new User(userToImage);

		if(userToImage.getImage() != null)
		{
			avatar.append(imagePath)
					.append(userToImage.getImage().getGid())
					.append(".").append(userToImage.getImage().getExtension());
			user.setAvatar(avatar.toString());
		}
		else
			user.setAvatar(null);


		log.info("Find User : " + user);

		return user;
	}


	@PutMapping("/users")
	@ApiOperation(value = "" , notes = "Update users")
	@ApiImplicitParam(name = "authorization", value="authorization", dataType = "string", paramType = "header")
	public ResponseEntity updateToUsers(@AuthenticationPrincipal Long uid , @ModelAttribute User updateUser , MultipartFile multipartFile)
	{
		UserToImage userToImage = userToImageService.findToUserToImage(uid);


		updateUser.setUid(uid);

		userService.updateToUser(updateUser);



		return new ResponseEntity(HttpStatus.OK);
	}

	@DeleteMapping("/users")
	@ApiOperation(value = "" , notes = "Delete users")
	@ApiImplicitParam(name = "authorization", value="authorization", dataType = "string", paramType = "header")
	public ResponseEntity deleteToUsers(@AuthenticationPrincipal Long uid)
	{
		UserToImage deleteUserToImage = userToImageService.deleteUserToImage(uid);


		StringBuffer deleteImageFilePath = new StringBuffer("classpath:static/images/")
				.append(Long.toString(deleteUserToImage.getImage().getGid()))
				.append(".").append(deleteUserToImage.getImage().getExtension());
		try {
			File file = ResourceUtils.getFile(deleteImageFilePath.toString());
			file.delete();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return new ResponseEntity(HttpStatus.OK);
	}




	/*
	* user - image
	* */

	@GetMapping("/users/{uid}/avatar")
	@ApiOperation(value = "" , notes = "Show one user about user id")
	@ApiImplicitParam(name = "authorization", value="authorization", dataType = "string", paramType = "header")
	public User findAvatarOnUsers(@PathVariable("uid") int uid)
	{
		User user = userService.findByUser(uid);


		if(user == null)	throw new NotFoundException();

		return user;
	}

	@PutMapping("/users/{uid}/avatar")
	@ApiOperation(value="" , notes = "Update one user about user id")
	@ApiImplicitParam(name = "authorization", value="authorization", dataType = "string", paramType = "header")
	public ResponseEntity updateAvatarOnUsers(@ModelAttribute User userParams)
	{
		User user = userService.updateToUser(userParams);

		if(user == null)	throw new NotFoundException();

		return new ResponseEntity(HttpStatus.OK);
	}

	@DeleteMapping("/users/{uid}/avatar")
	@ApiOperation(value = "" , notes = "Delete one user about user id")
	@ApiImplicitParam(name = "authorization", value="authorization", dataType = "string", paramType = "header")
	public ResponseEntity deleteAvatarOnUsers(@RequestParam("uid") int uid)
	{
		Boolean resultByDelete = userService.deleteToUser(uid);

		if(!resultByDelete)	throw new NotFoundException();

		return new ResponseEntity(HttpStatus.OK);
	}

	/*
	* user - market mid
	* */

	@GetMapping("/users/{uid}/furniture")
	@ApiOperation(value = "" , notes = "Show one user about user id")
	@ApiImplicitParam(name = "authorization", value="authorization", dataType = "string", paramType = "header")
	public User findFurnitureOnUsers(@PathVariable("uid") int uid)
	{
		User user = userService.findByUser(uid);


		if(user == null)	throw new NotFoundException();

		return user;
	}

	@PostMapping("/users/{uid}/furniture")
	@ApiOperation(value = "" , notes = "Show one user about user id")
	@ApiImplicitParam(name = "authorization", value="authorization", dataType = "string", paramType = "header")
	public User insertFurnitureOnUsers(@PathVariable("uid") int uid)
	{
		User user = userService.findByUser(uid);


		if(user == null)	throw new NotFoundException();

		return user;
	}

	@PutMapping("/users/{uid}/furniture")
	@ApiOperation(value="" , notes = "Update one user about user id")
	@ApiImplicitParam(name = "authorization", value="authorization", dataType = "string", paramType = "header")
	public ResponseEntity updateFurnitureOnUsers(@ModelAttribute User userParams)
	{
		User user = userService.updateToUser(userParams);

		if(user == null)	throw new NotFoundException();

		return new ResponseEntity(HttpStatus.OK);
	}

	@DeleteMapping("/users/{uid}/furniture")
	@ApiOperation(value = "" , notes = "Delete one user about user id")
	@ApiImplicitParam(name = "authorization", value="authorization", dataType = "string", paramType = "header")
	public ResponseEntity deleteFurnitureOnUsers(@RequestParam("uid") int uid)
	{
		Boolean resultByDelete = userService.deleteToUser(uid);

		if(!resultByDelete)	throw new NotFoundException();

		return new ResponseEntity(HttpStatus.OK);
	}

	@GetMapping("/users/{uid}/events")
	@ApiOperation(value = "" , notes = "Show one user about user id")
	@ApiImplicitParam(name = "authorization", value="authorization", dataType = "string", paramType = "header")
	public UserToEvent findEventsOnUsersByUid(@PathVariable("uid") int uid , @AuthenticationPrincipal Long authUid)
	{
		if(authUid != uid)	throw new BadRequestException();

		UserToEvent userToEvent = userToEventService.find(uid);


		return userToEvent;
	}

	@PostMapping("/users/{uid}/events")
	@ApiOperation(value = "" , notes = "Show one user about user id")
	@ApiImplicitParam(name = "authorization", value="authorization", dataType = "string", paramType = "header")
	public ResponseEntity insertEventsOnUsersByUid(@PathVariable("uid") long uid , @ModelAttribute("event")Event event)
	{
		UserToEvent userToEvent = userToEventService.find(uid);

		event.setEid(0);
		List<Event> eventList = userToEvent.getEventList();
		eventList.add(event);
		userToEvent.setEventList(eventList);

		userToEventService.update(userToEvent);

		return new ResponseEntity(HttpStatus.OK);
	}


	@DeleteMapping("/users/{uid}/events")
	@ApiOperation(value = "" , notes = "Delete one user about user id")
	@ApiImplicitParam(name = "authorization", value="authorization", dataType = "string", paramType = "header")
	public ResponseEntity deleteEventsOnUsersByUid(@RequestParam("uid") long uid)
	{

		eventToUserService.removeByUid(uid);

		return new ResponseEntity(HttpStatus.OK);
	}

	@GetMapping("/users/{uid}/events/{eid}")
	@ApiOperation(value = "" , notes = "Show one user about user id")
	@ApiImplicitParam(name = "authorization", value="authorization", dataType = "string", paramType = "header")
	public Event findEventOnUsersByUidANDEid(@PathVariable("uid") int uid , @PathVariable("uid") int eid ,  @AuthenticationPrincipal Long authUid)
	{
		Event result;

		if(authUid != uid)	new BadRequestException();

		result = eventService.findById(eid);

		if(result == null) new NotFoundException();

		return result;
	}

	@PutMapping("/users/{uid}/events/{eid}")
	@ApiOperation(value="" , notes = "Update one user about user id")
	@ApiImplicitParam(name = "authorization", value="authorization", dataType = "string", paramType = "header")
	public ResponseEntity updateEventOnUsersByUid(@PathVariable("uid") int uid , @PathVariable("uid") int eid, @ModelAttribute("event")Event event)
	{

		if(eventService.findById(eid) == null) throw new NotFoundException();

		eventService.update(event);

		return new ResponseEntity(HttpStatus.OK);
	}

	@DeleteMapping("/users/{uid}/events/{eid}")
	@ApiOperation(value = "" , notes = "Delete one user about user id")
	@ApiImplicitParam(name = "authorization", value="authorization", dataType = "string", paramType = "header")
	public ResponseEntity deleteEventOnUsersByUidANDEid(@PathVariable("uid") int uid , @PathVariable("uid") int eid ,  @AuthenticationPrincipal Long authUid)
	{
		if(authUid != uid)	new BadRequestException();

		return new ResponseEntity(HttpStatus.OK);
	}


}
