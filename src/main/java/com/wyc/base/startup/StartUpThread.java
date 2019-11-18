package com.wyc.base.startup;

import org.apache.log4j.Logger;

/**
 * Created by wangyc on 2019/11/18.
 */
public class StartUpThread extends Thread {

    static final Logger LOG = Logger.getLogger(StartUpThread.class);
    private String className;
    public StartUpThread(String className){
        this.className = className;
    }
    public void run(){
        try{
            Class<?> startupClass = Class.forName(className);
            Object o =  startupClass.newInstance();
            startupClass.getMethod("run").invoke(o);
        }catch (Exception e){
            LOG.error(e.getMessage() + " is not exist!");
        }
    }
}
