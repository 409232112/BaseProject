package com.wyc.systemmgr.controller;

import com.github.pagehelper.PageHelper;
import com.wyc.base.entity.Pagination;
import com.wyc.base.utils.PaginationUtil;
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
@RequestMapping(value = "/systemMgr/model")
public class ModelController {

    private static final Logger logger = Logger.getLogger(ModelController.class);

    @Autowired
    private IModelService modelService;


    @PostMapping("/find")
    public Pagination find(@RequestParam Map<String, Object> param){
        int pageNum = Integer.valueOf(param.get("page").toString());
        int pageSize = Integer.valueOf(param.get("rows").toString());
        PageHelper.startPage(pageNum, pageSize);
        List<Map> userList = modelService.find(param);
        Pagination result = PaginationUtil.getPageList(userList);
        return result;
    }

    @GetMapping("/findForMenu")
    public List<Map> find(){
        List<Map> menuList = modelService.findForMenu();
        return menuList;
    }

    public static void main(String args[]){
        List<Map> datas = new ArrayList();
        Map data = new HashMap();
        data.put("id","1");
        data.put("level","1");
        data.put("text","系统管理");
        datas.add(data);

        data = new HashMap();
        data.put("id","2");
        data.put("level","1");
        data.put("text","零件管理");
        datas.add(data);

        data = new HashMap();
        data.put("id","3");
        data.put("parent_id","1");
        data.put("level","2");
        data.put("text","用户管理");
        datas.add(data);

        data = new HashMap();
        data.put("id","4");
        data.put("parent_id","3");
        data.put("level","3");
        data.put("text","用户信息维护");
        datas.add(data);

        data = new HashMap();
        data.put("id","5");
        data.put("parent_id","3");
        data.put("level","3");
        data.put("text","用户信息查询");
        datas.add(data);

        data = new HashMap();
        data.put("id","6");
        data.put("parent_id","5");
        data.put("level","4");
        data.put("text","xxxxxxx");
        datas.add(data);

        System.out.println(datas);

        List<Map> retDatas =  new ArrayList();
        for(int i=0;i<datas.size();i++){
            Map retData = datas.get(i);
            String id = retData.get("id").toString();
            List<Map> children =  new ArrayList();
            for(int j=i+1;j<datas.size();j++){
                if(datas.get(j).containsKey("parent_id") && datas.get(j).get("parent_id").toString().equals(id)){
                    children.add(datas.get(j));
                }
            }
            if(children.size()!=0){
                retData.put("children",children);
            }

            if(retData.get("level").toString().equals("1")){
                retDatas.add(retData);
            }
        }
        System.out.println(retDatas);


    }
}
