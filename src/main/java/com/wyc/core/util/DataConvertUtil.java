package com.wyc.core.util;

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
            if(datas.get(i).containsKey("checked")){
                datas.get(i).put("checked",Boolean.valueOf(datas.get(i).get("checked").toString()));
            }
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

    /**
     * 将数据转换成前端柱状图所需要的数据格式必须有(name,x,y）
     * @param datas
     */
    public static Map convertResultToBarChartData(List<Map> datas){
        Map retMap =  new HashMap();
        List nameList = new ArrayList<>();
        List xList = new ArrayList<>();

        for(int i=0;i<datas.size();i++){
            if(!nameList.contains(datas.get(i).get("name"))){
                nameList.add(datas.get(i).get("name"));
            }
            if(!xList.contains(datas.get(i).get("x"))){
                xList.add(datas.get(i).get("x"));
            }
        }


        List<Map> series = new ArrayList<>();
        for(int j=0;j<nameList.size();j++){
            Map serie = new HashMap();
            List data = new ArrayList();
            for (int i = 0;i < xList.size();i++){
                boolean flag = false;
                for(int m=0;m<datas.size();m++){
                    if(datas.get(m).get("name").equals(nameList.get(j)) && datas.get(m).get("x").equals(xList.get(i))){
                        data.add(datas.get(m).get("y"));
                        flag = true;
                        break;
                    }
                }
                if(!flag){
                    data.add(0);
                }
            }
            serie.put("name",nameList.get(j));
            serie.put("type","bar");
            serie.put("data",data);
            series.add(serie);
        }

        retMap.put("legendData",nameList);
        retMap.put("xAxisData",xList);
        retMap.put("series",series);

        return retMap;
    }

    /**
     * 将数据转换成前端柱状图所需要的数据格式必须有(name,value）
     * @param datas
     */
    public static Map convertResultToPieChartData(List<Map> datas){
        Map retMap =  new HashMap();
        List nameList = new ArrayList<>();
        for(int i=0;i<datas.size();i++){
            if(!nameList.contains(datas.get(i).get("name"))){
                nameList.add(datas.get(i).get("name"));
            }
        }
        retMap.put("legendData",nameList);
        retMap.put("seriesData",datas);
        return retMap;
    }

}
