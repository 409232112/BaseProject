package com.wyc.core.file;

import com.wyc.core.util.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

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
            file_name =fileName+".xls";
        }
        FileUtil.downloadFile(file_name,filePath,response);

    }
}
