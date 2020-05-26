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
    public static final String DEFAULT_CSRF_TOKEN_ATTR_NAME = HttpSessionCsrfTokenRepository.class.getName().concat(".CSRF_TOKEN");

    @Autowired
    private HttpSession httpSession;

    @RequestMapping( value = "/", method = RequestMethod.GET )
    public String index( HttpServletRequest httpServletRequest, Model model ){
//        System.out.println( httpServletRequest.getSession().getServletContext().getSessionCookieConfig().getComment() );
        System.out.println( httpServletRequest.getSession().getId() );
//        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
//        HttpSession session = attr.getRequest().getSession();
//        System.out.println( session.getServletContext() );
//        return "login";
        return "index";
    }

    @RequestMapping( "/getsession" )
    @PreAuthorize("hasRole('ROLE_ANONYMOUS')")
    public @ResponseBody String getHttpSession( HttpServletRequest httpServletRequest ){
        System.out.println( httpServletRequest.getSession().getId() );
        return httpServletRequest.getSession().getAttribute(DEFAULT_CSRF_TOKEN_ATTR_NAME).toString();
    }

    @RequestMapping(value="/login", method= RequestMethod.GET)
    public String goLogin( Model model ){
        return "login";
    }
}
