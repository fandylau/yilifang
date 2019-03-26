package com.cum3.yilifang.project.system.job.task;

import org.springframework.stereotype.Component;

/**
 * 定时任务调度测试
 * 
 * @author Fandy Lau
 */
@Component("testTask")
public class TestTask {

    public void taskHasParams(String params) {
        System.out.println("执行有参方法：" + params);
    }

    public void taskNoParams() {
        System.out.println("执行无参方法");
    }

}
