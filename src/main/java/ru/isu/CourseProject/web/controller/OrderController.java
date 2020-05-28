package ru.isu.CourseProject.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.isu.CourseProject.domain.model.Category;
import ru.isu.CourseProject.domain.model.Executors;
import ru.isu.CourseProject.domain.model.Order;
import ru.isu.CourseProject.domain.model.User;
import ru.isu.CourseProject.domain.repository.CategoryRepository;
import ru.isu.CourseProject.domain.repository.ExecutorsRepository;
import ru.isu.CourseProject.domain.repository.OrderRepository;
import ru.isu.CourseProject.domain.repository.UserRepository;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Controller
@RequestMapping( "/orders" )
public class OrderController {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ExecutorsRepository executorsRepository;

    /*
        GET ALL ORDERS
     */

    @RequestMapping( value = "/all", method = RequestMethod.GET )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getAll( Model model ){
        model.addAttribute( "orders", orderRepository.getAll() );

        return "orders/orders";
    }

    @CrossOrigin
    @RequestMapping( value = "/allJson", method =  RequestMethod.GET )
    @PreAuthorize("hasAnyRole( 'ROLE_ADMIN', 'ROLE_CUSTOMER', 'ROLE_EXECUTOR' )")
    public @ResponseBody List<Order> getAllJson(){
        return orderRepository.getAll();
    }

    /*
        CREATE ORDER
     */

    @RequestMapping( value = "/create", method = RequestMethod.GET )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String create( Model model ){
        Order order = new Order();
        model.addAttribute( "order", order );
        return "orders/createOrder";
    }

    @ModelAttribute( "customers" )
    public List<User> getCustomers(){
        List<User> users = userRepository.getAllByRole( "ROLE_CUSTOMER" );
        for( User user : users ) user.setPassword( null );
        return users;
    }

