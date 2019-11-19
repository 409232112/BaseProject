package com.wyc.base.startup;

import com.wyc.base.register.StartUpRegister;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wangyc on 2019/11/18.
 */
@Component
public class StartUpInit {
    @PostConstruct
    public static void start() throws Exception{
        System.out.println("启动执行！");

        /**
        List<String> classNames = StartUpRegister.getClassList();
        ExecutorService executor = Executors.newFixedThreadPool(classNames.size());
        for(int i=0;i<classNames.size();i++){
            executor.execute(new StartUpThread(classNames.get(i)));
        }*/
    }
}
