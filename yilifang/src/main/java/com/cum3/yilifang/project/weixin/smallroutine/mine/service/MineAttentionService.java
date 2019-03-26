package com.cum3.yilifang.project.weixin.smallroutine.mine.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.cum3.yilifang.framework.common.utils.ServletUtils;
import com.cum3.yilifang.framework.web.service.BaseService;
import com.cum3.yilifang.project.weixin.smallroutine.mine.domain.MineAttention;

@Service("mineAttentionService")
public class MineAttentionService extends BaseService<MineAttention>{
    /**
     * 自定义业务 用于测试 
     * @return
     */
    public Object myBiz(Map<String,Object> paramMap){
        //直接返回所有前端传递参数
        return ServletUtils.getParameterMap();
        
    }
}
