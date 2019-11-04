package com.wyc.systemmgr.service;

import java.util.List;
import java.util.Map;

public interface IModelService {
    List<Map> find(Map param);

    List<Map> findForMenu();

}