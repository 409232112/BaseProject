package com.wyc.systemmgr.controller;

import com.github.pagehelper.PageHelper;
import com.wyc.base.entity.Pagination;
import com.wyc.base.utils.CommonUtility;
import com.wyc.base.utils.PaginationUtil;
import com.wyc.exception.BaseException;
import com.wyc.systemmgr.service.IModelService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangyc on 2019/10/31.
 */

@RestController
@RequestMapping(value = "/SystemMgr/model")
public class ModelController {

    private static final Logger logger = Logger.getLogger(ModelController.class);

    @Autowired
    private IModelService modelService;

    @PostMapping("/save")
    public String save(@RequestParam Map<String, Object> param) throws BaseException {
        modelService.save(param);
        return CommonUtility.constructResultJson("0","操作成功！");
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id) throws BaseException{
        try{
            modelService.delete(id);
            return CommonUtility.constructResultJson("0","操作成功！");
        }catch (BaseException e){
            e.printStackTrace();
            return CommonUtility.constructResultJson("-1",e.getMessage());
        }
    }

    @PostMapping("/find")
    public Pagination find(@RequestParam Map<String, Object> param) throws BaseException{
        int pageNum = Integer.valueOf(param.get("page").toString());
        int pageSize = Integer.valueOf(param.get("rows").toString());
        PageHelper.startPage(pageNum, pageSize);
        List<Map> userList = modelService.find(param);
        Pagination result = PaginationUtil.getPageList(userList);
        return result;
    }

    @GetMapping("/findForMenu")
    public List<Map> findForMenu(){
        List<Map> menuList = modelService.findForMenu();
        return menuList;
    }

}
