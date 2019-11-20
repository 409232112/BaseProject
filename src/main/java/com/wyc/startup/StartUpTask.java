package com.wyc.startup;

import com.wyc.core.utils.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by wangyc on 2019/11/18.
 */
@Component
public class StartUpTask {

    private static String tempDir;

    @Value("${tempDir}")
    public void setTempDir(String tempDir) {
        this.tempDir = tempDir;
    }

    /**
     * 创建目录
     */
    @PostConstruct
    public void makeDir(){
        FileUtil.touchDir(tempDir);
        System.out.println("启动执行！");
    }
}
