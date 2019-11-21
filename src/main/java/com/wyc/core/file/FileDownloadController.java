package com.wyc.core.file;

import com.alibaba.fastjson.JSON;
import com.wyc.core.excel.util.ExcelUtil;
import com.wyc.core.utils.CommonUtility;
import com.wyc.core.utils.HttpUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangyc on 2019/11/20.
 */
@RestController
public class FileDownloadController {

    private static String tempDir;

    @Value("${filesDir.tempDir}")
    public void setTempDir(String tempDir) {
        this.tempDir = tempDir;
    }

    @GetMapping("/downloadFile")
    public void download(String type, String file, String fileName, HttpServletResponse response) throws Exception {
        String filePath = "";
        String file_name = "";
        if(type.equals("excel") ){
            filePath = tempDir+file;
            file_name =URLEncoder.encode(fileName+".xls", "UTF-8");
        }

        response.setHeader("Content-Disposition", "attachment;filename="+ file_name);
        response.setHeader("Connection", "close");
        response.setHeader("Content-Type", "application/octet-stream");

        OutputStream ops = null;
        FileInputStream fis =null;
        byte[] buffer = new byte[8192];
        int bytesRead = 0;

        try {
            ops = response.getOutputStream();
            fis = new FileInputStream(filePath);
            while((bytesRead = fis.read(buffer, 0, 8192)) != -1){
                ops.write(buffer, 0, bytesRead);
            }
            ops.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(fis != null){
                    fis.close();
                }
                if(ops != null){
                    ops.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
