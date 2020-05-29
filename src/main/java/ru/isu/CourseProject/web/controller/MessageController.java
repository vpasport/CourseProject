package ru.isu.CourseProject.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.isu.CourseProject.domain.model.Message;
import ru.isu.CourseProject.domain.model.User;
import ru.isu.CourseProject.domain.repository.MessageRepository;
import ru.isu.CourseProject.domain.repository.UserRepository;
import ru.isu.CourseProject.web.ConvertToJson;
import ru.isu.CourseProject.web.RoleChecker;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping( "/messages" )
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    /*
        ALL MESSAGES
     */

    @RequestMapping( value = "/all", method = RequestMethod.GET )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String all( Model model ){
        model.addAttribute( "messages", messageRepository.getAll() );
        return "messages/messages";
    }

    @RequestMapping( value = "/allJson", method = RequestMethod.GET )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public @ResponseBody List<Message> allJson(){
        return messageRepository.getAll();
    }

    /*
        CREATE MESSAGES
     */

    @RequestMapping( value = "/create", method = RequestMethod.POST )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String create(
            @Valid @ModelAttribute( "message" ) Message message,
            @RequestParam( "to" ) Integer toid,
            @RequestParam( "from" ) Integer fromid,
            BindingResult errors,
            Model model
    ){
        if( errors.hasErrors() ) {
            System.out.println( errors );
            return "/error";
        }

        message.setTo( userRepository.searchById( toid ) );
        message.setFrom( userRepository.searchById( fromid ) );
        message.setDate( LocalDate.now() );
        message.setTime( LocalTime.now() );
        System.out.println( message );

        messageRepository.save( message );

        return "redirect:all";
    }

    @CrossOrigin
    @RequestMapping( value = "/createJson", method = RequestMethod.POST )
    public @ResponseBody String createJson(
        @RequestParam( "to" ) Integer from,
        @RequestParam( "text" ) String text,
        @RequestParam( "token" ) User user
    ){
        String check = RoleChecker.check( user );
        if( ! check.equals( "" ) ) return check;

        Message message = new Message();

        message.setTo( userRepository.searchById( from ) );
        message.setFrom( userRepository.searchById( user.getId() ) );
        message.setText( text );
        message.setDate( LocalDate.now() );
        message.setTime( LocalTime.now() );

        messageRepository.save( message );

        return "{ \"status\" : \"ok\" }";
    }

    @RequestMapping( value = "/create", method = RequestMethod.GET )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String create( Model model ){
        Message message = new Message();
        model.addAttribute( "message", message );
        return "messages/createMessage";
    }

    @ModelAttribute( "users" )
    public List<User> getUsersId(){
        return userRepository.getAll();
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

    /*
        GET MESSAGE BY ID
     */

    @RequestMapping( value = "/message", method = RequestMethod.GET )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getById( @RequestParam( "id" ) Integer id, Model model ){
        model.addAttribute( "message", messageRepository.getMessageById( id ) );

        return "messages/message";
    }

    @CrossOrigin
    @RequestMapping( value = "/messageJson", method = RequestMethod.GET )
    public @ResponseBody String getByIdJson(
            @RequestParam( "id" ) Integer id,
            @RequestParam( "token" ) User user
    ) throws JsonProcessingException {
        String check = RoleChecker.check( user );
        if( ! check.equals( "" ) ) return check;

        return ConvertToJson.convert( messageRepository.getMessageById( id ) );
    }

    /*
        GET MESSAGE BY 2 ID
     */

    @RequestMapping( value = "/message2id", method = RequestMethod.GET )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getBy2Id(
            @RequestParam( "id1" ) Integer id1,
            @RequestParam( "id2" ) Integer id2,
            Model model
    ){
        List<Message> res = messageRepository.getMessageBy2Id( id1, id2 );
        if( id1 != id2 ) res.addAll( messageRepository.getMessageBy2Id( id2, id1 ) );

        model.addAttribute( "messages", res );

        Collections.sort( res );

        return "messages/messages";
    }

    @CrossOrigin
    @RequestMapping( value = "/message2idJson", method = RequestMethod.GET )
    public @ResponseBody String getById2Json(
            @RequestParam( "id1" ) Integer id1,
            @RequestParam( "id2" ) Integer id2,
            @RequestParam( "token" ) User user
    ) throws JsonProcessingException {
        String check = RoleChecker.check( user );
        if( ! check.equals( "" ) ) return check;

        List<Message> res = messageRepository.getMessageBy2Id( id1, id2 );
        if( id1 != id2 ) res.addAll( messageRepository.getMessageBy2Id( id2, id1 ) );

        Collections.sort( res );

        return ConvertToJson.convert( res );
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
        messageRepository.deleteById( id );

        return "redirect:all";
    }

    /*
        EDIT MESSAGE BY ID
     */

    @RequestMapping( value = "/editmessage", method = RequestMethod.GET )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editMessage(
            @RequestParam( "id" ) Integer id,
            Model model
    ){
        model.addAttribute( "message", messageRepository.getMessageById( id ) );

        return "messages/editMessage";
    }

    @RequestMapping( value = "/editmessage", method = RequestMethod.POST )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editMessage(
            @Valid @ModelAttribute( "massage" ) Message message,
            BindingResult errors,
            Model model
    ){
        if( errors.hasErrors() ) return "error";

        messageRepository.save( message );

        return "redirect:all";
    }
}
