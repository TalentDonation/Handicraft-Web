package com.handicraft.admin.controller;


import com.handicraft.core.dto.Events.Event;
import com.handicraft.core.dto.Events.EventToUser;
import com.handicraft.core.service.Events.EventService;
import com.handicraft.core.service.Events.EventToUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


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
        log.info("This is start: "+ start);

        Event event = new Event();
        event.setEid(0);
        event.setStart(simpleDateFormat.parse(start));
        event.setEnd(simpleDateFormat.parse(end));
        event.setTitle(title);

        Event result = eventService.insert(event);

        return new ResponseEntity<>(result , HttpStatus.ACCEPTED);

    }

    @RequestMapping(value = "/calender/modifyevent", method = RequestMethod.GET)
    public ResponseEntity modifyEvent(@RequestParam("title") String title, @RequestParam("start") String start,
                                      @RequestParam("end") String end, @RequestParam("eid")String eid) throws ParseException
    {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        log.info("It's in the modify");
        log.info(title);
        log.info("This is start in modify: " + start);
        log.info("This is eid: " + eid);

        Event event = new Event();

        event.setStart(simpleDateFormat.parse(start));
        event.setEnd(simpleDateFormat.parse(end));
        event.setTitle(title);
        event.setEid(Long.parseLong(eid));
        Event result = eventService.update(event);


        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/calender/deleteevent", method = RequestMethod.GET)
    public ResponseEntity deleteEvent(@RequestParam("id") String id) {
        log.info("I'm in delete!");
        log.info("This is id: " + id);
        List<String> result = new ArrayList<>();

        eventToUserService.remove(Long.parseLong(id));
        result.add(id);

        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }


}
