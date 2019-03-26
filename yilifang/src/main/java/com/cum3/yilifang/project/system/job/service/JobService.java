package com.cum3.yilifang.project.system.job.service;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cum3.yilifang.framework.web.service.BaseService;
import com.cum3.yilifang.project.system.job.domain.Job;
import com.cum3.yilifang.project.system.job.domain.ScheduleConstants;
import com.cum3.yilifang.project.system.job.mapper.JobMapper;
import com.cum3.yilifang.project.system.job.utils.ScheduleUtils;

@Service("jobService")
public class JobService extends BaseService<Job> {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private JobMapper jobMapper;

    /**
     * 项目启动时，初始化定时器
     */
    @PostConstruct
    public void init() {
        List<Job> jobList = jobMapper.selectAll();
        for (Job job : jobList) {
            CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, job.getJobId());
            // 如果不存在，则创建
            if (cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, job);
            } else {
                ScheduleUtils.updateScheduleJob(scheduler, job);
            }
        }
    }

    /**
     * 获取quartz调度器的计划任务列表
     * 
     * @param job
     *            调度信息
     * @return
     */
    public List<Job> selectJobList(Job job) {
        return queryListByWhere(job);
    }
    /**
     * 暂停任务
     * 
     * @param job 调度信息
     */
    public int pauseJob(Job job) {
        job.setStatus(ScheduleConstants.Status.PAUSE.getValue());
        int rows = jobMapper.updateByPrimaryKeySelective(job);
        if (rows > 0) {
            ScheduleUtils.pauseJob(scheduler, job.getJobId());
        }
        return rows;
    }
    
    /**
     * 恢复任务
     * 
     * @param job 调度信息
     */
    public int resumeJob(Job job) {
        job.setStatus(ScheduleConstants.Status.NORMAL.getValue());
        int rows = jobMapper.updateByPrimaryKeySelective(job);
        if (rows > 0) {
            ScheduleUtils.resumeJob(scheduler, job.getJobId());
        }
        return rows;
    }
    
    /**
     * 删除任务后，所对应的trigger也将被删除
     * @param job 调度信息
     */
    public int deleteJob(Job job){
        int rows = jobMapper.deleteByPrimaryKey(job);
        if (rows > 0) {
            ScheduleUtils.deleteScheduleJob(scheduler, job.getJobId());
        }
        return rows;
    }
    
    /**
     * 批量删除调度信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public void deleteJobByIds(String ids) {
        for (String jobId : ids.split(",")) {
            Job job = jobMapper.selectByPrimaryKey(jobId);
            deleteJob(job);
        }
    }/**
     * 任务调度状态修改
     * 
     * @param job 调度信息
     */
    public int changeStatus(Job job) {
        int rows = 0;
        String status = job.getStatus();
        if (ScheduleConstants.Status.NORMAL.getValue().equals(status)) {
            rows = resumeJob(job);
        } else if (ScheduleConstants.Status.PAUSE.getValue().equals(status)) {
            rows = pauseJob(job);
        }
        return rows;
    }

    /**
     * 立即运行任务
     * 
     * @param job
     *            调度信息
     */
    public int run(Job job) {
        return ScheduleUtils.run(scheduler, super.queryById(job.getJobId()));
    }
    
    /**
     * 新增任务
     * @param job
     *            调度信息 调度信息
     */
    public int insertJobCron(Job job) {
        job.setStatus(ScheduleConstants.Status.PAUSE.getValue());
        job.setCreateTime(new Date());
        int rows = jobMapper.insert(job);
        if (rows > 0) {
            ScheduleUtils.createScheduleJob(scheduler, job);
        }
        return rows;
    }
    
    
    
    /**
     * 更新任务的时间表达式
     * @param job 调度信息
     */
    public int updateJobCron(Job job) {
        int rows = jobMapper.updateByPrimaryKeySelective(job);
        if (rows > 0) {
            ScheduleUtils.updateScheduleJob(scheduler, job);
        }
        return rows;
    }

    
}
