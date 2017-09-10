package com.handicraft.admin.controller;

import com.handicraft.admin.ServletInitializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;

/**
 * Created by 고승빈 on 2017-06-28.
 */

@Controller
@Slf4j
public class LoginController {

    @RequestMapping("/login")
    public ModelAndView getCalender()
    {
        log.info("test");


        ModelAndView mv = new ModelAndView();
        mv.setViewName("login");

        printFile(new File("/templates/"),0);

        return mv;
    }

    private void printFile(File file, int depth) {
        if (depth > 10) {
            return;
        }

        if (file.isDirectory()) {
            for (int i = 0; i < depth; i++)
                System.out.print("  ");
            System.out.println(file.getName() + "/");
            File[] subFiles = file.listFiles();
            for (File subFile: subFiles) {
                printFile(subFile, depth + 1);
            }
        }
        else {
            for (int i = 0; i < depth; i++)
                System.out.print("  ");
            System.out.println(file.getName());
        }
    }
}

