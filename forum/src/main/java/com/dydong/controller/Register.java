package com.dydong.controller;

import com.dydong.pojo.User;
import com.dydong.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class Register {

    @Autowired
    UserServiceImpl userService;

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String postRegister(Model model,String username,String password,String repeat){
        System.out.println(username+" "+password+" "+repeat);
        User user = userService.queryUserByName(username);
        if(user!=null)
        {
            model.addAttribute("msg","该用户已存在");
            return "register";
        }
        if(!repeat.equals(password))
        {
            model.addAttribute("msg","密码输入不一致");
            return "register";
        }
        user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setInvitation("user");
        user.setIntroduction("这个人很懒，没有留下什么备注......");
        user.setPic("/img/person_1.jpg");
        userService.addUser(user);
        return "/user/login";
    }
}
