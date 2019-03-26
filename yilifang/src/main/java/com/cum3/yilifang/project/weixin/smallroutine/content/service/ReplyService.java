package com.cum3.yilifang.project.weixin.smallroutine.content.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cum3.yilifang.framework.web.service.BaseService;
import com.cum3.yilifang.project.weixin.smallroutine.content.domain.Comments;
import com.cum3.yilifang.project.weixin.smallroutine.content.domain.Reply;

@Service("replyService")
public class ReplyService extends BaseService<Reply>{
    @Autowired
    private CommentsService commentsService;
    @Transactional
    public Integer saveReply(){
        Reply r = (Reply) params().entity(Reply.class);
        Comments o = commentsService.queryById(r.getBizId());
        Integer replyNum = o.getReplyNum();
        o.setReplyNum(++replyNum);
        commentsService.updateSelective(o);
        return super.save(r);
    }
    
    /**
     * 
     * 点赞数+1
     * @param id
     */
    public void  addLikeNum(Object id){
        Reply n =queryById(id);
        Integer likeNum = n.getLikeNum();
        n.setLikeNum(++likeNum);
        updateSelective(n);
    }
}
