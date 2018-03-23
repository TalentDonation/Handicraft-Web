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
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping(value = "/users")
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity findUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/users/{uid}")
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity findUser(@PathVariable("uid") long uid) {
        return ResponseEntity.ok(userService.findOne(uid));
    }

    @PutMapping("/users/{uid}")
    @ApiOperation(value = "", notes = "Update users")
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity updateUser(@PathVariable("uid") long uid, @RequestBody UserDto userDto) {
        userService.update(userDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/users/{uid}")
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity removeUser(@PathVariable("uid") long uid) {
        userService.remove(uid);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users/{uid}/avatar")
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity findUserAvatar(@PathVariable("uid") long uid) {
        return ResponseEntity.ok(userService.findAvatar(uid));
    }

    @PostMapping("/users/{uid}/avatar")
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity findUserAvatar(@PathVariable("uid") long uid, MultipartFile multipartFile) {
        userService.storeAvatar(uid, multipartFile);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/users/{uid}/avatar")
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity updateUserAvatar(@PathVariable("uid") long uid, MultipartFile multipartFile) {
        userService.changeAvatar(uid, multipartFile);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/users/{uid}/avatar")
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity removeUserAvatar(@PathVariable("uid") long uid) {
        userService.removeAvatar(uid);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/users/{uid}/furniture")
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity findUserFurniture(@PathVariable("uid") long uid, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "per_page", defaultValue = "10") int perPage) {
        return ResponseEntity.ok(furnitureService.find(uid, page, perPage));
    }

    @PutMapping("/users/{uid}/furniture")
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity updateUserFurniture(@RequestBody List<FurnitureDto> furnitureDtos) {
        furnitureService.update(furnitureDtos);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/users/{uid}/furniture")
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity removeUserFurniture(@RequestParam("uid") int uid) {
        furnitureService.removeByUid(uid);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users/{uid}/furniture/{fid}")
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity findUserFurnitureOne(@PathVariable("uid") long uid, @PathVariable("fid") long fid) {
        return ResponseEntity.ok(furnitureService.findOne(fid));
    }

    @PutMapping("/users/{uid}/furniture/{fid}")
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity updateUserFurnitureOne(@PathVariable("uid") long uid, @PathVariable("fid") long fid, @ModelAttribute FurnitureDto furnitureDto) {
        furnitureService.updateOne(furnitureDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/users/{uid}/furniture/{fid}")
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity removeUserFurnitureOne(@PathVariable("uid") long uid, @PathVariable("fid") long fid) {
        furnitureService.remove(fid);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users/{uid}/events")
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity findUserEvents(@PathVariable("uid") int uid) {
        eventService.find(uid);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/users/{uid}/events")
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity createUserEvents(@PathVariable("uid") long uid, @RequestBody EventDto eventDto) {
        eventService.create(uid, eventDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/users/{uid}/events")
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity updateUserEvents(@PathVariable("uid") long uid, @ModelAttribute("event") List<EventDto> eventDtos) {
        eventService.update(uid, eventDtos);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/users/{uid}/events")
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity removeUserEvents(@RequestParam("uid") long uid) {
        eventService.removeByUid(uid);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users/{uid}/events/{eid}")
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity findUserEvent(@PathVariable("uid") int uid, @PathVariable("uid") int eid) {
        return ResponseEntity.ok(eventService.findOne(uid, eid));
    }

    @PutMapping("/users/{uid}/events/{eid}")
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity updateUserEvent(@PathVariable("uid") int uid, @PathVariable("uid") int eid, @ModelAttribute("event") EventDto eventDto) {
        eventService.updateOne(uid, eventDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/users/{uid}/events/{eid}")
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity removeUserEvent(@PathVariable("uid") int uid, @PathVariable("eid") int eid) {
        eventService.removeByUidAndEid(uid, eid);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users/{uid}/comments")
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity findUserComments(@PathVariable("uid") long uid, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "per_page", defaultValue = "10") int perPage) {
        return ResponseEntity.ok(commentService.findByUid(uid));
    }

    @DeleteMapping("/users/{uid}/comments")
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity removeUserComments(@PathVariable("uid") long uid) {
        commentService.removeByUid(uid);
        return ResponseEntity.ok().build();
    }

}
