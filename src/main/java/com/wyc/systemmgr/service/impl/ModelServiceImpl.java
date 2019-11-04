package com.wyc.systemmgr.service.impl;

import com.wyc.systemmgr.dao.ModelDao;
import com.wyc.systemmgr.service.IModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ModelServiceImpl implements IModelService {
    @Autowired
    private ModelDao mdeolDao;

    @Override
    public List<Map> find(Map params){
        return mdeolDao.find(params);
    }

    @Override
    public List<Map> findForMenu(){
        List<Map> datas = mdeolDao.find(null);

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

        return retDatas;
    }
}