package com.wyc.filemgr.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.wyc.core.base.entity.Pagination;
import com.wyc.core.base.exception.BaseException;
import com.wyc.core.util.CommonUtility;
import com.wyc.core.util.FileUtil;
import com.wyc.core.util.PaginationUtil;
import com.wyc.filemgr.service.IFileService;
import com.wyc.logmgr.annotation.OperationLogDetail;
import com.wyc.logmgr.enums.OperationType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

/**
 * Created by wangyc on 2019/10/31.
 */

@RestController
@RequestMapping(value = "/FileMgr/file")
public class FileController {

    private static final Logger logger = Logger.getLogger(FileController.class);

    @Autowired
    private IFileService fileService;

    @OperationLogDetail(operationType = OperationType.SAVE,operationObject="File")
    @PostMapping("/save")
    public String save(@RequestParam Map<String, Object> param,@RequestParam("file") MultipartFile file) throws BaseException{
        try{
            fileService.save(param,file);
        }catch (Exception e){
            return CommonUtility.constructResultJson("-1",e.getMessage());
        }
        return CommonUtility.constructResultJson("0","操作成功！");
    }

    @OperationLogDetail(operationType = OperationType.DELETE,operationObject="File")
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id) throws BaseException{
        fileService.delete(id);
        return CommonUtility.constructResultJson("0","操作成功！");
    }

    @PostMapping("/find")
    public Pagination find(@RequestParam Map<String, Object> param)throws BaseException{
        System.out.println("===============");
        System.out.println(param);
        int pageNum = Integer.valueOf(param.get("page").toString());
        int pageSize = Integer.valueOf(param.get("rows").toString());
        PageHelper.startPage(pageNum, pageSize);
        List<Map> userList = fileService.find(param);
        Pagination result = PaginationUtil.getPageList(userList);
        return result;
    }

    @GetMapping("/getFileType")
    public String getFileType() throws BaseException{
        List<Map> datas = fileService.getFileType();
        return JSON.toJSONString(datas);
    }

    @GetMapping("/downloadFile/{id}")
    public String downloadFile(@PathVariable("id") String id,HttpServletResponse response) throws Exception{
        Map<String,String> filePathAndName = fileService.getFilePathAndName(id);
        String filePath = filePathAndName.get("file_path");
        String fileName = filePathAndName.get("file_name");
        try{
            FileUtil.downloadFile(fileName,filePath,response);
        }catch (FileNotFoundException e){
            return CommonUtility.constructResultJson("-1",e.getMessage());
        }
        return null;
    }

}
