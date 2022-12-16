package com.dydong.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ToolsController {
    @RequestMapping("/tools")
    public String tools() {
        return "tools";
    }
}