    @CrossOrigin
    @RequestMapping( value = "/usersJson", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole( 'ROLE_ADMIN', 'ROLE_CUSTOMER', 'ROLE_EXECUTOR' )")
    public @ResponseBody List<Integer> getRolesJSON(){
        return userRepository.getAllId();
    }

    @ModelAttribute( "categories" )
    public List<Category> getCategories(){
        return categoryRepository.getAll();
    }

    @CrossOrigin
    @RequestMapping( value = "/categoriesnameJson", method = RequestMethod.GET )
    @PreAuthorize("hasAnyRole( 'ROLE_ADMIN', 'ROLE_CUSTOMER', 'ROLE_EXECUTOR' )")
    public List<String> getCategoriesNamesJson(){
        return  categoryRepository.getAllNames();
    }

    @RequestMapping( value = "/create", method = RequestMethod.POST )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String create(
            @ModelAttribute( "order" ) Order order,
            BindingResult errors, Model model
    ){
        if( errors.hasErrors() ) return "/error";

        order.setDate( LocalDate.now() );

        orderRepository.save( order );

        return "redirect:all";
    }

    /*
        GET ORDER BY ID
     */

    @RequestMapping( value = "/getById", method = RequestMethod.GET )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getById( @RequestParam( "id" ) Integer id,  Model model ){
        model.addAttribute( "order", orderRepository.searchById( id ) );
        model.addAttribute( "executors", executorsRepository.getAllExecutorsByOrderId( id ) );
        return "orders/order";
    }

    @CrossOrigin
    @RequestMapping( value = "/getByIdJson", method = RequestMethod.GET )
    @PreAuthorize("hasAnyRole( 'ROLE_ADMIN', 'ROLE_CUSTOMER', 'ROLE_EXECUTOR' )")
    public @ResponseBody Order getByIdJson( @RequestParam( "id" ) Integer id ){
        return orderRepository.searchById( id );
    }

    @CrossOrigin
    @RequestMapping( value = "/getexecutorsbyorderidJson", method = RequestMethod.GET )
    @PreAuthorize("hasAnyRole( 'ROLE_ADMIN', 'ROLE_CUSTOMER', 'ROLE_EXECUTOR' )")
    public @ResponseBody List<User> getExecutorsByOrderId( @RequestParam( "orderid" ) Integer orderId ){
        List<User> users = executorsRepository.getAllExecutorsByOrderId( orderId );
        for( User user : users ) user.setPassword( null );
        return users;
    }

    /*
        ADD ALL EXECUTORS TABLE
     */

    @RequestMapping( value = "/executors", method = RequestMethod.GET )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String allExecutors( Model model ){
        model.addAttribute( "executors", orderRepository.getAllExecutors() );
        return "orders/executors";
    }

    @CrossOrigin
    @RequestMapping( value = "/executorsJson", method = RequestMethod.GET )
    @PreAuthorize("hasAnyRole( 'ROLE_ADMIN', 'ROLE_CUSTOMER', 'ROLE_EXECUTOR' )")
    public @ResponseBody List<Executors> allExecutorsJson(){
        return orderRepository.getAllExecutors();
    }

    /*
        ADD EXECUTOR BY ID
     */

    @RequestMapping( value = "/addexecutor", method = RequestMethod.GET )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String createExecutor( Model model ){
        Executors executors = new Executors();
        model.addAttribute( "executors", executors );

        return "orders/addExecutors";
    }

    @ModelAttribute( "orders" )
    public List<Order> getAllOrders(){
        return orderRepository.getAll();
    }

    @ModelAttribute( "execs" )
    public List<User> getAllExecutor(){
        List<User> users = userRepository.getAllByRole( "ROLE_EXECUTOR" );
        for( User user : users ) user.setPassword( null );
        return users;
    }

    @RequestMapping( value = "/addexecutor", method = RequestMethod.POST )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addExecutor(
            @ModelAttribute( "executors" ) Executors executors,
            BindingResult errors, Model model
    ){
        System.out.println( errors );
        if( errors.hasErrors() ) return "/error";

        executorsRepository.save( executors );

        return "redirect:executors";
    }

    @CrossOrigin
    @RequestMapping( value = "/addexecutorJson", method = RequestMethod.POST )
    @PreAuthorize("hasAnyRole( 'ROLE_ADMIN', 'ROLE_CUSTOMER', 'ROLE_EXECUTOR' )")
    public @ResponseBody String addExecutorJson(
            @RequestParam( "orderid" ) Integer orderId,
            @RequestParam( "executorid" ) Integer executorId
    ){
//        Executors executors = new Executors();
//        executors.setOrder( orderRepository.searchById( orderId ) );
//        Set<User> users = new TreeSet<User>();
//        users.add( userRepository.searchById( executorId ) );
//        executors.setExecutors( users );
//
//        executorsRepository.save( executors );
//
        return "{ status : ok }";
    }

    /*
        SELECT FINAL EXECUTOR
     */

    @RequestMapping( value = "/selectexecutor", method = RequestMethod.GET )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String selectFinalExecutor(
            @RequestParam( "id" ) Integer id,
            Model model
    ){
        model.addAttribute( "order", orderRepository.searchById( id ) );
        model.addAttribute( "executors", executorsRepository.getAllExecutorsByOrderId( id ) );
        model.addAttribute( "user", new User() );

        return "orders/selectExecutor";
    }

    @RequestMapping( value = "/selectexecutor", method = RequestMethod.POST )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String selectFinalExecutor(
            @RequestParam( "id" ) Integer id,
            @ModelAttribute( "user" ) User user,
            BindingResult errors, Model model
    ){
        if( errors.hasErrors() ) return "error";

        Order order = orderRepository.searchById( id );
        User executor = userRepository.getByLogin( user.getLogin() );

        order.setFinalExecutor( executor );

        orderRepository.save( order );

        return "redirect:all";
    }

    @CrossOrigin
    @RequestMapping( value = "/selectexecutorJson", method = RequestMethod.POST )
    @PreAuthorize("hasAnyRole( 'ROLE_ADMIN', 'ROLE_CUSTOMER', 'ROLE_EXECUTOR' )")
    public @ResponseBody String selectFinalExecutor(
            @RequestParam( "orderid" ) Integer orderId,
            @RequestParam( "userid" ) Integer userId
    ){
        Order order = orderRepository.searchById( orderId );
        order.setFinalExecutor( userRepository.searchById( userId ) );
        orderRepository.save( order );
        return "{ status : ok }";
    }


    /*
        EDIT ORDER BY ID
     */

    @RequestMapping( value = "/editorder", method = RequestMethod.GET )
    public String editById(
            @RequestParam( "id" ) Integer id,
            Model model
    ){
        model.addAttribute( "order", orderRepository.searchById( id ) );
        model.addAttribute( "executors", executorsRepository.getAllExecutorsByOrderId( id ) );

        return "orders/editOrder";
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
        orderRepository.deleteById( id );

        return "redirect:all";
    }
}