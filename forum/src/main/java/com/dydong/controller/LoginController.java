package com.dydong.controller;

import com.dydong.pojo.User;
import com.dydong.service.UserServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class LoginController {

    @Autowired
    UserServiceImpl userService;

    @GetMapping("/login")
    public String login() {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        if(session.getAttribute("username")!=null)  return "user/welcome";
        return "user/login";
    }

    @PostMapping("/logining")
    public String logining(String username, String password, Model model){
        //获取当前过户
        Subject subject = SecurityUtils.getSubject();
        //封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        //登录
        try{
            subject.login(token);
            model.addAttribute("username",username);
            User user = userService.queryUserByName(username);
            model.addAttribute("user",user);
            return "user/welcome";
        } catch (UnknownAccountException e){
            //用户名不存在
            model.addAttribute("msg","用户名错误");
            return "user/login";
        } catch (IncorrectCredentialsException e){
            model.addAttribute("msg","密码不存在");
            return "user/login";
        }
    }

    @RequestMapping("/noauth")
    @ResponseBody
    public String unauthorized(){
        return "未经授权无法访问此页面";
    }

    @RequestMapping("/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "user/login";
    }
}