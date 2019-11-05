package com.wyc.base.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wangyc on 2019/11/5.
 */
public class DataConvertUtil {
    /**
     *  将数据库查询出来的结果（必须有id,level,parent_id）转换成easyui tree所需要的数据结构
     * @param datas
     * @return
     */

    public static List<Map> convertResultToTreeData(List<Map> datas){
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
