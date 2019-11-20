package com.wyc.logmgr.controller;

import com.github.pagehelper.PageHelper;
import com.wyc.core.base.entity.Pagination;
import com.wyc.core.utils.CommonUtility;
import com.wyc.core.utils.PaginationUtil;
import com.wyc.core.base.exception.BaseException;
import com.wyc.logmgr.service.ILoginLogService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by wangyc on 2019/10/31.
 */

@RestController
@RequestMapping(value = "/LogMgr/loginlog")
public class LoginLogController {

    private static final Logger logger = Logger.getLogger(LoginLogController.class);

    @Autowired
    private ILoginLogService loginLogService;

    //@RoleCheck(roles ={"超级管理员","系统管理员"})
    @PostMapping("/find")
    public Pagination find(@RequestParam Map<String, Object> param)throws BaseException{
        int pageNum = Integer.valueOf(param.get("page").toString());
        int pageSize = Integer.valueOf(param.get("rows").toString());
        PageHelper.startPage(pageNum, pageSize);
        List<Map> userList = loginLogService.find(param);
        Pagination result = PaginationUtil.getPageList(userList);
        return result;
    }

    //@RoleCheck(roles ={"超级管理员","系统管理员"})
    @PostMapping("/chart")
    public String barChart(@RequestBody Map<String, Object> params) throws BaseException{
        String chartType = String .valueOf(params.get("chartType"));
        if(chartType.equals("bar")){
            Map chartInfo = loginLogService.queryForBarChart(params);
            return CommonUtility.constructResultJson("0","操作成功！",chartInfo);
        }else if(chartType.equals("pie")){
            Map chartInfo = loginLogService.queryForPieChart(params);
            return CommonUtility.constructResultJson("0","操作成功！",chartInfo);
        } else{
            return null;
        }
    }


}
