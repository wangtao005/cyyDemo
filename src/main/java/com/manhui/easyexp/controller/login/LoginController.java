package com.manhui.easyexp.controller.login;

import org.mics.token.annotation.IgnoreToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录跳转
 */
@Controller
public class LoginController {
    @GetMapping("/")
    @IgnoreToken //忽略登录
    public String login(HttpServletRequest request, HttpServletResponse response) {
        return "html/index";
    }

    @GetMapping("/test")
    @IgnoreToken //忽略登录
    public String test(HttpServletRequest request, HttpServletResponse response) {
        return "html/test";
    }

}
