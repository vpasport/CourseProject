package ru.isu.CourseProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.isu.CourseProject.model.User;
import ru.isu.CourseProject.repository.UserRepository;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping( "/users" )
public class UserController {

    @Autowired
    private UserRepository userRepository;

    /*
        CREATE USER
     */

    @RequestMapping( value = "/create", method = RequestMethod.GET )
    public String create( Model model ){
        User user = new User();
        model.addAttribute( "user", user );
        return "user/createUser";
    }

    @CrossOrigin
    @RequestMapping( value = "/jsonRoles", method = RequestMethod.GET)
    public @ResponseBody List<String> getRolesJSON(){
        return Arrays.asList( "Executor", "Customer", "Admin" );
    }

    @ModelAttribute( "roles" )
    public List<String> getRoles(){
        return Arrays.asList( "Executor", "Customer", "Admin" );
    }

    @CrossOrigin
    @RequestMapping( value = "/jsonSex", method = RequestMethod.GET)
    public @ResponseBody List<String> getSexJSON(){
        return Arrays.asList( "Male", "Female", "Other" );
    }

    @ModelAttribute( "sex" )
    public List<String> getSex(){
        return Arrays.asList( "Male", "Female", "Other" );
    }

    @RequestMapping( value = "/", method = RequestMethod.POST )
    public String create(
            @Valid @ModelAttribute( "user" ) User user,
            BindingResult errors, Model model ){
        System.out.println( user );
        if( errors.hasErrors() ) return "/index";

        user.setLastActivity( LocalDate.now() );
        user.setRating( 0. );
        System.out.println( user );

        userRepository.save( user );

//        userRepository.createUser(
//                user.getFirstName(),
//                user.getSecondName(),
//                user.getLogin(),
//                user.getPassword(),
//                user.getEmail(),
//                user.getLastActivity(),
//                user.getRole(),
//                user.getPhone(),
//                user.getRating(),
//                user.getSex(),
//                user.getAge(),
//                user.getSpecialty()
//        );

        return "redirect:all";
    }

    /*
        ALL USERS
     */

    @CrossOrigin
    @RequestMapping( value = "/allJson", method = RequestMethod.GET )
    public @ResponseBody List<User> allJSON(){
        return userRepository.getAll();
    }

    @RequestMapping( value = "/all", method = RequestMethod.GET)
    public String all( Model model ){
        model.addAttribute( "users", userRepository.getAll() );
        return "user/users";
    }

    /*
        GET USER BY ID
     */

    @RequestMapping( value = "/getById", method = RequestMethod.GET )
    public String getById( @RequestParam( "id" ) Integer id,  Model model ){
        model.addAttribute( "user", userRepository.searchById( id ) );
        return "user/user";
    }

    @CrossOrigin
    @RequestMapping( value = "/getByIdJson", method = RequestMethod.GET )
    public @ResponseBody List<User> getByIdJson( @RequestParam( "id" ) Integer id ){
        return  Arrays.asList( userRepository.searchById( id ) );
    }

    /*
        AUTH USER
     */

    @CrossOrigin
    @RequestMapping( value = "/auth", method = RequestMethod.GET )
    public @ResponseBody List<User> authUser(
            @RequestParam( "login" ) String login,
            @RequestParam( "password" ) String password
    ){
        User user = userRepository.getByLogin( login );

        if( user.getPassword().equals( password ) ){
            return Arrays.asList( user );
        } else {
            return null;
        }
    }
}
