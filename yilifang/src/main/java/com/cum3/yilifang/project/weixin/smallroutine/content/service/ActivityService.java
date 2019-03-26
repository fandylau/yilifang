package com.cum3.yilifang.project.weixin.smallroutine.content.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cum3.yilifang.framework.common.support.StrFormatter;
import com.cum3.yilifang.framework.common.utils.ToolSqlTplXml;
import com.cum3.yilifang.framework.web.service.BaseService;
import com.cum3.yilifang.project.weixin.smallroutine.content.domain.Activity;
import com.cum3.yilifang.project.weixin.smallroutine.content.domain.Praise;
import com.github.pagehelper.PageHelper;

@Service("activityService")
public class ActivityService extends BaseService<Activity>{
    @Autowired
    private PraiseService praiseService;
    /**
     * 查询随手拍
     * @return
     */
    public Activity queryPatById(){
        Activity  n  =queryById(params().pkValue());
        Integer hitNum = n.getHitNum();
        n.setHitNum(++hitNum);
        mapper.updateByPrimaryKey(n);//浏览数量跟新
        Praise p = new Praise();
        p.setOpenId(params().openId());
        p.setBizId(params().pkValue());
        p.setType("2");
        n.setHasPraise(praiseService.hasPraise(p));//是否点过赞了
        return n;
    }
    
    /**
     * 
     * 点赞数+1
     * @param id
     */
    public void  addLikeNum(Object id){
        Activity  n  =super.queryById(id);
        Integer likeNum = n.getLikeNum();
        n.setLikeNum(++likeNum);
        mapper.updateByPrimaryKeySelective(n);
    }
    
    /**
     * 活动列表
     * @return
     */
    public Object patPageList(){
        String sql = ToolSqlTplXml.getSql(getSqlXmlNameSpace()+"."+"queryList");
        PageHelper.startPage(params().page(), params().pageSize());//分页
        sql = StrFormatter.format(sql, params().openId());
        return getPageEntity(sqlMapper.selectList(sql));
    }
  
}
