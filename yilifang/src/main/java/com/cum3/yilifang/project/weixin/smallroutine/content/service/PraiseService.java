package com.cum3.yilifang.project.weixin.smallroutine.content.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cum3.yilifang.framework.web.service.BaseService;
import com.cum3.yilifang.project.weixin.smallroutine.content.domain.Praise;
import com.cum3.yilifang.project.weixin.smallroutine.mine.service.PatService;

@Service("praiseService")
public class PraiseService extends BaseService<Praise> {
    @Autowired
    private NewsService newsService;
    @Autowired
    private CommentsService commentsService;
    @Autowired
    private HotspotService hotspotService;
    @Autowired
    private PatService patService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private ReplyService replyService;
    
    @Autowired
    private RecuperateService recuperateService;
    /**
     * 点赞
     * @return
     * @throws ClassNotFoundException
     */
    @Transactional
    public Object pushPraise() throws ClassNotFoundException {
        Praise p = (Praise) params().entity(Praise.class);
        String type = p.getType();
        Praise ret = queryOne(p);
        if (ret == null) {
            if("1".equals(type)){//评论-->点赞数量+1
                commentsService.addLikeNum(p.getBizId());
            }
            if("2".equals(type)){//新闻-->点赞数量+1
                newsService.addLikeNum(p.getBizId());
            }
            if("3".equals(type)){//热点-->点赞数量+1
                hotspotService.addLikeNum(p.getBizId());
            }
            if("4".equals(type)){//随手拍-->点赞数量+1
                patService.addLikeNum(p.getBizId());
            }
            if("5".equals(type)){//回复-->点赞数量+1
                replyService.addLikeNum(p.getBizId());
            }
            if("6".equals(type)){//活动-->点赞数量+1
                activityService.addLikeNum(p.getBizId());
            }
            if("7".equals(type)){//活动-->点赞数量+1
                recuperateService.addLikeNum(p.getBizId());
            }
            return super.save(p);
        }
        return 0;
    }
    /**
     * 
     * 描述 查询是否点过赞
     * @param bizId
     * @return
     */
    public boolean hasPraise(Praise p){
        return queryOne(p) !=null;
    }
    
}
