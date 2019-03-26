package com.cum3.yilifang.framework.api.oss;

import java.io.InputStream;

import com.aliyun.oss.OSSClient;
import com.cum3.yilifang.framework.common.exception.ExceptionUtils;


/**
 * 阿里云存储
 * @author Fandy Liu
 * @created 2018年9月14日 下午9:35:41
 */
public class AliyunCloudStorageService extends CloudStorageService {
    private OSSClient client;

    public AliyunCloudStorageService(CloudStorageConfig config) {
        this.config = config;
        this.domain = config.getAliyunDomain();
        //初始化
        init();
    }
    /**
     * 初始化
     */
    private void init() {
        client = new OSSClient(config.getAliyunEndPoint(), config.getAliyunAccessKeyId(),
                config.getAliyunAccessKeySecret());
    }
    
    @Override
    public String upload(InputStream inputStream, String uploadPath, String fileName) {
        String finalUploadPath =config.getAliyunPrefix()+"/"+uploadPath+fileName;
        try {
            client.putObject(config.getAliyunBucketName(), finalUploadPath, inputStream);
        } catch (Exception e) {
            throw ExceptionUtils.exp("上传文件失败，请检查配置信息", e);
        }
        return config.getAliyunDomain()+finalUploadPath;
    }
    @Override
    public boolean download(String pathname, String filename, String localpath) throws Exception {
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public boolean delete(String pathname, String filename) throws Exception {
        // TODO Auto-generated method stub
        return false;
    }

   
}
