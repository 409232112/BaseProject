package com.wyc.core.utils;
import com.wyc.core.base.exception.BaseException;
import org.springframework.cglib.beans.BeanMap;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class BeanUtil {

    public static <T> Map<String, Object> convertToMap(T bean) {
        Map<String, Object> map = new HashMap();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key.toString(), beanMap.get(key));
            }
        }
        return map;
    }


    public static Object convertToBean(Map<String, Object> params) throws BaseException{
        String classStr = String.valueOf(params.get("beanName"));
        Class<?> clazz = null;
        Object object = null;
        try{
            clazz = Class.forName(classStr);
        }catch (ClassNotFoundException e){
            throw new BaseException(-1, "服务器内部错误", e);
        }
        try{
            object = clazz.newInstance();
        }catch (IllegalAccessException e){
            throw new BaseException(-1, "服务器内部错误", e);
        }catch (InstantiationException e2){
            throw new BaseException(-1, "服务器内部错误", e2);
        }

        Field[] fields = clazz.getDeclaredFields();
        for(Field field: fields){
            String name = field.getName();
            String methodStr = "set"+name.toUpperCase().substring(0, 1)+name.substring(1);
            Method method = null;
            try{
                method = clazz.getMethod(methodStr,new Class[]{field.getType()});
            }catch (NoSuchMethodException e){
                throw new BaseException(-1, "服务器内部错误", e);
            }

            for(Map.Entry<String, Object> entry : params.entrySet()) {
                if (("set"+entry.getKey()).equalsIgnoreCase(method.getName())){
                    try{
                        method.invoke(object, entry.getValue());
                    }catch (IllegalAccessException e){
                        throw new BaseException(-1, "服务器内部错误", e);
                    }catch (InvocationTargetException e2){
                        throw new BaseException(-1, "服务器内部错误", e2);
                    }

                }
            }
        }
        return object;
    }

}
