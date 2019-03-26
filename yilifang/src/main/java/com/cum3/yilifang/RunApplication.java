package com.cum3.yilifang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.cum3.yilifang.framework.datasource.DynamicDataSourceRegister;

/**
 * 启动程序
 * @author Fandy Lau
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@Import({DynamicDataSourceRegister.class}) 
@EnableTransactionManagement // 启注解事务管理
public class RunApplication {
    public static void main(String[] args) {
        //设置重启开关 不必每次修改都要重启
        System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(RunApplication.class, args);
        System.out.println("========================================启动成功=========================================");
    }
    
    /**
     * 
     * 启动时清理本地的附件 和redis缓存中附件md5值
     * @param storageService
     * @return
     */
   /* @Bean
    CommandLineRunner init(final StorageService storageService) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                storageService.deleteAll();
                storageService.init();
            }
        };
    }*/
}