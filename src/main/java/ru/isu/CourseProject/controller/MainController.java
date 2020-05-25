package ru.isu.CourseProject.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ru.isu.CourseProject.model.User;

import java.util.Arrays;
import java.util.List;

@RequestMapping
@Controller
@SessionAttributes( types =  User.class )
public class MainController {

    @Autowired
    private HttpSession httpSession;

    @RequestMapping( value = "/" )
    public String index( HttpServletRequest httpServletRequest ){
//        System.out.println( httpServletRequest.getSession() );
//        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
//        HttpSession session = attr.getRequest().getSession();
//        System.out.println( session.getServletContext() );
        return "index";
    }

    @RequestMapping( "/getsession" )
    public @ResponseBody String getHttpSession( HttpServletRequest httpServletRequest ){
        System.out.println( httpServletRequest.getSession().getServletContext().getSessionCookieConfig().getComment() );
        System.out.println( httpServletRequest.getSession().getId() );
        return "a";
    }
}
