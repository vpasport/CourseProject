package ru.isu.CourseProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
@Controller
public class MainController {

    @RequestMapping( value = "/" )
    public String index(){
        return "index";
    }
}
