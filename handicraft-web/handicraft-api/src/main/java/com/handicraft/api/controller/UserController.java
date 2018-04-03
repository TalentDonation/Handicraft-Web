package com.handicraft.api.controller;


import com.handicraft.core.dto.EventDto;
import com.handicraft.core.dto.FurnitureDto;
import com.handicraft.core.dto.UserDto;
import com.handicraft.core.service.CommentService;
import com.handicraft.core.service.EventService;
import com.handicraft.core.service.FurnitureService;
import com.handicraft.core.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(value = "user", description = "USER API")
@RestController
public class UserController {
    private final UserService userService;
    private final FurnitureService furnitureService;
    private final EventService eventService;
    private final CommentService commentService;

    @Autowired
    public UserController(UserService userService, FurnitureService furnitureService, EventService eventService, CommentService commentService) {
        this.userService = userService;
        this.furnitureService = furnitureService;
        this.eventService = eventService;
        this.commentService = commentService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity findUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN') and #uid == principal")
    @GetMapping(value = "/users/{uid}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity findUser(@PathVariable("uid") long uid) {
        return ResponseEntity.ok(userService.findOne(uid));
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN') and #uid == principal and #userDto.uid == principal")
    @PutMapping(value = "/users/{uid}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity updateUser(@PathVariable("uid") long uid, @RequestBody UserDto userDto) {
        userService.update(userDto);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN') and #uid == principal")
    @DeleteMapping("/users/{uid}")
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity removeUser(@PathVariable("uid") long uid) {
        userService.remove(uid);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN') and #uid == principal")
    @GetMapping(value = "/users/{uid}/avatar", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity findUserAvatar(@PathVariable("uid") long uid) {
        return ResponseEntity.ok(userService.findAvatar(uid));
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN') and #uid == principal")
    @PostMapping(value = "/users/{uid}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity createUserAvatar(@PathVariable("uid") long uid, @RequestParam("file") MultipartFile multipartFile) {
        long fileSize = userService.storeAvatar(uid, multipartFile);
        if(fileSize <= 0) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN') and #uid == principal")
    @PutMapping(value = "/users/{uid}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity updateUserAvatar(@PathVariable("uid") long uid, @ModelAttribute("file") MultipartFile multipartFile) {
        long fileSize = userService.changeAvatar(uid, multipartFile);
        if(fileSize <= 0) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN') and #uid == principal")
    @DeleteMapping("/users/{uid}/avatar")
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity removeUserAvatar(@PathVariable("uid") long uid) {
        userService.removeAvatar(uid);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN') and #uid == principal")
    @GetMapping(value = "/users/{uid}/furniture", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity findUserFurniture(@PathVariable("uid") long uid, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "per_page", defaultValue = "10") int perPage) {
        return ResponseEntity.ok(furnitureService.find(uid, page, perPage));
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN') and #uid == principal")
    @PutMapping(value = "/users/{uid}/furniture", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity updateUserFurniture(@RequestBody List<FurnitureDto> furnitureDtos) {
        furnitureService.update(furnitureDtos);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN') and #uid == principal")
    @DeleteMapping("/users/{uid}/furniture")
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity removeUserFurniture(@RequestParam("uid") int uid) {
        furnitureService.removeByUid(uid);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN') and #uid == principal")
    @GetMapping(value = "/users/{uid}/events", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity findUserEvents(@PathVariable("uid") int uid) {
        eventService.find(uid);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN') and #uid == principal")
    @PutMapping(value = "/users/{uid}/events", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity updateUserEvents(@PathVariable("uid") long uid, @RequestBody List<EventDto> eventDtos) {
        eventService.update(uid, eventDtos);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN') and #uid == principal")
    @DeleteMapping("/users/{uid}/events")
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity removeUserEvents(@RequestParam("uid") long uid) {
        eventService.removeByUid(uid);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN') and #uid == principal")
    @GetMapping(value = "/users/{uid}/comments", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity findUserComments(@PathVariable("uid") long uid, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "per_page", defaultValue = "10") int perPage) {
        return ResponseEntity.ok(commentService.findByUid(uid));
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN') and #uid == principal")
    @DeleteMapping("/users/{uid}/comments")
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity removeUserComments(@PathVariable("uid") long uid) {
        commentService.removeByUid(uid);
        return ResponseEntity.ok().build();
    }

}
