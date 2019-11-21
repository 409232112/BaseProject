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

    public static String renameFile(String filePath,String newName){
        String newFilePath = filePath.substring(0, filePath.lastIndexOf("/")) + "/" + newName + filePath.substring(filePath.lastIndexOf("."));
        File nf = new File(newFilePath);
        File f = new File(filePath);
        try {
            f.renameTo(nf);
        } catch (Exception err) {
            err.printStackTrace();
        }
        return newFilePath;
    }


}
