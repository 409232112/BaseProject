package com.wyc.base.annotation;

import com.wyc.base.register.StartUpRegister;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Created by wangyc on 2019/11/18.
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.TYPE)
@Import({StartUpRegister.class})
public @interface StartUpScan { // 启动类上面@SectionScan(basePackages={"扫描的类路径"}) 类似@ComponentScan(basePackages = {"com.migu.*"})
    String[] value() default {};
    String[] basePackages() default {};
}

