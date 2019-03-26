package com.cum3.yilifang.framework.api.oss;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import com.cum3.yilifang.framework.common.exception.ExceptionUtils;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

/**
 * 七牛云存储
 * 
 * @author Fandy Liu
 * @created 2018年9月14日 下午9:55:45
 */
public class QiniuCloudStorageService extends CloudStorageService {
    private UploadManager uploadManager;
    private String token;

    public QiniuCloudStorageService(CloudStorageConfig config) {
        this.config = config;
        this.domain = config.getQiniuDomain();
        // 初始化
        init();
    }

    private void init() {
        uploadManager = new UploadManager(new Configuration(Zone.autoZone()));
        token = Auth.create(config.getQiniuAccessKey(), config.getQiniuSecretKey())
                .uploadToken(config.getQiniuBucketName());
    }

    @Override
    public String upload(InputStream inputStream, String uploadPath, String fileName) throws IOException {
        String finalUploadPath = config.getQiniuPrefix()+"/"+uploadPath+"/"+fileName;
        try {
            Response res = uploadManager.put(IOUtils.toByteArray(inputStream), finalUploadPath, token);
            if (!res.isOK()) {
                throw new RuntimeException("上传七牛出错：" + res.toString());
            }
        } catch (Exception e) {
            throw ExceptionUtils.exp("上传文件失败，请核对七牛配置信息", e);
        }
        return config.getAliyunDomain()+"/"+finalUploadPath;
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
