package com.dydong.controller;

import com.dydong.mapper.ArticleMapper;
import com.dydong.pojo.Article;
import com.dydong.pojo.User;
import com.dydong.service.ArticleServiceImpl;
import com.dydong.service.UserService;
import com.dydong.service.UserServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    ArticleServiceImpl articleService;

    //欢迎界面
    @RequestMapping("/welcome")
    public String welcome(Model model){
        String username = Security.getUsername();
        if(username==null)  return "user/login";
        model.addAttribute("username",username);
        User user = userService.queryUserByName(username);
        model.addAttribute("user",user);
        return "user/welcome";
    }

    //发布文章界面
    @RequestMapping("/editor")
    public String editor(Model model){
        String username = Security.getUsername();
        if(username==null) return "user/login";
        model.addAttribute("username",username);
        return "user/editor";
    }

    //个人文章界面
    @RequestMapping("/selfarticle")
    public String selfArticle(Model model){
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        if(session.getAttribute("username")==null)  return "user/login";
        String username = (String) session.getAttribute("username");
        List<Article> articles = articleService.getArticleByAuthor(username);
        model.addAttribute("username",username);
        model.addAttribute("articles",articles);
        return "user/selfarticle";
    }

    //个人信息界面
    @RequestMapping("/selfinformation")
    public String selfInformation(Model model){
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        String username = (String) session.getAttribute("username");
        if(username==null)  return "user/login";
        model.addAttribute("username",username);
        User user = userService.queryUserByName(username);
        model.addAttribute("user",user);
        return "user/selfinformation";
    }

    //个人信息提交请求
    @PostMapping("/selfupdate")
    public String selfUpdate(User user,@RequestParam("picture") MultipartFile picture) throws IOException {
        int choice=0;
        String username = Security.getUsername();
        String invitation = Security.getInvitation();
        if(!invitation.equals("root")&&!username.equals(user.getUsername()))
            return "redirect:/user/noauth";
        if (user.getUsername().equals(username)) choice=1;
        if(picture.isEmpty()){
            User oldUser = userService.queryUserById(user.getId());
            user.setPic(oldUser.getPic());
            userService.updateUser(user);
            if (choice==1) return "redirect:/user/welcome   ";
            else return "redirect:/user/superUsers";
        }
        String path = System.getProperty("user.dir")+"/upload/icon";
        File realPath = new File(path);
        if(!realPath.exists()) realPath.mkdirs();
        String filename = "pg-" + UUID.randomUUID().toString().replaceAll("-", "") + ".jpg";
        File newfile = new File(realPath, filename);
        picture.transferTo(newfile);
        String url = "/upload/icon/"+filename;
        System.out.println(url);
        user.setPic(url);
        userService.updateUser(user);
        if (choice==1) return "redirect:/user/welcome";
        else return "redirect:/user/superUsers";
    }
}