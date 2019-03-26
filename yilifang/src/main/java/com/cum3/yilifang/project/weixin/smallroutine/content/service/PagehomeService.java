package com.cum3.yilifang.project.weixin.smallroutine.content.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cum3.yilifang.framework.api.ocr.ai.service.AipOcrService;
import com.cum3.yilifang.framework.api.oss.FtpCloudStorageService;
import com.cum3.yilifang.framework.datasource.DynamicDataSource;
import com.cum3.yilifang.framework.web.service.BaseService;
import com.cum3.yilifang.project.weixin.smallroutine.content.domain.Pagehome;

@Service("pagehomeService")
public class PagehomeService extends BaseService<Pagehome>{
    @Autowired
    private FtpCloudStorageService ftpCloudStorageService;
    @Autowired
    private AipOcrService aipOcrService;
  
    /**
     * 播放音频
     * @return
     * @throws Exception 
     */
    public Object playAudio() throws Exception {
        String pkValue = params().pkValue();
        DynamicDataSource.setDataSourceKey("medical");//切换id生成所在的库
        Pagehome n = super.queryById(pkValue);
        DynamicDataSource.setDataSourceKey("basic");
        if (n != null && StringUtils.isEmpty(n.getAudioUrls())) {
            // 生成语音附件
            byte[] b = aipOcrService.text2Audio(n.getSynopsis());
            String fileName = "社区简介音频.mp3";
            String uploadPath = ftpCloudStorageService.upload(b, fileName, 1);
            n.setAudioUrls(uploadPath);
            DynamicDataSource.setDataSourceKey("medical");//切换id生成所在的库
            updateSelective(n);
            DynamicDataSource.setDataSourceKey("basic");
            return n;
        }
        return n;
    }
}
