package com.cum3.yilifang.project.weixin.smallroutine.trade.task;

import org.springframework.stereotype.Component;

import com.cum3.yilifang.framework.common.utils.SpringUtils;
import com.cum3.yilifang.project.weixin.smallroutine.trade.domain.PbsRequire;
import com.cum3.yilifang.project.weixin.smallroutine.trade.service.PbsRequireService;

/**
 * 需求发布定时任务调度测试
 * 
 * @author Fandy Lau
 */
@Component("pbsRequireTask")
public class PbsRequireTask {
    
    
    /**
     * 更新状态过期
     * @author Fandy Liu
     * @created 2018年10月29日 下午8:38:32
     * @param params
     */
    public void updateStatusOverdue(String params) {
        PbsRequireService pbsRequireService =   SpringUtils.getBean(PbsRequireService.class);
        PbsRequire pbsRequire = pbsRequireService.queryById(params);
        if(pbsRequire.getTakingStatus()==0){
            pbsRequire.setStatus(0);//设置需求已过期
            pbsRequireService.updateSelective(pbsRequire);
        }
       
    }
}
