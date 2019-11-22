package com.wyc.core.util;

import com.wyc.core.base.exception.BaseException;

import java.io.File;
import java.text.DecimalFormat;

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

    public static String getFileSize(String path){
        File file = new File(path);
        String size = "";
        if(file.exists() && file.isFile()){
            long fileS = file.length();
            DecimalFormat df = new DecimalFormat("#.00");
            if (fileS < 1024) {
                size = "0"+df.format((double) fileS / 1024) + " KB";
            } else if (fileS < 1048576) {
                size = df.format((double) fileS / 1024) + " KB";
            } else if (fileS < 1073741824) {
                size = df.format((double) fileS / 1048576) + " MB";
            } else {
                size = df.format((double) fileS / 1073741824) +" GB";
            }
        }else if(file.exists() && file.isDirectory()){
            size = "";
        }else{
            size = "0 KB";
        }
        return size;
    }

    public static void deleteFile(String path) throws BaseException{
        File file = new File(path);
        if(file.isDirectory()){
            throw new BaseException("路径是目录，无法删除！");
        }
        if (file.exists()) {
            file.delete();
        }
    }
}
