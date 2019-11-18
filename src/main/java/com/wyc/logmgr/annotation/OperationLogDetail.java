package com.wyc.logmgr.annotation;

/**
 * Created by wangyc on 2019/11/18.
 */
import com.wyc.logmgr.enums.OperationType;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OperationLogDetail {

    /**
     * 操作类型(enum):主要是select,insert,update,delete
     */
    OperationType operationType() default OperationType.UNKNOWN;

    String operationObject() default "";
}

