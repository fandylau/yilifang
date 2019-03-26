package com.cum3.yilifang.framework.api.oss;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.entity.ContentType;

import com.cum3.yilifang.framework.common.exception.ExceptionUtils;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.Upload;

/**
 * 腾讯云存储
 * @author Fandy Liu
 * @created 2018年9月14日 下午9:50:49
 */
public class QcloudCloudStorageService extends CloudStorageService {
	private COSClient client;
	public QcloudCloudStorageService(CloudStorageConfig config) {
		this.config = config;
		this.domain = config.getQcloudDomain();
		init();// 初始化
	}

	private void init() {
		COSCredentials credentials = new BasicCOSCredentials(config.getQcloudSecretId(), config.getQcloudSecretKey());
		// 初始化客户端配置 设置bucket所在的区域，华南：gz 华北：tj 华东：sh
		ClientConfig clientConfig = new ClientConfig(new Region(config.getQcloudRegion()));
		client = new COSClient(credentials, clientConfig);

	}

    @Override
    public String upload(InputStream inputStream, String uploadPath, String fileName) {
        String finalUploadPath = config.getQcloudPrefix()+uploadPath +"/"+fileName;
        // 腾讯云必需要以"/"开头
        if (!finalUploadPath.startsWith("/")) {
            finalUploadPath = "/" + finalUploadPath;
        }
        ExecutorService threadPool = Executors.newFixedThreadPool(32);
        // 传入一个 threadpool, 若不传入线程池, 默认 TransferManager 中会生成一个单线程的线程池。
        TransferManager transferManager = new TransferManager(client, threadPool);
        // .....(提交上传下载请求, 如下文所属)
        // 创建上传Object的Metadata
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentType(ContentType.APPLICATION_OCTET_STREAM.getMimeType());
        try {
            // 这里有个风险，因为available返回的是int类型，有长度限制，如果文件大，这个不适用。
            meta.setContentLength(inputStream.available());
        } catch (IOException e1) {
            throw ExceptionUtils.exp("文件流错误，" + e1.getMessage());
        
        }
        PutObjectRequest putObjectRequest = new PutObjectRequest(config.getQcloudBucketName(),
                finalUploadPath, inputStream, meta);
        // 本地文件上传
        Upload upload = transferManager.upload(putObjectRequest);
        // 等待传输结束（如果想同步的等待上传结束，则调用 waitForCompletion）
        try {
            upload.waitForUploadResult();
        } catch (CosServiceException e) {
            throw  ExceptionUtils.exp("服务异常，" + e.getErrorMessage());
        } catch (CosClientException e) {
            throw  ExceptionUtils.exp("客户端异常，" + e.getMessage());
        } catch (InterruptedException e) {
            throw  ExceptionUtils.exp("系统异常，" + e.getMessage());
        } finally {
            // 关闭 TransferManger
            transferManager.shutdownNow();
        }
        // 例如：https://paddy-1256559626.cosbj.myqcloud.com/images/demo/20180426/0034397501f917.png
        return config.getQcloudDomain()+ finalUploadPath;
    
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
