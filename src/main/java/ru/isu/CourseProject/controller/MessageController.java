package ru.isu.CourseProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.isu.CourseProject.model.Message;
import ru.isu.CourseProject.model.User;
import ru.isu.CourseProject.repository.MessageRepository;
import ru.isu.CourseProject.repository.UserRepository;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
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
    public String all( Model model ){
        model.addAttribute( "messages", messageRepository.getAll() );
        return "messages/messages";
    }

    @RequestMapping( value = "/allJson", method = RequestMethod.GET )
    public @ResponseBody List<Message> allJson(){
        return messageRepository.getAll();
    }

    /*
        CREATE MESSAGES
     */

    @RequestMapping( value = "/create", method = RequestMethod.POST )
    public String create(
            @Valid @ModelAttribute( "message" ) Message message,
            BindingResult errors,
            Model model
    ){
        if( errors.hasErrors() ) {
            System.out.println( errors );
            return "/error";
        }

        message.setDate( LocalDate.now() );
        message.setTime( LocalTime.now() );
        System.out.println( message );

        messageRepository.save( message );

        return "redirect:all";
    }

    @CrossOrigin
    @RequestMapping( value = "/createJson", method = RequestMethod.POST )
    public @ResponseBody String createJson(
        @RequestParam( "from" ) Integer from,
        @RequestParam( "to" ) Integer to,
        @RequestParam( "text" ) String text
    ){
        Message message = new Message();

//        message.setFrom( userRepository.searchById( from ) );

        return "{ status : ok }";
    }

    @RequestMapping( value = "/create", method = RequestMethod.GET )
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
    public @ResponseBody List<Integer> getRolesJSON(){
        return userRepository.getAllId();
    }

    /*
        GET MESSAGE BY ID
     */

    @RequestMapping( value = "/message", method = RequestMethod.GET )
    public String getById( @RequestParam( "id" ) Integer id, Model model ){
        model.addAttribute( "message", messageRepository.getMessageById( id ) );

        return "messages/message";
    }

    @CrossOrigin
    @RequestMapping( value = "/messageJson", method = RequestMethod.GET )
    public @ResponseBody List<Message> getByIdJson( @RequestParam( "id" ) Integer id ){
        return Arrays.asList( messageRepository.getMessageById( id ) );
    }

    /*
        GET MESSAGE BY 2 ID
     */

    @RequestMapping( value = "/message2id", method = RequestMethod.GET )
    public String getBy2Id(
            @RequestParam( "id1" ) Integer id1,
            @RequestParam( "id2" ) Integer id2,
            Model model
    ){
        List<Message> res = messageRepository.getMessageBy2Id( id1, id2 );
        if( id1 != id2 ) res.addAll( messageRepository.getMessageBy2Id( id2, id1 ) );

        model.addAttribute( "messages", res );

        return "messages/messages";
    }

    @CrossOrigin
    @RequestMapping( value = "/message2idJson", method = RequestMethod.GET )
    public @ResponseBody List<Message> getById2Json(
            @RequestParam( "id1" ) Integer id1,
            @RequestParam( "id2" ) Integer id2
    ){
        List<Message> res = messageRepository.getMessageBy2Id( id1, id2 );
        if( id1 != id2 ) res.addAll( messageRepository.getMessageBy2Id( id2, id1 ) );
        return res;
    }

//    @CrossOrigin
//    @RequestMapping( value = "/kek2", method = RequestMethod.POST )
//    public @ResponseBody String create(
//                                       @RequestParam( "fromId") Integer fromId,
//                                       @RequestParam( "toId" ) Integer toId,
//                                       @RequestParam( "message" ) String message
//   ){
//        System.out.println( String.format( "%s %s %s", fromId, toId, message ) );
//
//        return "{status : ok}";
//    }
}
