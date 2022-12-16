package com.dydong.controller;

import com.dydong.pojo.Article;
import com.dydong.pojo.User;
import com.dydong.service.ArticleServiceImpl;
import com.dydong.service.UserServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/user")
public class SuperController {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    ArticleServiceImpl articleService;

    @RequestMapping("/superUsers")
    public String superUsers(Model model){
        String invitation = Security.getInvitation();
        if(!invitation.equals("root"))  return "redirect:/user/noauth";
        List<User> users = userService.queryAllUser();
        model.addAttribute("username",Security.getUsername());
        model.addAttribute("users",users);
        return "user/superusers";
    }

    @RequestMapping("/superUpdateUser/{id}")
    public String superUpdateUser(@PathVariable("id") int id,Model model){
        String invitation = Security.getInvitation();
        if(!invitation.equals("root"))  return "redirect:/user/noauth";
        User user = userService.queryUserById(id);
        model.addAttribute("username",Security.getUsername());
        model.addAttribute("user",user);
        return "user/superinformation";
    }

    @RequestMapping("/superDeleteUser/{id}")
    public String superDeleteUser(@PathVariable("id") int id){
        String invitation = Security.getInvitation();
        if(!invitation.equals("root"))  return "redirect:/user/noauth";
        userService.deleteUserById(id);
        return "redirect:/user/superUsers";
    }

    @RequestMapping("/superArticle")
    public String superArticle(Model model){
        String invitation = Security.getInvitation();
        if(!invitation.equals("root"))  return "redirect:/user/noauth";
        List<Article> articles = articleService.queryArticles();
        model.addAttribute("username",Security.getUsername());
        model.addAttribute("articles",articles);
        return "user/superarticle";
    }

    @RequestMapping("/superDeleteArticle/{id}")
    public String deleteArticle(@PathVariable("id") int id){
        String invitation = Security.getInvitation();
        if(!invitation.equals("root"))  return "redirect:/user/noauth";
        articleService.deleteArticleById(id);
        return "redirect:/user/superArticle";
    }

    @GetMapping("/superUpdateArticle")
    public String superUpdateArticle(@RequestParam("id") int id, Model model){
        String invitation = Security.getInvitation();
        if(!invitation.equals("root"))  return "redirect:/user/noauth";
        Article article = articleService.getArticleById(id);
        model.addAttribute("article",article);
        return "user/update";
    }
}
