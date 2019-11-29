package com.wyc.core.excel.service;

import com.alibaba.fastjson.JSON;
import com.wyc.core.base.exception.BaseException;
import com.wyc.core.excel.util.ExcelUtil;
import com.wyc.core.shiro.CurrentUserHelper;
import com.wyc.core.util.FileUtil;
import com.wyc.core.util.HttpUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangyc on 2019/11/21.
 */

@Service
public class ExcelService {
    private static String tempDir;

    @Value("${filesDir.tempDir}")
    public void setTempDir(String tempDir) {
        this.tempDir = tempDir;
    }

    private static String port;

    @Value("${server.port}")
    public void setPort(String port) {
        this.port = port;
    }

    public String allToExcel(Map<String, Object> param,HttpServletRequest request) throws Exception{
        Map<String,String> headers = new HashMap();
        headers.put("Cookie",request.getHeader("Cookie"));
        headers.put("Content-Type","application/x-www-form-urlencoded");
        String url="http://127.0.0.1:" + port +param.get("url");
        String result = HttpUtil.post(url,headers,(Map)param.get("params"));
        HashMap data = JSON.parseObject(result, HashMap.class);

        List<String> titles = (List)param.get("titles");
        List<String> columns = (List)param.get("columns");



        if(titles.size()!=columns.size()){
            throw new BaseException("titles 和 columns 长度不配");
        }
        List<Map> rows = (List)data.get("rows");
        Map<String,String> titleColRel = new HashMap();
        for(int i = 0;i<titles.size();i++){
            titleColRel.put(columns.get(i),titles.get(i));
        }


        List<Map<String,String>> dataList = new ArrayList<>();
        Map map;
        for(int i=0;i<rows.size();i++){
            map = new HashMap();
            for (Map.Entry<String, String> entry : titleColRel.entrySet()) {
                map.put(entry.getValue(),rows.get(i).get(entry.getKey()));
            }
            dataList.add(map);
        }

        String filePath = ExcelUtil.createFile(dataList,tempDir);
        FileUtil.renameFile(tempDir+filePath, CurrentUserHelper.getName()+"_"+param.get("file_name")+"_"+filePath.replace(".xls",""));
        String fileName = CurrentUserHelper.getName()+"_"+param.get("file_name")+"_"+filePath;

        return fileName;
    }
}
