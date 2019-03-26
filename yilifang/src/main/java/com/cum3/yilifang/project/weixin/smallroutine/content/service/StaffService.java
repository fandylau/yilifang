package com.cum3.yilifang.project.weixin.smallroutine.content.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cum3.yilifang.framework.api.ocr.ai.service.AipOcrService;
import com.cum3.yilifang.framework.api.oss.FtpCloudStorageService;
import com.cum3.yilifang.framework.common.utils.ServletUtils;
import com.cum3.yilifang.framework.web.service.BaseService;
import com.cum3.yilifang.project.weixin.smallroutine.content.domain.Staff;

@Service("staffService")
public class StaffService extends BaseService<Staff>{
    @Autowired
    private FtpCloudStorageService ftpCloudStorageService;
    @Autowired
    private AipOcrService aipOcrService;
    /**
     * 自定义业务 用于测试 
     * @return
     */
    public Object myBiz(){
        //直接返回所有前端传递参数
        return ServletUtils.getParameterMap();
    }
    
    /**
     * 播放音频
     * @return
     * @throws Exception 
     */
    public Object playAudio() throws Exception {
        String pkValue = params().pkValue();
        Staff n = super.queryById(pkValue);
        if (n != null && StringUtils.isEmpty(n.getAudioUrls())) {
            // 生成语音附件
            byte[] b = aipOcrService.text2Audio(n.getIntro());
            String fileName = "社区简介音频.mp3";
            String uploadPath = ftpCloudStorageService.upload(b, fileName, 1);
            n.setAudioUrls(uploadPath);
            updateSelective(n);
            return n;
        }
        return n;
    }
    
    
    
}
