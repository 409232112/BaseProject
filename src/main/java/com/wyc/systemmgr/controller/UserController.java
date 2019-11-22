package com.wyc.systemmgr.controller;

import com.github.pagehelper.PageHelper;

import com.wyc.core.base.entity.Pagination;
import com.wyc.core.base.exception.BaseException;
import com.wyc.logmgr.annotation.OperationLogDetail;
import com.wyc.logmgr.enums.OperationType;
import com.wyc.systemmgr.service.IUserService;
import com.wyc.core.util.CommonUtility;
import com.wyc.core.util.PaginationUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by wangyc on 2019/10/31.
 */

@RestController
@RequestMapping(value = "/SystemMgr/user")
public class UserController {

    private static final Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private IUserService userService;

    @OperationLogDetail(operationType = OperationType.SAVE,operationObject="User")
    @PostMapping("/save")
    public String save(@RequestParam Map<String, Object> param) throws BaseException{
        try{
            userService.save(param);
        }catch (BaseException e){
            return CommonUtility.constructResultJson("-1",e.getMessage());
        }
        return CommonUtility.constructResultJson("0","操作成功！");
    }

    @OperationLogDetail(operationType = OperationType.DELETE,operationObject="User,Model")
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id) throws BaseException{
        userService.delete(id);
        return CommonUtility.constructResultJson("0","操作成功！");
    }

    @PostMapping("/find")
    public Pagination find(@RequestParam Map<String, Object> param)throws BaseException{
        int pageNum = Integer.valueOf(param.get("page").toString());
        int pageSize = Integer.valueOf(param.get("rows").toString());
        PageHelper.startPage(pageNum, pageSize);
        List<Map> userList = userService.find(param);
        Pagination result = PaginationUtil.getPageList(userList);
        return result;
    }

    @OperationLogDetail(operationType = OperationType.UPDATE,operationObject="User")
    @PostMapping("/changePassword")
    public String changePassword(@RequestBody Map data) throws BaseException {
        userService.changePassword(data);
        return CommonUtility.constructResultJson("0","操作成功！");
    }
}
