package com.cum3.yilifang.project.test.wrapper;

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
public class TTestWrapper extends BaseWrapper {
    public TTestWrapper(Map<String, Object> single) {
        super(single);
    }
    public TTestWrapper(List<Map<String, Object>> multi) {
        super(multi);
    }
    public TTestWrapper(PageEntity pageEntity) {
        super(pageEntity);
    }
   
    @Override
    protected void wrapTheMap(Map map) {
        map.put("statusName", "失效！");
    }
    
}