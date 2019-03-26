package com.cum3.yilifang.framework.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cum3.yilifang.framework.api.oss.AliyunCloudStorageService;
import com.cum3.yilifang.framework.api.oss.CloudService;
import com.cum3.yilifang.framework.api.oss.CloudStorageConfig;
import com.cum3.yilifang.framework.api.oss.CloudStorageService;
import com.cum3.yilifang.framework.api.oss.FtpCloudStorageService;
import com.cum3.yilifang.framework.api.oss.QcloudCloudStorageService;
import com.cum3.yilifang.framework.api.oss.QiniuCloudStorageService;
/**
 * 
 * oss 服务配置
 * @author Fandy Liu
 * @created 2018年9月15日 上午12:04:43
 */
@Configuration
public class CloudStorageServiceConfig {
    @Autowired
    private CloudStorageConfig config;
    @Bean
    public CloudStorageService cloudStorageService(){
        if (config.getType() == CloudService.QINIU.getValue()) {
            return new QiniuCloudStorageService(config);
        } else if (config.getType() ==CloudService.ALIYUN.getValue()) {
            return new AliyunCloudStorageService(config);
        } else if (config.getType() ==CloudService.QCLOUD.getValue()) {
            return new QcloudCloudStorageService(config);
        }else if(config.getType() ==CloudService.FTP.getValue()){
            return new FtpCloudStorageService(config);
        }
        return null;
    }
}
