package com.wyc.core.util;

import java.util.UUID;

/**
 * Created by wangyc on 2019/11/4.
 */
public class StringUtil {
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
