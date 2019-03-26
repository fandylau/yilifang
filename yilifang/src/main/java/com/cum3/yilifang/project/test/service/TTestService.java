package com.cum3.yilifang.project.test.service;

import java.util.Date;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cum3.yilifang.framework.common.utils.date.DateStyle;
import com.cum3.yilifang.framework.common.utils.date.DateUtil;
import com.cum3.yilifang.framework.datasource.TargetDataSource;
import com.cum3.yilifang.framework.web.service.BaseService;
import com.cum3.yilifang.project.system.job.domain.Job;
import com.cum3.yilifang.project.system.job.domain.ScheduleConstants;
import com.cum3.yilifang.project.system.job.utils.ScheduleUtils;
import com.cum3.yilifang.project.test.domain.TTest;

@Service("tTestService")
public class TTestService extends BaseService<TTest>{
    @Autowired
    private Scheduler scheduler;
    
    /**
     * 自定义业务 用于测试 
     * @return
     */
    public Object myBiz(){
        Job job = new Job();
        job.setJobGroup("定时器测试");
        job.setTriggerStartTime(DateUtil.stringToDate("2018-10-29 21:25:00", DateStyle.YYYY_MM_DD_HH_MM_SS));
        job.setMisfirePolicy("0");
        job.setJobId("test33333");
        job.setJobName("testTask");//job的bean名称
        job.setMethodName("taskHasParams");//调用的bean方法
        job.setMethodParams("22222222222222");
        job.setStatus(ScheduleConstants.Status.NORMAL.getValue());
        job.setCreateTime(new Date());
        ScheduleUtils.startFixedDateJob(scheduler, job);
        return null;
    }
}
