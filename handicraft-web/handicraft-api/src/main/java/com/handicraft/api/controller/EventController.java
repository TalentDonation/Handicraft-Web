package com.handicraft.api.controller;


import com.handicraft.core.dto.EventDto;
import com.handicraft.core.service.EventService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@Api(value = "events", description = "Event API")
@RestController
public class EventController {
    @Autowired
    private EventService eventService;

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/events")
    @ApiOperation(value = "", notes = "Delete one user about user id")
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity findEvents() {
        return ResponseEntity.ok(eventService.findAll());
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @PostMapping(value = "/events", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity createEvents(@RequestBody EventDto eventDto) {
        eventService.create(eventDto);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping(value = "/events/{eid}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity findEvent(@PathVariable("uid") int eid) {
        return ResponseEntity.ok(eventService.findOne(eid));
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @PutMapping(value = "/events/{eid}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity updateEvent(@PathVariable("uid") int eid, @RequestBody EventDto eventDto) {
        eventService.updateOne(eventDto);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @DeleteMapping("/events/{eid}")
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity removeEvent(@PathVariable("eid") int eid) {
        eventService.removeByEid(eid);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping("/events/between")
    @ApiOperation(value = "", notes = "Delete one user about user id")
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity findEventsBetween(@RequestParam("start") String start, @RequestParam("end") String end) throws ParseException {
        return ResponseEntity.ok(eventService.findBetween(start, end));
    }
}
