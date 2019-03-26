package com.cum3.yilifang.project.weixin.smallroutine.content.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cum3.yilifang.framework.common.support.StrFormatter;
import com.cum3.yilifang.framework.common.utils.ToolSqlTplXml;
import com.cum3.yilifang.framework.web.service.BaseService;
import com.cum3.yilifang.project.weixin.smallroutine.content.domain.News;
import com.cum3.yilifang.project.weixin.smallroutine.content.domain.Praise;
import com.github.pagehelper.PageHelper;

@Service("newsService")
public class NewsService extends BaseService<News>{
    @Autowired
    private PraiseService praiseService;
    /**
     * 查询新闻详情
     * @return
     */
    public News queryNewsById(){
        News  n  =queryById(params().pkValue());
        Integer hitNum = n.getHitNum();
        n.setHitNum(++hitNum);
        updateSelective(n);
        Praise p = new Praise();
        p.setOpenId(params().openId());
        p.setBizId(params().pkValue());
        p.setType("2");
        n.setHasPraise(praiseService.hasPraise(p));
        return n;
    }
    /**
     * 
     * 点赞数+1
     * @param id
     */
    public void  addLikeNum(Object id){
        News  n  =super.queryById(id);
        Integer likeNum = n.getLikeNum();
        n.setLikeNum(++likeNum);
        updateSelective(n);
    }
    /**
     * 
     * 新闻列表
     * @return
     */
    public Object newsPageList(){
        String sql = ToolSqlTplXml.getSql(getSqlXmlNameSpace()+"."+"newsList");
        // 设置分页条件
        PageHelper.startPage(params().page(), params().pageSize());
        sql = StrFormatter.format(sql,params().openId());
        return getPageEntity(sqlMapper.selectList(sql));
    }
}
