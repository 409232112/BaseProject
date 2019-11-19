package com.wyc.shiro.annotation;

import java.lang.annotation.*;
import java.util.List;

/**
 * Created by wangyc on 2019/11/19.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RoleCheck {
    String[] roles() default {};
}
