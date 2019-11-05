package com.wyc.systemmgr.controller;

import com.wyc.base.utils.CommonUtility;
import com.wyc.exception.BaseException;
import com.wyc.systemmgr.service.IDepartmentService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by wangyc on 2019/10/31.
 */

@RestController
@RequestMapping(value = "/SystemMgr/department")
public class DepartmentController {

    private static final Logger logger = Logger.getLogger(DepartmentController.class);

    @Autowired
    private IDepartmentService departmentlService;

    @PostMapping("/save")
    public String save(@RequestParam Map<String, Object> param) throws BaseException {
        departmentlService.save(param);
        return CommonUtility.constructResultJson("0","操作成功！");
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id) throws BaseException{
        try{
            departmentlService.delete(id);
            return CommonUtility.constructResultJson("0","操作成功！");
        }catch (BaseException e){
            e.printStackTrace();
            return CommonUtility.constructResultJson("-1",e.getMessage());
        }
    }


    @PostMapping("/find")
    public List<Map> find(@RequestParam Map<String, Object> param) throws BaseException{
        List<Map> departList = departmentlService.find(param);
        return departList;
    }

    @PostMapping("/findTree")
    public List<Map> findTree(@RequestParam Map<String, Object> param) throws BaseException{
        List<Map> departList = departmentlService.findTree(param);
        return departList;
    }

}
