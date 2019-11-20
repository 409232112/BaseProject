package com.wyc.core.shiro.annotation;

import java.lang.annotation.*;

/**
 * Created by wangyc on 2019/11/19.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RoleCheck {
    String[] roles() default {};
}
