package ru.isu.CourseProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.isu.CourseProject.model.Message;
import ru.isu.CourseProject.model.User;

import javax.validation.Valid;
import java.time.LocalDate;

@RequestMapping
@Controller
public class MainController {

    @RequestMapping( value = "/" )
    public String index(){
        return "index";
    }
}
