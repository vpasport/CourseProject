package ru.isu.CourseProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.isu.CourseProject.model.User;
import ru.isu.CourseProject.repository.UserRepository;

import javax.validation.Valid;

@Controller
@RequestMapping( "/users" )
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping( value = "/create", method = RequestMethod.GET )
    public String create( Model model ){
        User user = new User();
        model.addAttribute( "user", user );
        return "user/createUser";
    }

    @RequestMapping( value = "/", method = RequestMethod.POST )
    public String create(@Valid @ModelAttribute( "user" ) User user, BindingResult errors, Model model ){
        if( errors.hasErrors() ) return "/index";

        userRepository.createUser(
                user.getLogin(),
                user.getPassword(),
                user.getFirstName(),
                user.getSecondName()
        );

        return "redirect:/user/users";
    }

    @RequestMapping( value = "/all", method = RequestMethod.GET)
    public String all( Model model ){
        model.addAttribute( "users", userRepository.getAll() );
        return "user/users";
    }

    @RequestMapping( value = "/getById", method = RequestMethod.GET )
    public String getById( @RequestParam( "id" ) Integer id,  Model model ){
        model.addAttribute( "user", userRepository.searchById( id ) );
        return "user/user";
    }
}
