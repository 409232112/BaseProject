package com.wyc.core.excel.controller;

import com.alibaba.fastjson.JSON;
import com.wyc.core.base.exception.BaseException;
import com.wyc.core.excel.util.ExcelUtil;
import com.wyc.core.utils.CommonUtility;
import com.wyc.core.utils.HttpUtil;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangyc on 2019/11/20.
 */
@RestController
@RequestMapping(value = "/excel")
public class ExcelController {

    private static String tempDir;

    @Value("${tempDir}")
    public void setTempDir(String tempDir) {
        this.tempDir = tempDir;
    }

    @PostMapping("/allToExcel")
    public String allToExcel(@RequestBody Map<String, Object> param,HttpServletRequest request) throws Exception {
        Map<String,Object> headers = new HashMap();
        headers.put("Cookie",request.getHeader("Cookie"));
        String url = "http://127.0.0.1:8888/"+param.get("url");
        String result = HttpUtil.post(url,(Map)param.get("params"),headers);
        HashMap data = JSON.parseObject(result, HashMap.class);
        String filePath = ExcelUtil.createFile(String.valueOf(param.get("file_name")),(List)param.get("titles"),(List)param.get("columns"), (List)data.get("rows"));
        Map retData = new HashMap();
        retData.put("file",filePath);
        retData.put("type","excel");
        return CommonUtility.constructResultJson("0","操作成功！",retData);
    }
}
