package com.cum3.yilifang.project.system.job.domain;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import com.cum3.yilifang.framework.common.annotations.CreateTime;
import com.cum3.yilifang.framework.common.annotations.UpdateTime;
import com.cum3.yilifang.framework.common.genid.UUIdGenId;
import com.cum3.yilifang.framework.web.domain.BaseEntity;

import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

@Table(name = "sys_job")
@NameStyle(Style.normal)
public class Job extends BaseEntity<Job>{
    /**
     * 描述
     */
    private static final long serialVersionUID = -2519008090501818761L;

    /**
     * 任务ID
     */
    @Id
    @KeySql(genId = UUIdGenId.class)
    private String jobId;

    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 任务组名
     */
    private String jobGroup;

    /**
     * 任务方法
     */
    private String methodName;

    /**
     * 方法参数
     */
    private String methodParams;

    /**
     * cron执行表达式
     */
    private String cronExpression;
    /**
     * 几秒后执行
     */
    private Long delayMillisecond;
    /**
     * 开始执行时间 
     */
    private Date triggerStartTime;

    /**
     * 计划执行错误策略（0默认 1继续 2等待 3放弃）
     */
    private String misfirePolicy;

    /**
     * 状态（0正常 1暂停）
     */
    private String status;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    @CreateTime
    private Date createTime;
    
    
   

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    @UpdateTime
    private Date updateTime;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 获取任务ID
     *
     * @return jobId - 任务ID
     */
    public String getJobId() {
        return jobId;
    }

    /**
     * 设置任务ID
     *
     * @param jobId 任务ID
     */
    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    /**
     * 获取任务名称
     *
     * @return jobName - 任务名称
     */
    public String getJobName() {
        return jobName;
    }

    /**
     * 设置任务名称
     *
     * @param jobName 任务名称
     */
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    /**
     * 获取任务组名
     *
     * @return jobGroup - 任务组名
     */
    public String getJobGroup() {
        return jobGroup;
    }

    /**
     * 设置任务组名
     *
     * @param jobGroup 任务组名
     */
    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    /**
     * 获取任务方法
     *
     * @return methodName - 任务方法
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * 设置任务方法
     *
     * @param methodName 任务方法
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    /**
     * 获取方法参数
     *
     * @return methodParams - 方法参数
     */
    public String getMethodParams() {
        return methodParams;
    }

    /**
     * 设置方法参数
     *
     * @param methodParams 方法参数
     */
    public void setMethodParams(String methodParams) {
        this.methodParams = methodParams;
    }

    /**
     * 获取cron执行表达式
     *
     * @return cronExpression - cron执行表达式
     */
    public String getCronExpression() {
        return cronExpression;
    }

    /**
     * 设置cron执行表达式
     *
     * @param cronExpression cron执行表达式
     */
    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    /**
     * 获取计划执行错误策略（0默认 1继续 2等待 3放弃）
     *
     * @return misfirePolicy - 计划执行错误策略（0默认 1继续 2等待 3放弃）
     */
    public String getMisfirePolicy() {
        return misfirePolicy;
    }

    /**
     * 设置计划执行错误策略（0默认 1继续 2等待 3放弃）
     *
     * @param misfirePolicy 计划执行错误策略（0默认 1继续 2等待 3放弃）
     */
    public void setMisfirePolicy(String misfirePolicy) {
        this.misfirePolicy = misfirePolicy;
    }

    /**
     * 获取状态（0正常 1暂停）
     *
     * @return status - 状态（0正常 1暂停）
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态（0正常 1暂停）
     *
     * @param status 状态（0正常 1暂停）
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取创建者
     *
     * @return createBy - 创建者
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * 设置创建者
     *
     * @param createBy 创建者
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * 获取创建时间
     *
     * @return createTime - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新者
     *
     * @return updateBy - 更新者
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * 设置更新者
     *
     * @param updateBy 更新者
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * 获取更新时间
     *
     * @return updateTime - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取备注信息
     *
     * @return remark - 备注信息
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注信息
     *
     * @param remark 备注信息
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getDelayMillisecond() {
        return delayMillisecond;
    }

    public void setDelayMillisecond(Long delayMillisecond) {
        this.delayMillisecond = delayMillisecond;
    }

    public Date getTriggerStartTime() {
        return triggerStartTime;
    }

    public void setTriggerStartTime(Date triggerStartTime) {
        this.triggerStartTime = triggerStartTime;
    }
    
    
    
}