package com.cum3.yilifang.project.system.job.domain;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import com.cum3.yilifang.framework.common.annotations.CreateTime;
import com.cum3.yilifang.framework.common.genid.UUIdGenId;
import com.cum3.yilifang.framework.web.domain.BaseEntity;

import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

@Table(name = "sys_job_log")
@NameStyle(Style.normal)
public class JobLog extends BaseEntity<JobLog> {
    /**
     * 描述
     */
    private static final long serialVersionUID = 1618409315160835394L;

    /**
     * 任务日志ID
     */
    @Id
    @KeySql(genId = UUIdGenId.class)
    private String jobLogId;

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
     * 日志信息
     */
    private String jobMessage;

    /**
     * 执行状态（0正常 1失败）
     */
    private String status;

    /**
     * 创建时间
     */
    @CreateTime
    private Date createTime;

    /**
     * 异常信息
     */
    private String exceptionInfo;

    /**
     * 获取任务日志ID
     *
     * @return jobLogId - 任务日志ID
     */
    public String getJobLogId() {
        return jobLogId;
    }

    /**
     * 设置任务日志ID
     *
     * @param jobLogId 任务日志ID
     */
    public void setJobLogId(String jobLogId) {
        this.jobLogId = jobLogId;
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
     * 获取日志信息
     *
     * @return jobMessage - 日志信息
     */
    public String getJobMessage() {
        return jobMessage;
    }

    /**
     * 设置日志信息
     *
     * @param jobMessage 日志信息
     */
    public void setJobMessage(String jobMessage) {
        this.jobMessage = jobMessage;
    }

    /**
     * 获取执行状态（0正常 1失败）
     *
     * @return status - 执行状态（0正常 1失败）
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置执行状态（0正常 1失败）
     *
     * @param status 执行状态（0正常 1失败）
     */
    public void setStatus(String status) {
        this.status = status;
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
     * 获取异常信息
     *
     * @return exceptionInfo - 异常信息
     */
    public String getExceptionInfo() {
        return exceptionInfo;
    }

    /**
     * 设置异常信息
     *
     * @param exceptionInfo 异常信息
     */
    public void setExceptionInfo(String exceptionInfo) {
        this.exceptionInfo = exceptionInfo;
    }
}