package com.cum3.yilifang.framework.api.oss;

/**
 * 云服务商
 */
public enum CloudService {
    /**
     * 七牛云
     */
    QINIU(1),
    /**
     * 阿里云
     */
    ALIYUN(2),
    /**
     * 腾讯云
     */
    QCLOUD(3),
    /**
     * ftp
     */
    FTP(4);

    private int value;

    private CloudService(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
