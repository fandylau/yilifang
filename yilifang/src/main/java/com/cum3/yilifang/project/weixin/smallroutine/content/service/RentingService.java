package com.cum3.yilifang.project.weixin.smallroutine.content.service;

import org.springframework.stereotype.Service;

import com.cum3.yilifang.framework.common.utils.ServletUtils;
import com.cum3.yilifang.framework.web.service.BaseService;
import com.cum3.yilifang.project.weixin.smallroutine.content.domain.Renting;

@Service("rentingService")
public class RentingService extends BaseService<Renting>{
    /**
     * 自定义业务 用于测试 
     * @return
     */
    public Object myBiz(){
        //直接返回所有前端传递参数
        return ServletUtils.getParameterMap();
    }
}
