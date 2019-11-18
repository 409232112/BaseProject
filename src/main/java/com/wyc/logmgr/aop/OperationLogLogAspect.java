package com.wyc.logmgr.aop;

import com.alibaba.fastjson.JSON;
import com.wyc.logmgr.annotation.OperationLogDetail;
import com.wyc.logmgr.service.IOperationLogService;
import com.wyc.logmgr.util.IpUtil;
import com.wyc.shiro.CurrentUserHelper;
import org.apache.commons.collections.map.HashedMap;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by wangyc on 2019/11/18.
 */

@Aspect
@Component
public class OperationLogLogAspect {
    @Autowired
    private IOperationLogService operationLogService;

    @Value("${operationLog}")
    private String operationLogOn;

    @Pointcut("@annotation(com.wyc.logmgr.annotation.OperationLogDetail)")
    public void operationLog(){}

    @Around("operationLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object res = null;
        long time = System.currentTimeMillis();
        try {
            res =  joinPoint.proceed();
            time = System.currentTimeMillis() - time;
            return res;
        } finally {
            try {
                if(Boolean.valueOf(operationLogOn)){
                    addOperationLog(joinPoint,res,time);
                }
                //方法执行完成后增加日志
            }catch (Exception e){
                System.out.println("LogAspect 操作失败：" + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @Before("operationLog()")
    public void doBeforeAdvice(JoinPoint joinPoint){
        System.out.println("进入方法前执行.....");

    }

    /**
     * 处理完请求，返回内容
     * @param ret
     */
    @AfterReturning(returning = "ret", pointcut = "operationLog()")
    public void doAfterReturning(Object ret) {
        System.out.println("方法的返回值 : " + ret);
    }

    /**
     * 后置异常通知
     */
    @AfterThrowing("operationLog()")
    public void throwss(JoinPoint jp){
        System.out.println("方法异常时执行.....");
    }


    /**
     * 后置最终通知,final增强，不管是抛出异常或者正常退出都会执行
     */
    @After("operationLog()")
    public void after(JoinPoint jp){
        System.out.println("方法最后执行.....");
    }

    private void addOperationLog(JoinPoint joinPoint, Object res, long time){
        Map<String, Object> param = new HashedMap();
        param.put("runTime",String.valueOf(time));
        param.put("returnValue",String.valueOf(res));
        param.put("params", JSON.toJSONString(joinPoint.getArgs()));
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        param.put("methodName",String.valueOf(signature.getDeclaringTypeName() + "." + signature.getName()));
        OperationLogDetail annotation = signature.getMethod().getAnnotation(OperationLogDetail.class);
        if(annotation != null){
            param.put("operationType",annotation.operationType().getValue());
            param.put("operationObject",annotation.operationObject());
        }
        param.put("userId",CurrentUserHelper.getId());
        param.put("name", CurrentUserHelper.getName());
        param.put("username",CurrentUserHelper.getUsername());
        param.put("department",CurrentUserHelper.getDept());
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        param.put("ip", IpUtil.getIpAddr(request));
        param.put("url",  request.getRequestURI());


        System.out.println("记录日志");
        try{
            operationLogService.insert(param);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
