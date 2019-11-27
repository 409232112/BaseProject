package com.wyc.logmgr.controller;

import com.github.pagehelper.PageHelper;
import com.wyc.core.base.entity.Pagination;
import com.wyc.core.util.PaginationUtil;
import com.wyc.core.base.exception.BaseException;

import com.wyc.logmgr.service.IOperationLogService;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by wangyc on 2019/10/31.
 */

@RestController
@RequestMapping(value = "/LogMgr/operationlog")
public class OperationLogController {

    private static final Logger logger = Logger.getLogger(OperationLogController.class);

    @Autowired
    private IOperationLogService operationLogService;

    //@RoleCheck(roles ={"超级管理员","系统管理员"})
    @PostMapping("/find")
    public Pagination find(@RequestParam Map<String, Object> param)throws BaseException{
        int pageNum = Integer.valueOf(param.get("page").toString());
        int pageSize = Integer.valueOf(param.get("rows").toString());
        PageHelper.startPage(pageNum, pageSize);
        List<Map> userList = operationLogService.find(param);
        Pagination result = PaginationUtil.getPageList(userList);
        return result;
    }

}
