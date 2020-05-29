package ru.isu.CourseProject.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.isu.CourseProject.domain.model.Category;
import ru.isu.CourseProject.domain.model.Order;
import ru.isu.CourseProject.domain.model.User;
import ru.isu.CourseProject.domain.repository.CategoryRepository;
import ru.isu.CourseProject.domain.repository.ExecutorsRepository;
import ru.isu.CourseProject.domain.repository.OrderRepository;
import ru.isu.CourseProject.domain.repository.UserRepository;
import ru.isu.CourseProject.web.ConvertToJson;
import ru.isu.CourseProject.web.RoleChecker;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

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
    public String getAll(Model model, Pageable page ){
        Page<Order> orders = orderRepository.getAll( page );
        System.out.println( orders );
        model.addAttribute( "orders", orders );

        return "orders/orders";
    }

    @CrossOrigin
    @RequestMapping( value = "/allJson", method =  RequestMethod.GET )
    public @ResponseBody String getAllJson(
            @RequestParam( "token" ) User user
    ) throws JsonProcessingException {
        String check = RoleChecker.check( user );
        if( ! check.equals( "" ) ) return check;

        return ConvertToJson.convert( orderRepository.getAll() );
    }

    @CrossOrigin
    @RequestMapping( value = "/getbytypeJson", method = RequestMethod.GET )
    public @ResponseBody String getAllByTypeJson(
            @RequestParam( "type" ) String type,
            @RequestParam( "token" ) User user
    ) throws JsonProcessingException {
        String check = RoleChecker.check( user );
        if( ! check.equals( "" ) ) return check;

        return ConvertToJson.convert( orderRepository.getByType( type ) );
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

    @ModelAttribute( "statuses" )
    public List<String> getStats(){
        return Arrays.asList( "start", "executorsEnd", "end" );
    }

    @CrossOrigin
    @RequestMapping( value = "/getstatusesJson", method = RequestMethod.GET )
    public @ResponseBody String getStatusesJson() throws JsonProcessingException {
        return ConvertToJson.convert( Arrays.asList( "start", "executorsEnd", "end" ) );
    }

    @CrossOrigin
    @RequestMapping( value = "/setstatusJson", method = RequestMethod.POST )
    public @ResponseBody String setStatusJson(
            @RequestParam( "order" ) Integer orderId,
            @RequestParam( "status" ) String status,
            @RequestParam( "token" ) User user
    ){
        String check = RoleChecker.check( user );
        if( ! check.equals( "" ) ) return check;

        Order order = orderRepository.searchById( orderId );
        order.setStatus( status );
        orderRepository.save( order );

        return "{ \"status\" : \"ok\" }";
    }

    @CrossOrigin
    @RequestMapping( value = "/getorderswithstatusforexecutorJson", method = RequestMethod.GET )
    public @ResponseBody String getOrdersWithStatusForExecutorJson(
            @RequestParam( "status" ) String status,
            @RequestParam( "token" ) User user
    ) throws JsonProcessingException {
        String check = RoleChecker.check( user );
        if( ! check.equals( "" ) ) return check;

        return ConvertToJson.convert( orderRepository.getOrdersWithStatusForExecutor( user.getId(), status ) );
    }

    @CrossOrigin
    @RequestMapping( value = "/getordersbytypewithstatusnullJson", method = RequestMethod.GET )
    public @ResponseBody String getOrdersByType(
            @RequestParam( "type" ) String type,
            @RequestParam( "token" ) User user
    ) throws JsonProcessingException {
        String check = RoleChecker.check( user );
        if( ! check.equals( "" ) ) return check;

        return ConvertToJson.convert( orderRepository.getOrdersByTypeWithStatusNull( type ) );
    }

    @CrossOrigin
    @RequestMapping( value = "/getordersbyexecutoridwithstatusNotnullJson", method = RequestMethod.GET )
    public @ResponseBody String getOrdersByExecutorIdWithStatusNotNullJson(
            @RequestParam( "token" ) User user
    ) throws JsonProcessingException {
        String check = RoleChecker.check( user );
        if( ! check.equals( "" ) ) return check;

        return ConvertToJson.convert( orderRepository.getOrdersByExecutorIdWithStatusNotNull( user.getId() ) );
    }

    @CrossOrigin
    @RequestMapping( value = "/getordersbyexecutoridandtypewithstatusnullJson", method = RequestMethod.GET )
    public @ResponseBody String getOrdersByExecutorIdAndTypeWithStatusNullJson(
            @RequestParam( "type" ) String type,
            @RequestParam( "token" ) User user
    ) throws JsonProcessingException {
        String check = RoleChecker.check( user );
        if( ! check.equals( "" ) ) return check;

        return ConvertToJson.convert( orderRepository.getOrdersByExecutorIdAndTypeWithStatusNull( type, user.getId() ) );
    }

    @ModelAttribute( "types" )
    public List<String> getTypes(){
        return Arrays.asList( "Exclusive", "Open" );
    }

    @CrossOrigin
    @RequestMapping( value = "/usersJson", method = RequestMethod.GET)
    public @ResponseBody String getRolesJSON(
            @RequestParam( "token" ) User user
    ) throws JsonProcessingException {
        String check = RoleChecker.check( user );
        if( ! check.equals( "" ) ) return check;

        return ConvertToJson.convert( userRepository.getAllId() );
    }

    @ModelAttribute( "categories" )
    public List<Category> getCategories(){
        return categoryRepository.getAll();
    }

    @CrossOrigin
    @RequestMapping( value = "/categoriesnameJson", method = RequestMethod.GET )
    public String getCategoriesNamesJson(
            @RequestParam( "token" ) User user
    ) throws JsonProcessingException {
        String check = RoleChecker.check( user );
        if( ! check.equals( "" ) ) return check;

        return ConvertToJson.convert( categoryRepository.getAllNames() );
    }

    @RequestMapping( value = "/create", method = RequestMethod.POST )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String create(
            @Valid @ModelAttribute( "order" ) Order order,
            @RequestParam( "customer" ) Integer customer,
            BindingResult errors, Model model
    ){
        if( errors.hasErrors() ) return "/error";

        order.setCustomer( userRepository.searchById( customer ) );
        order.setDate( LocalDate.now() );

        orderRepository.save( order );

        return "redirect:all";
    }

    @CrossOrigin
    @RequestMapping( value = "/createJson", method = RequestMethod.POST )
    public @ResponseBody String createJson(
            @RequestParam( "name" ) String name,
            @RequestParam( "description" ) String description,
            @RequestParam( "deadline" ) LocalDate deadLine,
            @RequestParam( "categoryid" ) Category category,
            @RequestParam( "maxprice" ) Double maxPrice,
            @RequestParam( "token" ) User user
    ){
        String check = RoleChecker.check( user );
        if( ! check.equals( "" ) ) return check;

        Order order = new Order();

        order.setName( name );
        order.setDescription( description );
        order.setDate( LocalDate.now() );
        order.setDeadline( deadLine );
        order.setCategory( category );
        order.setCustomer( user );
        order.setMaxPrice( maxPrice );
        order.setType( "Open" );

        orderRepository.save( order );

        return String.format( "{ \"status\" : \"ok\", \"id\" : \"%s\" }", orderRepository.getMaxId() );
    }

    @CrossOrigin
    @RequestMapping( value = "/createexclusiveJson", method = RequestMethod.POST )
    public @ResponseBody String createExclusiveJson(
            @RequestParam( "name" ) String name,
            @RequestParam( "description" ) String description,
            @RequestParam( "deadline" ) LocalDate deadLine,
            @RequestParam( "categoryid" ) Category category,
            @RequestParam( "maxprice" ) Double maxPrice,
            @RequestParam( "finaluserid" ) Integer executor,
            @RequestParam( "token" ) User user
    ){
        String check = RoleChecker.check( user );
        if( ! check.equals( "" ) ) return check;

        Order order = new Order();

        order.setName( name );
        order.setDescription( description );
        order.setDate( LocalDate.now() );
        order.setDeadline( deadLine );
        order.setCategory( category );
        order.setCustomer( user );
        order.setMaxPrice( maxPrice );
        order.setFinalExecutor( userRepository.searchById( executor ) );
        order.setType( "Exclusive" );

        orderRepository.save( order );

        return String.format( "{ \"status\" : \"ok\", \"id\" : \"%s\" }", orderRepository.getMaxId() );
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
    public @ResponseBody String getByIdJson(
            @RequestParam( "id" ) Integer id,
            @RequestParam( "token" ) User user
    ) throws JsonProcessingException {
        String check = RoleChecker.check( user );
        if( ! check.equals( "" ) ) return check;

        return ConvertToJson.convert( orderRepository.searchById( id ) );
    }

    @CrossOrigin
    @RequestMapping( value = "/getexecutorsbyorderidJson", method = RequestMethod.GET )
    public @ResponseBody String getExecutorsByOrderId(
            @RequestParam( "orderid" ) Integer orderId,
            @RequestParam( "token" ) User user
    ) throws JsonProcessingException {
        String check = RoleChecker.check( user );
        if( ! check.equals( "" ) ) return check;

        List<User> users = executorsRepository.getAllExecutorsByOrderId( orderId );
        for( User usr : users ) usr.setPassword( null );

        return ConvertToJson.convert( users );
    }

    /*
        ADD ALL EXECUTORS TABLE
     */

    @RequestMapping( value = "/executors", method = RequestMethod.GET )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String allExecutors( Model model ){
        model.addAttribute( "orders", orderRepository.getAll() );

        return "orders/executors";
    }

    @CrossOrigin
    @RequestMapping( value = "/executorsJson", method = RequestMethod.GET )
    public @ResponseBody String allExecutorsJson(
            @RequestParam( "token" ) User user
    ) throws JsonProcessingException {
        String check = RoleChecker.check( user );
        if( ! check.equals( "" ) ) return check;

        return ConvertToJson.convert( orderRepository.getAllExecutors() );
    }

    /*
        ADD EXECUTOR BY ID
     */

    @RequestMapping( value = "/addexecutor", method = RequestMethod.GET )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String createExecutor( Model model ){;
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
            @RequestParam( "order" ) Integer orId,
            @RequestParam( "executor" ) Integer exId,
            Model model
    ){
        Order order = orderRepository.searchById( orId );
        Set<User> executors = order.getExecutors();
        executors.add( userRepository.searchById( exId ) );

        orderRepository.save( order );

        return "redirect:executors";
    }

    @CrossOrigin
    @RequestMapping( value = "/addexecutorJson", method = RequestMethod.POST )
    public @ResponseBody String addExecutorJson(
            @RequestParam( "order" ) Integer orId,
            @RequestParam( "executor" ) Integer execId,
            @RequestParam( "token" ) User user
    ){
        String check = RoleChecker.check( user );
        if( ! check.equals( "" ) ) return check;

        Order order = orderRepository.searchById( orId );
        Set<User> executors = order.getExecutors();
        executors.add( userRepository.searchById( execId ) );

        orderRepository.save( order );

        return "{ \"status\" : \"ok\" }";
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

        return "orders/selectExecutor";
    }

    @RequestMapping( value = "/selectexecutor", method = RequestMethod.POST )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String selectFinalExecutor(
            @RequestParam( "user" ) Integer uId,
            @RequestParam( "order" ) Integer orId
    ){
        Order order = orderRepository.searchById( orId );
        order.setFinalExecutor( userRepository.searchById( uId ) );
        orderRepository.save( order );
        return "redirect:all";
    }

    @CrossOrigin
    @RequestMapping( value = "/selectexecutorJson", method = RequestMethod.POST )
    public @ResponseBody String selectFinalExecutorJson(
            @RequestParam( "order" ) Integer orderId,
            @RequestParam( "user" ) Integer userId,
            @RequestParam( "token" ) User user
    ){
        String check = RoleChecker.check( user );
        if( ! check.equals( "" ) ) return check;

        Order order = orderRepository.searchById( orderId );
        order.setFinalExecutor( userRepository.searchById( userId ) );
        orderRepository.save( order );

        return "{ \"status\" : \"ok\" }";
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

    @RequestMapping( value = "/edit", method = RequestMethod.POST )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String edit(
            @Valid @ModelAttribute( "order" ) Order order,
            @RequestParam( "customer" ) Integer customer,
            BindingResult errors, Model model
    ){
        if( errors.hasErrors() ) return "/error";

        order.setCustomer( userRepository.searchById( customer ) );
        order.setDate( LocalDate.now() );

        orderRepository.save( order );

        return "redirect:all";
    }

    /*
        GET ORDER BY CUSTOMER ID
     */

    @CrossOrigin
    @RequestMapping( value = "/getorderbycustomeridJson", method = RequestMethod.GET )
    public @ResponseBody String getOrderByCustomerId(
            @RequestParam( "id" ) Integer id,
            @RequestParam( "token" ) User user
    ) throws JsonProcessingException {
        String check = RoleChecker.check( user );
        if( ! check.equals( "" ) ) return check;

        return ConvertToJson.convert( orderRepository.getByCustomerId( id ) );
    }
}
