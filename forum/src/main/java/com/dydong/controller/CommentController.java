package com.dydong.controller;


import com.dydong.pojo.Comment;
import com.dydong.service.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {

    @Autowired
    CommentServiceImpl commentService;

    @PostMapping("/comment")
    private String comment(Comment comment){
        String username = Security.getUsername();
        if(username==null)  return "/user/login";
        comment.setAuthor(username);
        commentService.addComment(comment);
        return "redirect:/articleshow/"+comment.getAid();
    }
}
