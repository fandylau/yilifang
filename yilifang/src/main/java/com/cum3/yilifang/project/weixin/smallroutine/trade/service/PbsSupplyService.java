package com.cum3.yilifang.project.weixin.smallroutine.trade.service;

import java.util.Date;

import javax.persistence.Transient;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.cum3.yilifang.framework.web.service.BaseService;
import com.cum3.yilifang.project.system.job.domain.Job;
import com.cum3.yilifang.project.system.job.domain.ScheduleConstants;
import com.cum3.yilifang.project.system.job.utils.ScheduleUtils;
import com.cum3.yilifang.project.weixin.smallroutine.trade.domain.PbsSupply;

@Service("pbsSupplyService")
public class PbsSupplyService extends BaseService<PbsSupply>{

    

    @Autowired
    private Scheduler scheduler;
    
    /**
     * 订单有效
     */
    public static final int REQUIRE_STATUS_EFECTIVE = 1;
    /**
     * 订单过期
     */
    public static final int REQUIRE_STATUS_OVERDUE = 2;
    
    /**
     * 需求状态 -- 未接单
     */
    public static final int REQUIRE_TAKINGSTATUS_NORECIVE = 0;
    /**
     * 需求状态 -- 已接单
     */
    public static final int REQUIRE_TAKINGSTATUS_RECIVED= 1;

    /**
     * 完成状态 -- 未完成
     */
    public static final int REQUIRE_FINISHSTATUS_NOFINISH= 0;
    
    /**
     * 完成状态 -- 已完成
     */
    public static final int REQUIRE_FINISHSTATUS_FINISHED= 1;
    
    @Transient
    public boolean saveOrUpdateSupply() throws Exception{
        PbsSupply supply = params().entity(PbsSupply.class);
        String recNo = supply.getRecNo();
        if(!StringUtils.isEmpty(recNo)){//已经存在 删除原先的定时任务
            ScheduleUtils.deleteScheduleJob(scheduler, recNo);
        }
        boolean result = super.saveOrUpdate(supply);
        Job job = new Job();
        job.setJobId(supply.getRecNo());
        job.setJobGroup("供货发布");
        job.setTriggerStartTime(supply.getEffecEndTime());
        job.setMisfirePolicy("0");
        job.setJobName("pbsSupplyTask");//job的bean名称
        job.setMethodName("updateStatusOverdue");//调用的bean方法
        job.setMethodParams(supply.getRecNo());
        job.setStatus(ScheduleConstants.Status.NORMAL.getValue());
        job.setCreateTime(new Date());
        ScheduleUtils.startFixedDateJob(scheduler, job);
        return result;
        
    }
    
    
    /**
     * 过期
     * @author Fandy Liu
     * @created 2018年10月25日 下午11:03:55
     */
    public void overdue(PbsSupply req){
        req = queryOne(req);
        req.setStatus(REQUIRE_STATUS_OVERDUE);//过期
        updateSelective(req);
    }
    /**
     * 已接单
     * @author Fandy Liu
     * @created 2018年10月25日 下午11:03:55
     */
    public void recived(PbsSupply req){
        req = queryOne(req);
        req.setTakingStatus(REQUIRE_TAKINGSTATUS_RECIVED);//已接单
        updateSelective(req);
    }
    /**
     * 未接单
     * @author Fandy Liu
     * @created 2018年10月25日 下午11:14:36
     * @param req
     */
    public void noRecived(PbsSupply req){
        req = queryOne(req);
        req.setTakingStatus(REQUIRE_TAKINGSTATUS_NORECIVE);//未接单
        updateSelective(req);
    }
   /**
    * 已完成
    * @author Fandy Liu
    * @created 2018年10月25日 下午11:11:19
    * @param req
    */
    public void finish(PbsSupply req) {
        req = queryOne(req);
        req.setFinishStatus(REQUIRE_FINISHSTATUS_FINISHED);//已完成
        updateSelective(req);
    }
    /**
     * 未完成
     * @author Fandy Liu
     * @created 2018年10月25日 下午11:12:15
     * @param req
     */
    public void unFinish(PbsSupply req){
        req = queryOne(req);
        req.setFinishStatus(REQUIRE_FINISHSTATUS_NOFINISH);
        updateSelective(req);
    }

}
