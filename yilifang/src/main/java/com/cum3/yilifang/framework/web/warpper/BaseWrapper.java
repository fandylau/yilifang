package com.cum3.yilifang.framework.web.warpper;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.cum3.yilifang.framework.web.domain.PageEntity;
/**
 * 数据包装器 用于返回字典或者状态位的中文
 * @author Fandy Liu
 * @created 2018年10月27日 下午3:35:09
 */
public abstract class BaseWrapper {
    protected abstract void wrapTheMap(Map map);
    private PageEntity pageResult;
    private Map<String,Object> single;
    private List multi;
    
    public BaseWrapper(Map single) {
        pageResult = null;
        this.single = null;
        multi = null;
        this.single = single;
    }

    public BaseWrapper(List multi) {
        pageResult = null;
        single = null;
        this.multi = null;
        this.multi = multi;
    }

    public BaseWrapper(PageEntity pageResult) {
        this.pageResult = null;
        single = null;
        multi = null;
        if (pageResult != null && pageResult.getDataList() != null) {
            this.pageResult = pageResult;
            multi = pageResult.getDataList();
        }
    }

    public Object wrap() {
        if (single != null)
            wrapTheMap(single);
        if (multi != null) {
            Map map;
            for (Iterator<?> iterator = multi.iterator(); iterator.hasNext(); wrapTheMap(map))
                map = (Map)iterator.next();

        }
        if (pageResult != null)
            return pageResult;
        if (single != null)
            return single;
        if (multi != null)
            return multi;
        else
            return null;
    }

    

}