package com.example.fsjpa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.fsjpa.entity.User;
import com.example.fsjpa.services.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class GreetingController {

    private final UserService userService;

    @RequestMapping("/greet")
    public ModelAndView showview() {
        String str = "";
        User u = userService.getUser(1);
        if(u != null) str += "Hello " + u.getFirstName() + " " + u.getLastName();
        ModelAndView mv = new ModelAndView();
        mv.setViewName("result");
        mv.addObject("message",
                "Welcomes "
                + "you to Spring!<br/>"
                + str);
        return mv;
    }

    @RequestMapping("/")
    public ModelAndView showIndex() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");
        return mv;
    }
}
