package com.wyc.systemmgr.controller;

import com.github.pagehelper.PageHelper;

import com.wyc.base.entity.Pagination;
import com.wyc.exception.BaseException;
import com.wyc.systemmgr.service.IUserService;
import com.wyc.base.utils.CommonUtility;
import com.wyc.base.utils.PaginationUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by wangyc on 2019/10/31.
 */

@RestController
@RequestMapping(value = "/systemMgr/user")
public class UserController {

    private static final Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private IUserService userService;

    @PostMapping("/save")
    public String save(@RequestParam Map<String, Object> param) throws BaseException{
        userService.save(param);

        return CommonUtility.constructResultJson("0","操作成功！");
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id) throws BaseException{
        userService.delete(id);
        return CommonUtility.constructResultJson("0","操作成功！");
    }

    @PostMapping("/find")
    public Pagination find(@RequestParam Map<String, Object> param)throws BaseException{
        logger.info(param);
        int pageNum = Integer.valueOf(param.get("page").toString());
        int pageSize = Integer.valueOf(param.get("rows").toString());
        PageHelper.startPage(pageNum, pageSize);
        List<Map> userList = userService.find(param);
        Pagination result = PaginationUtil.getPageList(userList);

        return result;
    }
}
