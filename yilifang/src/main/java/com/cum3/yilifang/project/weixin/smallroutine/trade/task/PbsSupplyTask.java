package com.cum3.yilifang.project.weixin.smallroutine.trade.task;

import org.springframework.stereotype.Component;

import com.cum3.yilifang.framework.common.utils.SpringUtils;
import com.cum3.yilifang.project.weixin.smallroutine.trade.domain.PbsSupply;
import com.cum3.yilifang.project.weixin.smallroutine.trade.service.PbsSupplyService;

/**
 * 需求发布定时任务调度测试
 * 
 * @author Fandy Lau
 */
@Component("pbsSupplyTask")
public class PbsSupplyTask {
    
    
    /**
     * 更新状态过期
     * @author Fandy Liu
     * @created 2018年10月29日 下午8:38:32
     * @param params
     */
    public void updateStatusOverdue(String params) {
        PbsSupplyService pbsSupplyService =   SpringUtils.getBean(PbsSupplyService.class);
        PbsSupply pbsSupply = pbsSupplyService.queryById(params);
        if(pbsSupply.getTakingStatus() ==0){
            pbsSupply.setStatus(0);//设置需求已过期
            pbsSupplyService.updateSelective(pbsSupply);
        }
       
    }
}
