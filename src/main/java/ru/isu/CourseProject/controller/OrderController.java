package ru.isu.CourseProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.isu.CourseProject.model.Category;
import ru.isu.CourseProject.model.Order;
import ru.isu.CourseProject.model.User;
import ru.isu.CourseProject.repository.CategoryRepository;
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

    @ModelAttribute( "customer" )
    public List<Integer> getCustomers(){
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
        if( errors.hasErrors() ) return "/index";

        order.setDate( LocalDate.now() );

        orderRepository.save( order );

        return "redirect:all";
    }
}
