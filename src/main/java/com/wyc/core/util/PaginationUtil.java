package com.wyc.core.util;

import com.github.pagehelper.PageInfo;
import com.wyc.core.base.entity.Pagination;

import java.util.List;

/**
 * Created by wangyc on 2019/11/1.
 */
public class PaginationUtil {

    public static Pagination getPageList(List list){
        PageInfo pageInfo = new PageInfo(list);
        return new Pagination(pageInfo.getTotal(),pageInfo.getList());
    }
}
