package ru.isu.CourseProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.isu.CourseProject.repository.MessageRepository;

@Controller
@RequestMapping( "/messages" )
public class MessageController {

    private MessageRepository messageRepository;

    @RequestMapping( value = "/all", method = RequestMethod.GET)
    public String all( Model model ){
        model.addAttribute( "messages", messageRepository.getAll() );
        return "messages/messages";
    }
}
