package com.cum3.yilifang.project.system.operlog.domain;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import com.cum3.yilifang.framework.common.genid.UUIdGenId;
import com.cum3.yilifang.framework.web.domain.BaseEntity;

import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

@Table(name = "sys_oper_log")
@NameStyle(Style.normal)
public class OperLog extends BaseEntity<OperLog> {
    /**
     * 描述
     */
    private static final long serialVersionUID = -6411568983303555693L;

    /**
     * 主键
     */
    @Id
    @KeySql(genId = UUIdGenId.class)
    private String id;

    /**
     * 模块标题
     */
    private String title;

    /**
     * 功能请求
     */
    private Integer businessType;

    /**
     * 操作人类型 0 其他 1后台用户 2手机端用户
     */
    private Integer operatorType;

    /**
     * 方法名称
     */
    private String method;

    /**
     * 来源渠道
     */
    private String channel;

    /**
     * 登录账号
     */
    private String loginName;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 请求URL
     */
    private String operUrl;

    /**
     * 主机地址
     */
    private String operIp;
    
    /**
     * 操作地址
     */
    private String operLocation;

    public String getOperLocation() {
        return operLocation;
    }

    public void setOperLocation(String operLocation) {
        this.operLocation = operLocation;
    }

    /**
     * 请求参数
     */
    private String operParam;

    /**
     * 操作状态 1正常 0异常
     */
    private Integer status;

    /**
     * 错误消息
     */
    private String errorMsg;

    /**
     * 操作时间
     */
    private Date operTime;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取模块标题
     *
     * @return title - 模块标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置模块标题
     *
     * @param title 模块标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取功能请求
     *
     * @return businessType - 功能请求
     */
    public Integer getBusinessType() {
        return businessType;
    }

    /**
     * 设置功能请求
     *
     * @param businessType 功能请求
     */
    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    /**
     * 获取操作人类型 0 其他 1后台用户 2手机端用户
     *
     * @return operatorType - 操作人类型 0 其他 1后台用户 2手机端用户
     */
    public Integer getOperatorType() {
        return operatorType;
    }

    /**
     * 设置操作人类型 0 其他 1后台用户 2手机端用户
     *
     * @param operatorType 操作人类型 0 其他 1后台用户 2手机端用户
     */
    public void setOperatorType(Integer operatorType) {
        this.operatorType = operatorType;
    }

    /**
     * 获取方法名称
     *
     * @return method - 方法名称
     */
    public String getMethod() {
        return method;
    }

    /**
     * 设置方法名称
     *
     * @param method 方法名称
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * 获取来源渠道
     *
     * @return channel - 来源渠道
     */
    public String getChannel() {
        return channel;
    }

    /**
     * 设置来源渠道
     *
     * @param channel 来源渠道
     */
    public void setChannel(String channel) {
        this.channel = channel;
    }

    /**
     * 获取登录账号
     *
     * @return loginName - 登录账号
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * 设置登录账号
     *
     * @param loginName 登录账号
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    /**
     * 获取部门名称
     *
     * @return deptName - 部门名称
     */
    public String getDeptName() {
        return deptName;
    }

    /**
     * 设置部门名称
     *
     * @param deptName 部门名称
     */
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    /**
     * 获取请求URL
     *
     * @return operUrl - 请求URL
     */
    public String getOperUrl() {
        return operUrl;
    }

    /**
     * 设置请求URL
     *
     * @param operUrl 请求URL
     */
    public void setOperUrl(String operUrl) {
        this.operUrl = operUrl;
    }

    /**
     * 获取主机地址
     *
     * @return operIp - 主机地址
     */
    public String getOperIp() {
        return operIp;
    }

    /**
     * 设置主机地址
     *
     * @param operIp 主机地址
     */
    public void setOperIp(String operIp) {
        this.operIp = operIp;
    }

    /**
     * 获取请求参数
     *
     * @return operParam - 请求参数
     */
    public String getOperParam() {
        return operParam;
    }

    /**
     * 设置请求参数
     *
     * @param operParam 请求参数
     */
    public void setOperParam(String operParam) {
        this.operParam = operParam;
    }

    /**
     * 获取操作状态 1正常 0异常
     *
     * @return status - 操作状态 1正常 0异常
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置操作状态 1正常 0异常
     *
     * @param status 操作状态 1正常 0异常
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取错误消息
     *
     * @return errorMsg - 错误消息
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * 设置错误消息
     *
     * @param errorMsg 错误消息
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    /**
     * 获取操作时间
     *
     * @return operTime - 操作时间
     */
    public Date getOperTime() {
        return operTime;
    }

    /**
     * 设置操作时间
     *
     * @param operTime 操作时间
     */
    public void setOperTime(Date operTime) {
        this.operTime = operTime;
    }

    
}