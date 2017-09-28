package com.handicraft.admin.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import com.handicraft.core.service.UserToEventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.xml.ws.Response;

@Controller
@Slf4j
public class CalenderController {

	@Autowired
	UserToEventService userToEventService;

	@RequestMapping("/calender")
	public ModelAndView getCalender( ) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("calender");


		
		return mv;
	}

	@RequestMapping("/calender/token")
	public String getNaverLogin(@RequestParam String code , @RequestParam String state ) throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();

		StringBuffer stringBuffer = new StringBuffer("https://nid.naver.com/oauth2.0/token")
				.append("?grant_type=authorization_code")
				.append("&client_id=XhmXptV7PpE7X_2Zp7lw")
				.append("&client_secret=WB43Gka_2U")
				.append("&code=").append(code)
				.append("&state=").append(state);



		String result = restTemplate.getForObject(URI.create(stringBuffer.toString()), String.class);

		System.out.println(result);

		return "redirect:/calender";
	}

	@RequestMapping(value = "/calender/newevent", method = RequestMethod.GET)
	public ResponseEntity registerEvent(@RequestParam("title") String title, @RequestParam("time") String time) {
		log.info("It's in the new");
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
