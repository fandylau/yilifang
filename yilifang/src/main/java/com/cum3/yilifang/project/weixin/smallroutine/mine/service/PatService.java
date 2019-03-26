package com.cum3.yilifang.project.weixin.smallroutine.mine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cum3.yilifang.framework.common.support.StrFormatter;
import com.cum3.yilifang.framework.common.utils.ToolSqlTplXml;
import com.cum3.yilifang.framework.web.service.BaseService;
import com.cum3.yilifang.project.weixin.smallroutine.content.domain.Praise;
import com.cum3.yilifang.project.weixin.smallroutine.content.service.PraiseService;
import com.cum3.yilifang.project.weixin.smallroutine.mine.domain.Pat;
import com.github.pagehelper.PageHelper;

@Service("patService")
public class PatService extends BaseService<Pat>{
    @Autowired
    private PraiseService praiseService;
    /**
     * 查询随手拍
     * @return
     */
    public Pat queryPatById(){
        Pat  n  =queryById(params().pkValue());
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
        Pat  n  =super.queryById(id);
        Integer likeNum = n.getLikeNum();
        n.setLikeNum(++likeNum);
        updateSelective(n);
    }
    
    /**
     * 
     * 随手拍列表
     * @return
     */
    public Object patPageList(){
        String sql = ToolSqlTplXml.getSql(getSqlXmlNameSpace()+"."+"queryList");
        PageHelper.startPage(params().page(), params().pageSize());
        sql = StrFormatter.format(sql,params().openId());
        return getPageEntity(sqlMapper.selectList(sql));
    }
  
}
