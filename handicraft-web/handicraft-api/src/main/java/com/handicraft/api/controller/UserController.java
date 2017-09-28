package com.handicraft.api.controller;


import com.handicraft.api.exception.NotFoundException;
import com.handicraft.core.dto.*;
import com.handicraft.core.service.AuthorityService;
import com.handicraft.core.service.UserService;
import com.handicraft.core.service.UserToAuthorityService;
import com.handicraft.core.service.UserToImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
@Api(value = "user" , description = "USER API")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	UserToImageService userToImageService;

	@Autowired
	UserToAuthorityService userToAuthorityService;

	@Value("${images-path}")
	String imagePath;


	@GetMapping("/users")
	@ApiOperation(value = "" , notes = "Show one user about user id")
	@ApiImplicitParam(name = "authorization", value="authorization", dataType = "string", paramType = "header")
	@Transactional
	public User findUser(@AuthenticationPrincipal Long uid)
	{
		UserToImage userToImage = userToImageService.findToUserToImage(uid);

		StringBuffer avatar = new StringBuffer();
		avatar.append(userToImage.getImage().getName())
				.append(userToImage.getImage().getGid())
				.append(".").append(userToImage.getImage().getExtension());

		User user = new User(userToImage);

		user.setAvatar(avatar.toString());

		log.info("Find User : " + user);

		return user;
	}

	@PostMapping("/users")
	@ApiOperation(value = "" , notes = "Create one user")
	@ApiImplicitParam(name = "authorization", value="authorization", dataType = "string", paramType = "header")
	public ResponseEntity insertToUser(@ModelAttribute User userParams , MultipartFile multipartFile)
	{
		UserToImage userToImage = new UserToImage(userParams);

		Image image = new Image();
		image.setGid(0);
		image.setName(imagePath);
		image.setExtension(multipartFile.getOriginalFilename().split("\\.")[1]);

		userToImage.setImage(image);

		UserToImage insertResult = userToImageService.insertToUserToImage(userToImage);

		StringBuffer uri = new StringBuffer();
		try {
			uri.append(ResourceUtils.getFile("classpath:static/images").getPath())
                    .append("/").append(insertResult.getImage().getGid())
					.append(".").append(insertResult.getImage().getExtension());

			multipartFile.transferTo(new File(uri.toString()));

		} catch (IOException e) {
			e.printStackTrace();
		}

		return new ResponseEntity(HttpStatus.CREATED);
	}

	@PutMapping("/users")
	@ApiOperation(value = "" , notes = "Update users")
	@ApiImplicitParam(name = "authorization", value="authorization", dataType = "string", paramType = "header")
	public ResponseEntity updateToUsers(@AuthenticationPrincipal Long uid , @ModelAttribute User userParams , MultipartFile multipartFile)
	{
		userParams.setUid(uid);

		userService.updateToUser(userParams);

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

	@GetMapping("/users/avatar")
	@ApiOperation(value = "" , notes = "Show one user about user id")
	@ApiImplicitParam(name = "authorization", value="authorization", dataType = "string", paramType = "header")
	public User findAvatarOnUsers(@PathVariable("uid") int uid)
	{
		User user = userService.findByUser(uid);


		if(user == null)	throw new NotFoundException();

		return user;
	}

	@PutMapping("/users/avatar")
	@ApiOperation(value="" , notes = "Update one user about user id")
	@ApiImplicitParam(name = "authorization", value="authorization", dataType = "string", paramType = "header")
	public ResponseEntity updateAvatarOnUsers(@ModelAttribute User userParams)
	{
		User user = userService.updateToUser(userParams);

		if(user == null)	throw new NotFoundException();

		return new ResponseEntity(HttpStatus.OK);
	}

	@DeleteMapping("/users/avatar")
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

	@GetMapping("/users/furniture")
	@ApiOperation(value = "" , notes = "Show one user about user id")
	@ApiImplicitParam(name = "authorization", value="authorization", dataType = "string", paramType = "header")
	public User findFurnitureOnUsers(@PathVariable("uid") int uid)
	{
		User user = userService.findByUser(uid);


		if(user == null)	throw new NotFoundException();

		return user;
	}

	@PostMapping("/users/furniture")
	@ApiOperation(value = "" , notes = "Show one user about user id")
	@ApiImplicitParam(name = "authorization", value="authorization", dataType = "string", paramType = "header")
	public User insertFurnitureOnUsers(@PathVariable("uid") int uid)
	{
		User user = userService.findByUser(uid);


		if(user == null)	throw new NotFoundException();

		return user;
	}

	@PutMapping("/users/furniture")
	@ApiOperation(value="" , notes = "Update one user about user id")
	@ApiImplicitParam(name = "authorization", value="authorization", dataType = "string", paramType = "header")
	public ResponseEntity updateFurnitureOnUsers(@ModelAttribute User userParams)
	{
		User user = userService.updateToUser(userParams);

		if(user == null)	throw new NotFoundException();

		return new ResponseEntity(HttpStatus.OK);
	}

	@DeleteMapping("/users/furniture")
	@ApiOperation(value = "" , notes = "Delete one user about user id")
	@ApiImplicitParam(name = "authorization", value="authorization", dataType = "string", paramType = "header")
	public ResponseEntity deleteFurnitureOnUsers(@RequestParam("uid") int uid)
	{
		Boolean resultByDelete = userService.deleteToUser(uid);

		if(!resultByDelete)	throw new NotFoundException();

		return new ResponseEntity(HttpStatus.OK);
	}


}
