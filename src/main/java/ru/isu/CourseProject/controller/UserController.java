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
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

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

    @ModelAttribute( "roles" )
    public List<String> getRoles(){
        return Arrays.asList( "Executor", "Customer", "Admin" );
    }

    @ModelAttribute( "sex" )
    public List<String> getSex(){
        return Arrays.asList( "Male", "Female", "Other" );
    }

    @RequestMapping( value = "/", method = RequestMethod.POST )
    public String create(@Valid @ModelAttribute( "user" ) User user, BindingResult errors, Model model ){
        System.out.println( errors );
        if( errors.hasErrors() ) return "/index";

        user.setLastActivity( LocalDate.now() );
        user.setRating( 0. );

        System.out.println( user );

        userRepository.save( user );
//        userRepository.createUser(
//                user.getLogin(),
//                user.getPassword(),
//                user.getFirstName(),
//                user.getSecondName()
//        );

        return "redirect:all";
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
