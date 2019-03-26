package com.cum3.yilifang.project.weixin.smallroutine.content.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cum3.yilifang.framework.api.ocr.ai.service.AipOcrService;
import com.cum3.yilifang.framework.api.oss.FtpCloudStorageService;
import com.cum3.yilifang.framework.web.service.BaseService;
import com.cum3.yilifang.project.weixin.smallroutine.content.domain.Notice;

@Service("noticeService")
public class NoticeService extends BaseService<Notice>{
    @Autowired
    private FtpCloudStorageService ftpCloudStorageService;
    @Autowired
    private AipOcrService aipOcrService;
    /**
     * 自定义业务 用于测试 
     * @return
     */
    public Notice queryById(Object id){
        Notice  n  =super.queryById(id);
        Integer hitNum = n.getHitNum();
        ++hitNum;
        n.setHitNum(hitNum);
        updateSelective(n);
        return n;
    }
    /**
     * 播放音频
     * @return
     * @throws Exception 
     */
    public Object playAudio() throws Exception {
        String pkValue = params().pkValue();
        Notice n = super.queryById(pkValue);
        if (n != null && StringUtils.isEmpty(n.getAudioUrls())) {
            // 生成语音附件
            StringBuffer content = new StringBuffer();
            if ("1".equals(n.getType()))  content.append("通知:");
            if ("2".equals(n.getType()))  content.append("公告:");
            content.append(n.getTitle()).append("。").append(n.getContent());
            byte[] b = aipOcrService.text2Audio(content.toString());
            String fileName = "通知公告音频.mp3";
            String uploadPath = ftpCloudStorageService.upload(b, fileName, 1);
            n.setAudioUrls(uploadPath);
            updateSelective(n);
            return n;
        }
        return n;
    }
}
