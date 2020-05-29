package ru.isu.CourseProject.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.isu.CourseProject.domain.model.User;
import ru.isu.CourseProject.domain.repository.UserRepository;
import ru.isu.CourseProject.web.ConvertToJson;
import ru.isu.CourseProject.web.CustomAuthenticationProvider;
import ru.isu.CourseProject.web.RoleChecker;


import javax.validation.Valid;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Formatter;
import java.util.List;

@Controller
@RequestMapping( "/users" )
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private CustomAuthenticationProvider authManager = new CustomAuthenticationProvider( userRepository );

    /*
        CREATE USER
     */

    @RequestMapping( value = "/create", method = RequestMethod.GET )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String create( Model model ){
        User user = new User();
        model.addAttribute( "user", user );
        return "user/createUser";
    }

    @CrossOrigin
    @RequestMapping( value = "/jsonRoles", method = RequestMethod.GET )
    public @ResponseBody String getRolesJSON(
            @RequestParam( "token" ) User user
    ) throws JsonProcessingException {
        String check = RoleChecker.check( user );
        if( ! check.equals( "" ) ) return check;

        return ConvertToJson.convert( Arrays.asList( "Executor", "Customer", "Admin" ) );
    }

    @ModelAttribute( "roles" )
    public List<String> getRoles(){
        return Arrays.asList( "ROLE_EXECUTOR", "ROLE_CUSTOMER", "ROLE_ADMIN" );
    }

    @CrossOrigin
    @RequestMapping( value = "/jsonSex", method = RequestMethod.GET)
    public @ResponseBody String getSexJSON(
            @RequestParam( "token" ) User user
    ) throws JsonProcessingException {
        String check = RoleChecker.check( user );
        if( ! check.equals( "" ) ) return check;

        return ConvertToJson.convert( Arrays.asList( "Male", "Female", "Other" ) );
    }

    @ModelAttribute( "sex" )
    public List<String> getSex(){
        return Arrays.asList( "Male", "Female", "Other" );
    }

    @RequestMapping( value = "/", method = RequestMethod.POST )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String create(
            @Valid @ModelAttribute( "user" ) User user,
            BindingResult errors, Model model
    ) throws NoSuchAlgorithmException {
        System.out.println( user );
        if( errors.hasErrors() ) return "/error";

        user.setLastActivity( LocalDate.now() );
        user.setRating( 0. );
        String pass = user.getPassword();
        user.setPassword( getHash( pass ) );
        System.out.println( user );

        userRepository.save( user );

        return "redirect:all";
    }

    public String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        Formatter formatter = new Formatter(sb);
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }
        return sb.toString();
    }

    public String getHash(String pass) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        return bytesToHexString(md.digest(pass.getBytes()));
    }

    @CrossOrigin
    @RequestMapping( value = "/createJson", method = RequestMethod.POST )
    public @ResponseBody String create(
            @RequestParam( "firstname" ) String firstName,
            @RequestParam( "secondname" ) String secondName,
            @RequestParam( "login" ) String login,
            @RequestParam( "password" ) String password,
            @RequestParam( "email" ) String email,
            @RequestParam( "role" ) String role,
            @RequestParam( "phone" ) String phone,
            @RequestParam( "sex" ) String sex,
            @RequestParam( "age" ) Integer age,
            @RequestParam( "specialty" ) String specialty
    ) throws NoSuchAlgorithmException {
        User user = new User();

        if( userRepository.getByLogin( login ) == null ) user.setLogin( login );
        else return "{ \"status\" : false, \"error\" : \"login\" }";
        user.setFirstName( firstName );
        user.setSecondName( secondName );
        user.setPassword( getHash(password) );
        user.setEmail( email );
        user.setRole( role );
        user.setPhone( phone );
        user.setSex( sex );
        if( age >= 18 ) user.setAge( age );
        else return "{ \"status\" : false, \"error\" : \"age\" }";
        user.setSpecialty( specialty );
        user.setLastActivity( LocalDate.now() );
        user.setRating( 0. );
        user.setToken( getHash( String.format( "%s%s%s", login, password, LocalTime.now().toString() ) ) );

        System.out.println( user );

        userRepository.save(user);

        return String.format( "{ \"status\" : \"ok\", \"id\" : \"%s\", \"token\" : \"%s\" }", userRepository.getByLogin( login ).getId(), user.getToken() );
    }

    /*
        ALL USERS
     */

    @CrossOrigin
    @RequestMapping( value = "/allJson", method = RequestMethod.GET )
    public @ResponseBody String allJSON(
            @RequestParam( "token" ) User user
    ) throws JsonProcessingException {
        String check = RoleChecker.check( user );
        if( ! check.equals( "" ) ) return check;

        List<User> users = userRepository.getAll();
        for( User usr : users ) usr.setPassword( null );

        return ConvertToJson.convert( users );
    }

    @RequestMapping( value = "/all", method = RequestMethod.GET )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String all( Model model ){
        model.addAttribute( "users", userRepository.getAll() );
        return "user/users";
    }

    /*
        GET ALL EXECUTORS
     */

    @CrossOrigin
    @RequestMapping( value = "/getallexecutorsJson", method = RequestMethod.GET )
    public @ResponseBody String getAllExecutorsJson(
            @RequestParam( "token" ) User user
    ) throws JsonProcessingException {
        String check = RoleChecker.check( user );
        if( ! check.equals( "" ) ) return check;

        List<User> users = userRepository.getAllByRole( "ROLE_EXECUTOR" );
        for( User usr : users ) usr.setPassword( null );

        return ConvertToJson.convert( users );
    }

    /*
        GET ALL CUSTOMER
     */

    @CrossOrigin
    @RequestMapping( value = "/getallcustomerJson", method = RequestMethod.GET )
    public @ResponseBody String getAllCustomerJson(
            @RequestParam( "token" ) User user
    ) throws JsonProcessingException {
        String check = RoleChecker.check( user );
        if( ! check.equals( "" ) ) return check;

        List<User> users = userRepository.getAllByRole( "ROLE_CUSTOMER" );
        for( User usr : users ) usr.setPassword( null );

        return ConvertToJson.convert( users );
    }

    /*
        GET USER BY ID
     */

    @RequestMapping( value = "/getById", method = RequestMethod.GET )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getById( @RequestParam( "id" ) Integer id,  Model model ){
        model.addAttribute( "user", userRepository.searchById( id ) );
        return "user/user";
    }

    @CrossOrigin
    @RequestMapping( value = "/getByIdJson", method = RequestMethod.GET )
    public @ResponseBody String getByIdJson(
            @RequestParam( "id" ) Integer id,
            @RequestParam( "token" ) User user
    ) throws JsonProcessingException {
        String check = RoleChecker.check( user );
        if( ! check.equals( "" ) ) return check;

        User usr = userRepository.searchById( id );
        user.setPassword( null );

        return ConvertToJson.convert( usr );
    }

    /*
        AUTH USER
     */

    @CrossOrigin
    @RequestMapping( value = "/auth", method = RequestMethod.GET )
    public @ResponseBody String authUser(
            @RequestParam( "login" ) String login,
            @RequestParam( "password" ) String password,
            @RequestParam( "token" ) User user
    ) throws NoSuchAlgorithmException {
        if( user != null ) return "{ \"status\" : \"already authorize\" }";
        User usr = userRepository.getByLogin( login );
        if( usr == null ) return "{ \"status\" : \"user not found\" }";
        if( ! usr.getPassword().equals( getHash( password ) ) ) return "{ \"status\" : \"invalid password\" }";
        String token = getHash( String.format( "%s%s%s", login, password, LocalTime.now().toString() ) );

        userRepository.setToken( token, usr.getId() );

        return String.format( "{ \"token\" : \"%s\", \"id\" : \"%s\" }", token, usr.getId() );

//        if( token.equals( "" ) ) return "{ \" status \" : \" false \" }";
//        String pass = getHash( password );
//        User user = userRepository.getByLogin( login );
//        if( user != null && user.getPassword().equals( pass ) ){
//            Authentication auth  = new UsernamePasswordAuthenticationToken(user,user.getPassword(), user.getAuthorities());
//
//            SecurityContext sc = SecurityContextHolder.getContext();
//            sc.setAuthentication(auth);
//            HttpSession session = req.getSession(true);
//            session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, sc);
//            return String.format( "{ \"JSESSIONID\" : \"%s\" }", RequestContextHolder.currentRequestAttributes().getSessionId() );
//        }
//
    }

    /*
        DELETE BY ID
     */

    @RequestMapping( value = "/deletebyid", method = RequestMethod.GET )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteById(
            @RequestParam( "id" ) Integer id,
            Model model
    ){
        userRepository.deleteById( id );

        return "redirect:all";
    }

    /*
        EDIT USER BY ID
     */

    @RequestMapping( value = "/edituser", method = RequestMethod.GET )
    @PreAuthorize( "hasRole('ROLE_ADMIN')" )
    public String editUser(
            @RequestParam( "id" ) Integer id,
            Model model
    ){
        model.addAttribute( "user", userRepository.searchById( id ) );

        return "user/editUser";
    }

    @RequestMapping( value = "/edituser", method = RequestMethod.POST )
    @PreAuthorize( "hasRole('ROLE_ADMIN')" )
    public String editUser(
            @Valid @ModelAttribute( "user" ) User user,
            BindingResult errors,
            Model model
    ){
        System.out.println( errors );
        if( errors.hasErrors() ) return "error";

        userRepository.save( user );

        return "redirect:all";
    }
}
