package com.dydong.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ChatroomController {
    @RequestMapping("/chatroom")
    public String chatroom(Model model){
        String username = Security.getUsername();
        if(username==null)  return "user/login";
        model.addAttribute("username",username);
        return "chatroom";
    }
}
