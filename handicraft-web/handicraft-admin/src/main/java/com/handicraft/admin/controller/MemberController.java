package com.handicraft.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MemberController {

    @RequestMapping(value = "/member")
    public ModelAndView getMember(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("member");
        return mv;
    }

    @RequestMapping(value = "/upload" , method = RequestMethod.GET)
    public ModelAndView uploadFile(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("member");


        return mv;
    }
}
