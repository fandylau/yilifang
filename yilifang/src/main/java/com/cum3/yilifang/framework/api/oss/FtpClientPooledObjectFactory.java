package com.cum3.yilifang.framework.api.oss;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * ftp 连接池工厂类
 * @author Fandy Liu
 * @created 2018年10月5日 下午9:34:46
 */
public class FtpClientPooledObjectFactory implements PooledObjectFactory<FTPClient> {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    private CloudStorageConfig config;

    public FtpClientPooledObjectFactory(CloudStorageConfig config) {
        this.config = config;
    }

    @Override
    public PooledObject<FTPClient> makeObject() throws Exception {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(config.getFtpHost(), config.getFtpPort());
            ftpClient.login(config.getFtpUsername(), config.getFtpPassword());
            log.info("连接FTP服务器返回码{}", ftpClient.getReplyCode());
            ftpClient.setBufferSize(config.getBufferSize());
            ftpClient.setControlEncoding(config.getEncoding());
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.setActivePortRange(config.getFtpMinPort(), config.getFtpMaxPort());
            return new DefaultPooledObject<>(ftpClient);
        } catch (Exception e) {
            log.error("建立FTP连接失败", e);
            if (ftpClient.isAvailable()) {
                ftpClient.disconnect();
            }
            ftpClient = null;
            throw new Exception("建立FTP连接失败", e);
        }
    }

    @Override
    public void destroyObject(PooledObject<FTPClient> p) throws Exception {
        FTPClient ftpClient = getObject(p);
        if (ftpClient != null && ftpClient.isConnected()) {
            ftpClient.disconnect();
        }
    }

    @Override
    public boolean validateObject(PooledObject<FTPClient> p) {
        FTPClient ftpClient = getObject(p);
        if (ftpClient == null || !ftpClient.isConnected()) {
            return false;
        }
        try {
            ftpClient.changeWorkingDirectory(config.getFtpRootPath());
            return true;
        } catch (Exception e) {
            log.error("验证FTP连接失败::{}", e.getMessage());
            return false;
        }
    }

    @Override
    public void activateObject(PooledObject<FTPClient> p) throws Exception {
    }

    @Override
    public void passivateObject(PooledObject<FTPClient> p) throws Exception {
    }

    private FTPClient getObject(PooledObject<FTPClient> p) {
        if (p == null || p.getObject() == null) {
            return null;
        }
        return p.getObject();
    }

}