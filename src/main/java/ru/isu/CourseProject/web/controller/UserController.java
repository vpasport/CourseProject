package ru.isu.CourseProject.web.controller;

import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import ru.isu.CourseProject.domain.model.User;
import ru.isu.CourseProject.domain.repository.UserRepository;
import ru.isu.CourseProject.web.CustomAuthenticationProvider;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Formatter;
import java.util.List;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

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
    @PreAuthorize("hasAnyRole( 'ROLE_ADMIN', 'ROLE_CUSTOMER', 'ROLE_EXECUTOR' )")
    public @ResponseBody List<String> getRolesJSON(){
        return Arrays.asList( "Executor", "Customer", "Admin" );
    }

    @ModelAttribute( "roles" )
    public List<String> getRoles(){
        return Arrays.asList( "ROLE_EXECUTOR", "ROLE_CUSTOMER", "ROLE_ADMIN" );
    }

    @CrossOrigin
    @RequestMapping( value = "/jsonSex", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole( 'ROLE_ADMIN', 'ROLE_CUSTOMER', 'ROLE_EXECUTOR' )")
    public @ResponseBody List<String> getSexJSON(){
        return Arrays.asList( "Male", "Female", "Other" );
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
    ){
        System.out.println( user );
        if( errors.hasErrors() ) return "/error";

        user.setLastActivity( LocalDate.now() );
        user.setRating( 0. );
        System.out.println( user );

        userRepository.save( user );

        return "redirect:all";
    }

    @CrossOrigin
    @RequestMapping( value = "/createJson", method = RequestMethod.POST )
    @PreAuthorize("hasAnyRole( 'ROLE_ADMIN', 'ROLE_CUSTOMER', 'ROLE_EXECUTOR' )")
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
    ){
        User user = new User();

        System.out.println( login );
        if( userRepository.getByLogin( login ) == null ) user.setLogin( login );
        else return "{ status : false, error : 'login' }";
        user.setFirstName( firstName );
        user.setSecondName( secondName );
        user.setPassword( password );
        user.setEmail( email );
        user.setRole( role );
        user.setPhone( phone );
        user.setSex( sex );
        if( age >= 18 ) user.setAge( age );
        else return "{ status : false, error : 'age' }";
        user.setSpecialty( specialty );
        user.setLastActivity( LocalDate.now() );
        user.setRating( 0. );

        System.out.println( user );

        userRepository.save(user);

        return String.format( "{ status : ok, id : %s }", userRepository.getByLogin( login ).getId() );
    }

    /*
        ALL USERS
     */

    @CrossOrigin(  )
    @RequestMapping( value = "/allJson", method = RequestMethod.GET )
    @PreAuthorize("hasAnyRole( 'ROLE_ADMIN', 'ROLE_CUSTOMER', 'ROLE_EXECUTOR' )")
    public @ResponseBody List<User> allJSON(){
        List<User> users = userRepository.getAll();
        for( User user : users ) user.setPassword( null );
        return users;
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
    @PreAuthorize("hasAnyRole( 'ROLE_ADMIN', 'ROLE_CUSTOMER', 'ROLE_EXECUTOR' )")
    public @ResponseBody List<User> getAllExecutorsJson(){
        List<User> users = userRepository.getAllByRole( "ROLE_EXECUTOR" );
        for( User user : users ) user.setPassword( null );
        return users;
    }

    /*
        GET ALL CUSTOMER
     */

    @CrossOrigin
    @RequestMapping( value = "/getallcustomerJson", method = RequestMethod.GET )
    @PreAuthorize("hasAnyRole( 'ROLE_ADMIN', 'ROLE_CUSTOMER', 'ROLE_EXECUTOR' )")
    public @ResponseBody List<User> getAllCustomerJson(){
        List<User> users = userRepository.getAllByRole( "ROLE_CUSTOMER" );
        for( User user : users ) user.setPassword( null );
        return userRepository.getAllByRole( "ROLE_CUSTOMER" );
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
    @PreAuthorize("hasAnyRole( 'ROLE_ADMIN', 'ROLE_CUSTOMER', 'ROLE_EXECUTOR' )")
    public @ResponseBody User getByIdJson( @RequestParam( "id" ) Integer id ){
        User user = userRepository.searchById( id );
        user.setPassword( null );
        return user;
    }

    /*
        AUTH USER
     */

    @CrossOrigin
    @RequestMapping( value = "/auth", method = RequestMethod.GET )
    public @ResponseBody String authUser(
            @RequestParam( "login" ) String login,
            @RequestParam( "password" ) String password,
            HttpServletRequest req
    ) throws NoSuchAlgorithmException {
        System.out.println( login );

        String pass = getHash( password );
        User user = userRepository.getByLogin( login );
//        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(login, password);
//        Authentication auth = authManager.authenticate(authReq);
//        SecurityContext sc = SecurityContextHolder.getContext();
//        sc.setAuthentication(auth);
//
        if( user != null && user.getPassword().equals( pass ) ){
            Authentication auth  = new UsernamePasswordAuthenticationToken(user,user.getPassword(), user.getAuthorities());

            SecurityContext sc = SecurityContextHolder.getContext();
            sc.setAuthentication(auth);
            HttpSession session = req.getSession(true);
            session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, sc);
//            Authentication authentication = new UsernamePasswordAuthenticationToken(
//                user,
//                user.getPassword(),
//                user.getAuthorities()
//            );
//            SecurityContextHolder.getContext().setAuthentication( authentication );
//            System.out.println( SecurityContextHolder.getContext().getAuthentication() );
//        return String.format( "{ \"JSESSIONID\" : \"%s\" }", session.getAttribute( SPRING_SECURITY_CONTEXT_KEY ) );
            return String.format( "{ \"JSESSIONID\" : \"%s\" }", RequestContextHolder.currentRequestAttributes().getSessionId() );
        }

        return "{ \"status\" : \"error\" }";
    }

    public static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        Formatter formatter = new Formatter(sb);
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }
        return sb.toString();
    }

    public static String getHash(String pass) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        return bytesToHexString(md.digest(pass.getBytes()));
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
}
