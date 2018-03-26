package com.handicraft.api.controller;


import com.handicraft.core.service.EventService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@Api(value = "events", description = "Event API")
@RestController
public class EventController {
    @Autowired
    private EventService eventService;

    @GetMapping("/events")
    @ApiOperation(value = "", notes = "Delete one user about user id")
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity findEvents() {
        return ResponseEntity.ok(eventService.findAll());
    }

    @GetMapping("/events/between")
    @ApiOperation(value = "", notes = "Delete one user about user id")
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity findEventsBetween(@RequestParam("start") String start, @RequestParam("end") String end) throws ParseException {
        return ResponseEntity.ok(eventService.findBetween(start, end));
    }
}
