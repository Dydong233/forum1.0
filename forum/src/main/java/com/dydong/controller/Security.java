package com.dydong.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

public class Security {
    public static String getUsername()
    {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        String username = (String) session.getAttribute("username");
        return username;
    }

    public static String getInvitation()
    {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        String invitation = (String) session.getAttribute("invitation");
        return invitation;
    }
}
