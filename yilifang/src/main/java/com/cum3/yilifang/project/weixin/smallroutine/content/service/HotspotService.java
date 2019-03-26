package com.cum3.yilifang.project.weixin.smallroutine.content.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cum3.yilifang.framework.common.support.StrFormatter;
import com.cum3.yilifang.framework.common.utils.ToolSqlTplXml;
import com.cum3.yilifang.framework.web.service.BaseService;
import com.cum3.yilifang.project.weixin.smallroutine.content.domain.Hotspot;
import com.cum3.yilifang.project.weixin.smallroutine.content.domain.Praise;
import com.github.pagehelper.PageHelper;

@Service("hotspotService")
public class HotspotService extends BaseService<Hotspot>{
    @Autowired
    private PraiseService praiseService;
    /**
     * 查询热点详情
     * @return
     */
    public Hotspot queryHostpotById(){
        Hotspot  n  =queryById(params().pkValue());
        Integer hitNum = n.getHitNum();
        n.setHitNum(++hitNum);
        updateSelective(n);
        Praise p = new Praise();
        p.setOpenId(params().openId());
        p.setBizId(params().pkValue());
        p.setType("3");
        n.setHasPraise(praiseService.hasPraise(p));
        return n;
    }
    /**
     * 
     * 点赞数+1
     * @param id
     */
    public void  addLikeNum(Object id){
        Hotspot  o  =queryById(id);
        Integer likeNum = o.getLikeNum();
        o.setLikeNum(++likeNum);
        updateSelective(o);
    }
    /**
     * 热点列表
     * @return
     */
    public Object hotspotPageList(){
        String sql = ToolSqlTplXml.getSql(getSqlXmlNameSpace()+"."+"hotspotList");
        PageHelper.startPage(params().page(), params().pageSize());
        sql = StrFormatter.format(sql,params().openId());
        return getPageEntity(sqlMapper.selectList(sql));
    }
}
