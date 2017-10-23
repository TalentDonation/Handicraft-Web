package com.handicraft.api.controller;


import com.handicraft.core.dto.Events.EventToUser;
import com.handicraft.core.service.Events.EventToUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
@Api(value = "events" , description = "Event API")
public class EventController {

    @Autowired
    EventToUserService eventToUserService;


    @GetMapping("/events")
    @ApiOperation(value = "" , notes = "Delete one user about user id")
    @ApiImplicitParam(name = "authorization", value="authorization", dataType = "string", paramType = "header")
    public List<EventToUser> findEvents()
    {
        List<EventToUser> eventToUserList = eventToUserService.find();

        for(EventToUser eventToUser : eventToUserList)
        {
            eventToUser.setUid(eventToUser.getUserList().get(0).getUid());
        }

        return eventToUserList;
    }

    @GetMapping("/events/between")
    @ApiOperation(value = "" , notes = "Delete one user about user id")
    @ApiImplicitParam(name = "authorization", value="authorization", dataType = "string", paramType = "header")
    public List<EventToUser> findEventsByDate(@RequestParam("start") Date start  , @RequestParam("end") Date end) {

        List<EventToUser> eventToUserList = eventToUserService.findByDate(start , end);

        for(EventToUser eventToUser : eventToUserList)
        {
            eventToUser.setUid(eventToUser.getUserList().get(0).getUid());
        }

        return eventToUserList;
    }
}
