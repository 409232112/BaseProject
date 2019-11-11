package com.wyc.systemmgr.controller;

import com.wyc.base.utils.CommonUtility;
import com.wyc.exception.BaseException;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
}