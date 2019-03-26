package com.cum3.yilifang.project.weixin.smallroutine.content.service;

import org.springframework.stereotype.Service;

import com.cum3.yilifang.framework.common.support.StrFormatter;
import com.cum3.yilifang.framework.common.utils.JSONUtils;
import com.cum3.yilifang.framework.common.utils.ToolSqlTplXml;
import com.cum3.yilifang.framework.web.service.BaseService;
import com.cum3.yilifang.project.weixin.smallroutine.content.domain.Comments;
import com.github.pagehelper.PageHelper;

@Service("commentsService")
public class CommentsService extends BaseService<Comments>{
    /**
     * 描述
     * @author Fandy Liu
     * @created 2018年9月21日 上午12:41:11
     * @return
     */
    public Object queryPageCmListByBizId(){
        String sql = ToolSqlTplXml.getSql(getSqlXmlNameSpace()+"."+"queryPageCmListByBizId");
        String bizId  = JSONUtils.parseString(params().passdataJson(), "bizId");
        PageHelper.startPage(params().page(), params().pageSize());
        sql = StrFormatter.format(sql,params().openId(),bizId);
        return getPageEntity(sqlMapper.selectList(sql));
    }
    
    /**
     * 
     * 点赞数+1
     * @param id
     */
    public void  addLikeNum(Object id){
        Comments  o  =queryById(id);
        Integer likeNum = o.getLikeNum();
        o.setLikeNum(++likeNum);
        updateSelective(o);
    }
    
    /**
     * 
     * 描述
     * @author Fandy Liu
     * @created 2018年9月24日 上午3:21:22
     * @param id
     */
    public void addReplyNum(Object id){
        Comments  o  =queryById(id);
        Integer replyNum = o.getReplyNum();
        o.setReplyNum(++replyNum);
        updateSelective(o);
    }
}
