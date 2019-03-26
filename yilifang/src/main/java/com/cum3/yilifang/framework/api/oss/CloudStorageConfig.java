package com.cum3.yilifang.framework.api.oss;

import java.io.Serializable;

import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * 云存储配置信息
 * @author Fandy Liu
 * @created 2018年9月14日 下午9:54:27
 */
@Component
@Data
public class CloudStorageConfig implements Serializable {
    private static final long serialVersionUID = 1L;
    @Value("${oss.type}")
    private Integer type;
    @Value("${oss.qiniuDomain}")
    private String qiniuDomain;
    //七牛路径前缀
    @Value("${oss.qiniuPrefix}")
    private String qiniuPrefix;
    //七牛ACCESS_KEY
    @Value("${oss.qiniuAccessKey}")
    private String qiniuAccessKey;
    //七牛SECRET_KEY
    @Value("${oss.qiniuSecretKey}")
    private String qiniuSecretKey;
    //七牛存储空间名
    @Value("${oss.qiniuBucketName}")
    private String qiniuBucketName;

    //阿里云绑定的域名
    @Value("${oss.aliyunDomain}")
    private String aliyunDomain;
    //阿里云路径前缀
    @Value("${oss.aliyunPrefix}")
    private String aliyunPrefix;
    //阿里云EndPoint
    @Value("${oss.aliyunEndPoint}")
    private String aliyunEndPoint;
    //阿里云AccessKeyId
    @Value("${oss.aliyunAccessKeyId}")
    private String aliyunAccessKeyId;
    //阿里云AccessKeySecret
    @Value("${oss.aliyunAccessKeySecret}")
    private String aliyunAccessKeySecret;
    //阿里云BucketName
    @Value("${oss.aliyunBucketName}")
    private String aliyunBucketName;

    //腾讯云绑定的域名
    @Value("${oss.qcloudDomain}")
    private String qcloudDomain;
    //腾讯云路径前缀
    @Value("${oss.qcloudPrefix}")
    private String qcloudPrefix;
    //腾讯云AppId
    @Value("${oss.qcloudAppId}")
    private Integer qcloudAppId;
    //腾讯云SecretId
    @Value("${oss.qcloudSecretId}")
    private String qcloudSecretId;
    //腾讯云SecretKey
    @Value("${oss.qcloudSecretKey}")
    private String qcloudSecretKey;
    //腾讯云BucketName
    @Value("${oss.qcloudBucketName}")
    private String qcloudBucketName;
    //腾讯云COS所属地区
    @Value("${oss.qcloudRegion}")
    private String qcloudRegion;
    
    //ftp相关配置
    @Value("${oss.ftpHost}")
    private String ftpHost;
    @Value("${oss.ftpPort}")
    private int ftpPort = FTPClient.DEFAULT_PORT;
    @Value("${oss.ftpUsername}")
    private String ftpUsername;
    @Value("${oss.ftpPassword}")
    private String ftpPassword;
    private int bufferSize = 8096;
    /**
     * linux 开启的文件传输端口
     */
    @Value("${oss.ftpMinPort}")
    private int ftpMinPort;
    @Value("${oss.ftpMaxPort}")
    private int ftpMaxPort;
    /**
     * 初始化连接数
     */
    @Value("${oss.initialSize}")
    private Integer initialSize = 5;
    @Value("${oss.encoding}")
    private String encoding;
    /**
     * ftp文件目录
     */
    @Value("${oss.ftpRootPath}")
    private String ftpRootPath;
    @Value("${oss.ftpDomain}")
    private String ftpDomain;

   
}
