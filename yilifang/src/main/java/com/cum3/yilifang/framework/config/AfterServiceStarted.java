package com.cum3.yilifang.framework.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.cum3.yilifang.framework.common.utils.ToolSqlTplXml;
@Component
public class AfterServiceStarted implements ApplicationRunner{
    /**
     * 会在服务启动完成后立即执行
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
       //加载sql模板存入缓存
        ToolSqlTplXml.init(true);
    }
}