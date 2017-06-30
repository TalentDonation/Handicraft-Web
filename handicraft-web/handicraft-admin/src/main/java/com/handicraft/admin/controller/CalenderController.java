package com.handicraft.admin.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;

@Controller
public class CalenderController {

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

}
