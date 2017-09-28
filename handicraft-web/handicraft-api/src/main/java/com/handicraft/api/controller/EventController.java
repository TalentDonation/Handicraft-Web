package com.handicraft.api.controller;


import com.handicraft.core.dto.Event;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Api(value = "events" , description = "Events API")
public class EventController {

    @PostMapping("/events")
    @ApiOperation(value = "" , notes = "Update multiple furniture")
    public ResponseEntity findEvents(@ModelAttribute Event event)
    {
        return new ResponseEntity(HttpStatus.CREATED);

    }

}
