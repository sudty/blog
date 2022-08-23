package com.blog.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
@RequestMapping("/admin")
public class LoginController {

    @GetMapping()
    public String loginPage(){
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        RedirectAttributes attributes){
        log.info("开始登录");
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        try{
            subject.login(token);
            return "redirect:/admin/home";
        }catch (UnknownAccountException e){
            log.info("--------用户名错误------------");
            attributes.addFlashAttribute("msg", "用户名错误");
            return "redirect:/admin";
        }catch (IncorrectCredentialsException e){
            log.info("--------密码错误------------");
            attributes.addFlashAttribute("msg", "密码错误");
            return "redirect:/admin";
        }catch (AuthenticationException ae){
            attributes.addFlashAttribute("msg", "登录失败");
            return "redirect:/admin";
        }
    }

    @GetMapping("/home")
    public String home(){
        return "admin/index";
    }

    @GetMapping("/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/";
    }

}
