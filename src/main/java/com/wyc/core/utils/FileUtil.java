package com.wyc.core.utils;

import java.io.File;

/**
 * Created by wangyc on 2019/11/20.
 */
public class FileUtil {
    public static void touchDir(String path){
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
    }
}
