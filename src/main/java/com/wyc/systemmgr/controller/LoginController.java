package com.wyc.systemmgr.controller;

import com.wyc.base.utils.CommonUtility;
import com.wyc.exception.BaseException;
import com.wyc.shiro.CurrentUserHelper;
import com.wyc.systemmgr.util.VerificationCodeImgUtil;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;

import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.util.Map;

/**
 * Created by wangyc on 2019/10/31.
 */

@RestController
public class LoginController {

    private static final Logger logger = Logger.getLogger(LoginController.class);

    @PostMapping("/login")
    public String login(@RequestBody Map<String, Object> param) throws BaseException{

        if(!param.containsKey("username")){
            return CommonUtility.constructResultJson("-1","param username is required！");
        }
        if(!param.containsKey("password")){
            return CommonUtility.constructResultJson("-1","param password is required！");
        }
        String code = String.valueOf(param.get("code"));
        Session session = SecurityUtils.getSubject().getSession();
        String vCode = String.valueOf(session.getAttribute("code"));
        if(!vCode.equalsIgnoreCase(code)){
            return CommonUtility.constructResultJson("-1","验证码错误！");
        }
        String username = String.valueOf(param.get("username"));
        String password = String.valueOf(param.get("password"));


        Subject subject = SecurityUtils.getSubject();
        // 在认证提交前准备 token（令牌）
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        // 执行认证登陆
        String retMsg;
        String retCode = "-1";
        try {
            subject.login(token);
            if (subject.isAuthenticated()) {
                retMsg = "登录成功";
                retCode = "0";
                session = SecurityUtils.getSubject().getSession();
                session.setAttribute("user", SecurityUtils.getSubject().getPrincipal());
            } else {
                token.clear();
                retMsg = "登录失败";
            }
        } catch (UnknownAccountException uae) {
            retMsg = "未知账户";
        } catch (IncorrectCredentialsException ice) {
            retMsg = "密码不正确";
        } catch (LockedAccountException lae) {
            retMsg = "账户已锁定";
        } catch (ExcessiveAttemptsException eae) {
            retMsg = "用户名或密码错误次数过多";
        } catch (AuthenticationException ae) {
            retMsg = "用户名或密码不正确！";
        }
        return CommonUtility.constructResultJson(retCode,retMsg);
    }

    @GetMapping("/logout")
    public String logout() throws BaseException{
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return CommonUtility.constructResultJson("0","退出成功！");
    }

    @GetMapping("/identifyCode")
    public String getIdentifyCode(HttpServletRequest request, HttpServletResponse response)  throws BaseException{
        response.setContentType("image/jpeg");
        //禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        Session session = SecurityUtils.getSubject().getSession();
        VerificationCodeImgUtil vCode = new VerificationCodeImgUtil();
        session.setAttribute("code", vCode.getCode());
        try{
            vCode.write(response.getOutputStream());
        }catch (IOException e){
            throw new BaseException(-1, "生成文件错误", e);
        }
        return null;
    }
}
