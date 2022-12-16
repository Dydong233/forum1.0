package com.dydong.controller;

import com.dydong.mapper.UserMapper;
import com.dydong.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    UserMapper userMapper;

    @RequestMapping({"/","/index"})
    public String index(Model model){
        List<User> users = userMapper.queryAllUser();
        model.addAttribute("users",users);
        return "index";
    }
}
