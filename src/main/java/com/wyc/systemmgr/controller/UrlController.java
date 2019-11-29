package com.wyc.systemmgr.controller;

import com.wyc.core.base.exception.BaseException;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by wangyc on 2019/10/31.
 */

@Controller

public class UrlController {

    private static final Logger logger = Logger.getLogger(UrlController.class);

    @GetMapping("/login")
    public String login() throws BaseException {
        Subject subject = SecurityUtils.getSubject();

        if (subject.getPrincipal() != null) {
            return "redirect:main";
        }else{
            return null;
        }
    }

    @GetMapping("/main")
    public String main() throws BaseException {
        return "main.html";
    }

    @GetMapping("/403")
    public String forbidden(HttpServletResponse response) throws BaseException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        return "403.html";
    }

    @GetMapping("/404")
    public String notFound(HttpServletResponse response) throws BaseException {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        return "404.html";
    }
}
