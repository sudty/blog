package com.blog.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SysController {


    @RequestMapping("admin/toLogin")
    public String toLogin(){
        return "admin/login";
    }

    @RequestMapping("error/404")
    public String to404(){
        return "error/404";
    }


}
