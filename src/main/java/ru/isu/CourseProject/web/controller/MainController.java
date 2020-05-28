package ru.isu.CourseProject.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ru.isu.CourseProject.domain.model.User;

@RequestMapping
@Controller
public class MainController {

    @RequestMapping( value = "/", method = RequestMethod.GET )
    public String index(){
        return "index";
    }

    @RequestMapping(value="/login", method= RequestMethod.GET)
    public String goLogin( Model model ){
        return "login";
    }

}
