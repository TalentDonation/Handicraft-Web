package com.handicraft.admin.controller;

import com.handicraft.core.dto.Event;
import com.handicraft.core.dto.EventToUser;
import com.handicraft.core.service.EventService;
import com.handicraft.core.service.EventToUserService;
import lombok.extern.slf4j.Slf4j;
import org.mortbay.util.ajax.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@Slf4j
public class CalenderController {

    @Autowired
    EventToUserService eventToUserService;

    @Autowired
    EventService eventService;

    @RequestMapping("/calender")
    public ModelAndView getCalender() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("calender");

        return mv;
    }

    @RequestMapping(value = "/calender/event", method = RequestMethod.GET)
    @ResponseBody
    public List<EventToUser> findEvent() {
        log.info("I'm in event");

        List<EventToUser> eventToUserList = eventToUserService.find();

        log.info("This is start: " + eventToUserList.get(1).getStart().toString());
        log.info("This is end: " + eventToUserList.get(1).getEnd().toString());

        return eventToUserList;
    }

    // TODO: 날짜 형식: yyyy-MM-dd hh:mm:ss
    @RequestMapping(value = "/calender/newevent", method = RequestMethod.GET)
    public ResponseEntity registerEvent(@RequestParam("title") String title, @RequestParam("start") String start, @RequestParam("end") String end) throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        log.info("I'm in newEvent!");
        log.info(title);

        Event event = new Event();
        event.setEid(0);
        event.setStart(simpleDateFormat.parse(start));
        event.setEnd(simpleDateFormat.parse(end));
        event.setTitle(title);

        Event result = eventService.insert(event);

        return new ResponseEntity<>(result , HttpStatus.ACCEPTED);

    }

    @RequestMapping(value = "/calender/modifyevent", method = RequestMethod.GET)
    public ResponseEntity modifyEvent(@RequestParam("title") String title, @RequestParam("time") String time) {
        log.info("It's in the modify");
        String times[] = time.split("-");
        String start = times[0].trim();
        String end = times[1].trim();
        log.info("start: " + start);
        log.info("end: " + end);
        List<String> result = new ArrayList<>();
        result.add(title);
        result.add(start);
        result.add(end);

        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/calender/deleteevent", method = RequestMethod.GET)
    public ResponseEntity deleteEvent(@RequestParam("id") String id) {
        log.info("This is id: " + id);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }


}
