package com.wyc.core.base.exception;

/**
 * Created by wangyc on 2019/11/21.
 */

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotFoundController implements ErrorController {
    private static final String ERROR_PATH = "/error";
    @RequestMapping(value=ERROR_PATH)
    public String handleError(HttpServletRequest request){
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        return "/404";
    }
    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
