package com.cum3.yilifang.project.weixin.smallroutine.mine.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.cum3.yilifang.framework.common.exception.ExceptionUtils;
import com.cum3.yilifang.framework.common.utils.JSONUtils;
import com.cum3.yilifang.framework.web.service.BaseService;
import com.cum3.yilifang.project.system.operlog.Log;
import com.cum3.yilifang.project.system.operlog.enums.BusinessType;
import com.cum3.yilifang.project.system.operlog.enums.OperatorType;
import com.cum3.yilifang.project.weixin.smallroutine.mine.domain.Favourate;

@Service("favourateService")
public class FavourateService extends BaseService<Favourate>{
    /**
     * 新增或取消我的收藏
     * @return
     * @throws ClassNotFoundException 
     */
    @Log(title = "新增或取消收藏", businessType = BusinessType.INSERORDELETE,operatorType = OperatorType.MOBILE,isSaveRequestData= true)
    public Object addOrCancelMyFavourate(Map<String,Object> paramMap,String jsonStr ) throws Exception{
        String addOrCancel =(String) paramMap.get("addOrCancel");
        Favourate fav = (Favourate)JSONUtils.getObject(jsonStr,Favourate.class);
        if("Add".equalsIgnoreCase(addOrCancel)){
            return save(fav);
        }
        if("Cancel".equalsIgnoreCase(addOrCancel)){
            return deleteByWhere(fav);
        }
        return  ExceptionUtils.exp(" the method can not excuted ! make sure you posted the corected params! ");
    }
}
