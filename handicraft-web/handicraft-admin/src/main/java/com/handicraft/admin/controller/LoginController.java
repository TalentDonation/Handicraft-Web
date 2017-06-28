package com.handicraft.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by 고승빈 on 2017-06-28.
 */

@Controller
public class LoginController {

    @RequestMapping("/login")
    public ModelAndView getCalender()
    {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("login");

        return mv;
    }
}
