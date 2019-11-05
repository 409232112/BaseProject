package com.wyc.base.utils;

import java.util.*;

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
        List<Map> rootNodes = new ArrayList<>();
        for(int i=0;i<datas.size();i++){
            String parent_id = datas.get(i).get("parent_id").toString();
            if(parent_id.equals("0")){
                rootNodes.add(datas.get(i));
            }
        }
        for(int i=0;i<rootNodes.size();i++){
            getChildrenList(datas,rootNodes.get(i));
        }

        return rootNodes;
    }

    /**
     * List map 根据key去重
     * @param list
     * @param mapKey
     * @return
     */
    public static List<Map> removeRepeatMapByKey(List<Map> list, String mapKey){

        //把list中的数据转换成msp,去掉同一id值多余数据，保留查找到第一个id值对应的数据
        List<Map> listMap = new ArrayList<>();
        Map<String, Map> msp = new HashMap<>();
        for(int i = list.size()-1 ; i>=0; i--){
            Map map = list.get(i);
            String id = (String)map.get(mapKey);
            map.remove(mapKey);
            msp.put(id, map);
        }
        //把msp再转换成list,就会得到根据某一字段去掉重复的数据的List<Map>
        Set<String> mspKey = msp.keySet();
        for(String key: mspKey){
            Map newMap = msp.get(key);
            newMap.put(mapKey, key);
            listMap.add(newMap);
        }
        return listMap;
    }
    private static void getChildrenList(List<Map> datas,Map parentNode){
        List<Map> children = new ArrayList<>();
        for(int i=0;i<datas.size();i++){
            if(datas.get(i).get("parent_id").toString().equals(parentNode.get("id").toString())){
                getChildrenList(datas,datas.get(i));
                children.add(datas.get(i));
            }
        }
        if(children.size()!=0){
            parentNode.put("children",children);
        }
    }

    public static void main(String args[]){
        List<Map> datas = new ArrayList<>();

        Map data = new HashMap();
        data.put("id","222");
        data.put("name","222");
        data.put("level","2");
        data.put("parent_id","111");
        datas.add(data);

        data = new HashMap();
        data.put("id","333");
        data.put("name","333");
        data.put("level","3");
        data.put("parent_id","222");
        datas.add(data);

        data = new HashMap();
        data.put("id","111");
        data.put("name","111");
        data.put("level","1");
        data.put("parent_id","0");
        datas.add(data);

        List<Map> rootNodes = new ArrayList<>();
        for(int i=0;i<datas.size();i++){
            String parent_id = datas.get(i).get("parent_id").toString();
            if(parent_id.equals("0")){
                rootNodes.add(datas.get(i));
            }
        }


        for(int i=0;i<rootNodes.size();i++){
           getChildrenList(datas,rootNodes.get(i));
        }

        System.out.println(rootNodes);
    }

}
