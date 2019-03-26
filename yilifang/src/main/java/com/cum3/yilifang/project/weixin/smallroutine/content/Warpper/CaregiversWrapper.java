package com.cum3.yilifang.project.weixin.smallroutine.content.Warpper;

import java.util.List;
import java.util.Map;

import com.cum3.yilifang.framework.common.annotations.Wrapper;
import com.cum3.yilifang.framework.web.domain.PageEntity;
import com.cum3.yilifang.framework.web.warpper.BaseWrapper;
/**
 * 
 * 照护人员包装器
 * @author Fandy Liu
 * @created 2018年10月29日 上午11:08:38
 */
@Wrapper
public class CaregiversWrapper extends BaseWrapper {
    
    public CaregiversWrapper(Map single) {
        super(single);
    }
    public CaregiversWrapper(List multi) {
        super(multi);
    }
    public CaregiversWrapper(PageEntity pageEntity) {
        super(pageEntity);
    }
   
    @Override
    protected void wrapTheMap(Map map) {
        Integer status = map.get("status")==null?null:(Integer) map.get("status");//审核状态
        if(status != null){
            if(status == 0){
                map.put("statusName", "待审核");
            }
            if(status == 1){
                map.put("statusName", "审核通过");
            }
            if(status == 2){
                map.put("statusName", "审核不通过");
            }
        }
    }
    
}