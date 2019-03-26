package com.cum3.yilifang.project.weixin.smallroutine.mine.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.cum3.yilifang.framework.common.utils.JSONUtils;
import com.cum3.yilifang.framework.web.service.BaseService;
import com.cum3.yilifang.project.weixin.smallroutine.mine.domain.Dynamics;

@Service("dynamicsService")
public class DynamicsService extends BaseService<Dynamics>{
    /**
     * 首页的动态列表
     * @param paramMap
     * @return
     */
    public Object dynamicsOfIndex(String jsonBody){
        // System.out.println(queryListBySqlTemplate("dynamicsOfIndex",paramMap.get("openId")).toString());
        String openId = JSONUtils.parseString(jsonBody, "openId");
        return queryListBySqlTemplate("dynamicsOfIndex",openId);
    }
    /**
     * 动态详情 返回 详情信息和评论数量
     * @param paramMap
     * @return
     */
    public Object dynamicsDetail(String jsonBody){
        String  dynamicId = JSONUtils.parseString(jsonBody, "dynamicId");
        Object detail =  selectOneBySqlId("dynamicsDetail",dynamicId);//按id
        Object coments = queryListBySqlTemplate("commentsOfDynamic", dynamicId,dynamicId,dynamicId);
        Map<String,Object> ret = new LinkedHashMap<>();
        ret.put("detail", detail);
        ret.put("coments", coments);
        return ret;
    }
}
