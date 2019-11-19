package com.wyc.exception;


import com.wyc.base.utils.CommonUtility;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


import java.util.HashMap;


/**
 * 全局异常处理器
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = Logger.getLogger(GlobalExceptionHandler.class);

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public String handle(Exception e) {
        log.error(e.getClass().toString(),e);

        String result=null;
        try {
            String message= "服务器内部异常";
            result= CommonUtility.constructResultJson("-1", message,new HashMap());
        } catch (BaseException e1) {
            e1.printStackTrace();
        }
        return result;
    }

    @ResponseBody
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public String handle403(Exception e) {
        log.error(e.getClass().toString(),e);

        String result=null;
        try {
            String message= "没有权限！";
            result= CommonUtility.constructResultJson("-1", message,new HashMap());
        } catch (BaseException e1) {
            e1.printStackTrace();
        }
        return result;
    }



}
