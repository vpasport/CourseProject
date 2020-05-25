package ru.isu.CourseProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.isu.CourseProject.model.Category;
import ru.isu.CourseProject.model.Executors;
import ru.isu.CourseProject.model.Order;
import ru.isu.CourseProject.model.User;
import ru.isu.CourseProject.repository.CategoryRepository;
import ru.isu.CourseProject.repository.ExecutorsRepository;
import ru.isu.CourseProject.repository.OrderRepository;
import ru.isu.CourseProject.repository.UserRepository;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

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
    public String getAll( Model model ){
        model.addAttribute( "orders", orderRepository.getAll() );

        return "orders/orders";
    }

    @CrossOrigin
    @RequestMapping( value = "/allJson", method =  RequestMethod.GET )
    public @ResponseBody List<Order> getAllJson(){
        return orderRepository.getAll();
    }

    /*
        CREATE ORDER
     */

    @RequestMapping( value = "/create", method = RequestMethod.GET )
    public String create( Model model ){
        Order order = new Order();
        model.addAttribute( "order", order );
        return "orders/createOrder";
    }

    @ModelAttribute( "customers" )
    public List<User> getCustomers(){
        return userRepository.getAllCustomers();
    }

    @CrossOrigin
    @RequestMapping( value = "/usersJson", method = RequestMethod.GET)
    public @ResponseBody List<Integer> getRolesJSON(){
        return userRepository.getAllId();
    }

    @ModelAttribute( "categories" )
    public List<Category> getCategories(){
        return categoryRepository.getAll();
    }

    @CrossOrigin
    @RequestMapping( value = "/categoriesnameJson", method = RequestMethod.GET )
    public List<String> getCategoriesNamesJson(){
        return  categoryRepository.getAllNames();
    }

    @RequestMapping( value = "/create", method = RequestMethod.POST )
    public String create(
            @Valid @ModelAttribute( "order" ) Order order,
            BindingResult errors, Model model
    ){
        System.out.println( order.getDeadline() );
        System.out.println( order );
        System.out.println( errors );
        if( errors.hasErrors() ) return "/error";

        order.setDate( LocalDate.now() );

        orderRepository.save( order );

        return "redirect:all";
    }

    /*
        GET ORDER BY ID
     */

    @RequestMapping( value = "/getById", method = RequestMethod.GET )
    public String getById( @RequestParam( "id" ) Integer id,  Model model ){
        model.addAttribute( "order", orderRepository.searchById( id ) );
        model.addAttribute( "executors", executorsRepository.getAllExecutorsByOrderId( id ) );
        return "orders/order";
    }

    @CrossOrigin
    @RequestMapping( value = "/getByIdJson", method = RequestMethod.GET )
    public @ResponseBody Order getByIdJson( @RequestParam( "id" ) Integer id ){
        return orderRepository.searchById( id );
    }

    /*
        ADD ALL EXECUTORS TABLE
     */

    @RequestMapping( value = "/executors", method = RequestMethod.GET )
    public String allExecutors( Model model ){
        model.addAttribute( "executors", orderRepository.getAllExecutors() );
        return "orders/executors";
    }

    /*
        ADD EXECUTOR BY ID
     */

    @RequestMapping( value = "/addexecutor", method = RequestMethod.GET )
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
        System.out.println( userRepository.getAllExecutors() );
        return userRepository.getAllExecutors();
    }

    @RequestMapping( value = "/addexecutor", method = RequestMethod.POST )
    public String addExecutor(
            @Valid @ModelAttribute( "executors" ) Executors executors,
            BindingResult errors, Model model
    ){
        if( errors.hasErrors() ) return "/error";

        executorsRepository.save( executors );

        return "redirect:executors";
    }

    /*
        SELECT FINAL EXECUTOR
     */

    @RequestMapping( value = "/selectexecutor", method = RequestMethod.GET )
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

        return "orders/orders";
    }
}
