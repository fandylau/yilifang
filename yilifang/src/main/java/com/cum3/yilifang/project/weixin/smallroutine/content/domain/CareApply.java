package com.cum3.yilifang.project.weixin.smallroutine.content.domain;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import com.cum3.yilifang.framework.common.annotations.ComCode;
import com.cum3.yilifang.framework.common.annotations.CreateTime;
import com.cum3.yilifang.framework.common.annotations.UpdateTime;
import com.cum3.yilifang.framework.common.genid.UUIdGenId;
import com.cum3.yilifang.framework.web.domain.BaseEntity;

import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

@Table(name = "yilifang_comunity_careapply")
@NameStyle(Style.normal)
public class CareApply extends BaseEntity<CareApply>{
    /**
     * 描述
     */
    private static final long serialVersionUID = 1562258836467996872L;

    /**
     * recNo
     */
    @Id
    @KeySql(genId = UUIdGenId.class)
    private String recNo;

    /**
     * comCode
     */
    @ComCode
    private String comCode;
    /**
     * 申请人姓名
     */
    private String applyerName;

    /**
     * 护工id
     */
    private String carederId;

    /**
     * 被照护人的openId
     */
    private String openId;

    /**
     * 照护类型
     */
    private String careType;

    /**
     * 被照护人基本情况
     */
    private String caregiverInfo;

    /**
     * 照护地点
     */
    private String careAddress;

    /**
     * 被照护人电话
     */
    private String caregiverPhone;

    /**
     * 照护要求
     */
    private String careRequire;
    /**
     * 地址
     */
    private String address;            
    /**
     * 经度
     */
    private String longitude;            
    /**
     * 纬度
     */
    private String latitude;  

    /**
     * applyTime
     */
    @CreateTime
    private Date applyTime;

    /**
     * 审核状态
     */
    private Integer status;

    /**
     * 审核人
     */
    private String checker;

    /**
     * 审核时间
     */
    @UpdateTime
    private Date checkTime;

    /**
     * 审核意见
     */
    private String checkOpnions;

    /**
     * 获取recNo
     *
     * @return recNo - recNo
     */
    public String getRecNo() {
        return recNo;
    }

    /**
     * 设置recNo
     *
     * @param recNo recNo
     */
    public void setRecNo(String recNo) {
        this.recNo = recNo;
    }

    /**
     * 获取comCode
     *
     * @return comCode - comCode
     */
    public String getComCode() {
        return comCode;
    }

    /**
     * 设置comCode
     *
     * @param comCode comCode
     */
    public void setComCode(String comCode) {
        this.comCode = comCode;
    }

    /**
     * 获取护工id
     *
     * @return carederId - 护工id
     */
    public String getCarederId() {
        return carederId;
    }

    /**
     * 设置护工id
     *
     * @param carederId 护工id
     */
    public void setCarederId(String carederId) {
        this.carederId = carederId;
    }

    /**
     * 获取被照护人的openId
     *
     * @return openId - 被照护人的openId
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * 设置被照护人的openId
     *
     * @param openId 被照护人的openId
     */
    public void setOpenId(String openId) {
        this.openId = openId;
    }

    /**
     * 获取照护类型
     *
     * @return careType - 照护类型
     */
    public String getCareType() {
        return careType;
    }

    /**
     * 设置照护类型
     *
     * @param careType 照护类型
     */
    public void setCareType(String careType) {
        this.careType = careType;
    }

    /**
     * 获取被照护人基本情况
     *
     * @return caregiverInfo - 被照护人基本情况
     */
    public String getCaregiverInfo() {
        return caregiverInfo;
    }

    /**
     * 设置被照护人基本情况
     *
     * @param caregiverInfo 被照护人基本情况
     */
    public void setCaregiverInfo(String caregiverInfo) {
        this.caregiverInfo = caregiverInfo;
    }

    /**
     * 获取照护地点
     *
     * @return careAddress - 照护地点
     */
    public String getCareAddress() {
        return careAddress;
    }

    /**
     * 设置照护地点
     *
     * @param careAddress 照护地点
     */
    public void setCareAddress(String careAddress) {
        this.careAddress = careAddress;
    }

    /**
     * 获取被照护人电话
     *
     * @return caregiverPhone - 被照护人电话
     */
    public String getCaregiverPhone() {
        return caregiverPhone;
    }

    /**
     * 设置被照护人电话
     *
     * @param caregiverPhone 被照护人电话
     */
    public void setCaregiverPhone(String caregiverPhone) {
        this.caregiverPhone = caregiverPhone;
    }

    /**
     * 获取照护要求
     *
     * @return careRequire - 照护要求
     */
    public String getCareRequire() {
        return careRequire;
    }

    /**
     * 设置照护要求
     *
     * @param careRequire 照护要求
     */
    public void setCareRequire(String careRequire) {
        this.careRequire = careRequire;
    }

    /**
     * 获取applyTime
     *
     * @return applyTime - applyTime
     */
    public Date getApplyTime() {
        return applyTime;
    }

    /**
     * 设置applyTime
     *
     * @param applyTime applyTime
     */
    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取审核人
     *
     * @return checker - 审核人
     */
    public String getChecker() {
        return checker;
    }

    /**
     * 设置审核人
     *
     * @param checker 审核人
     */
    public void setChecker(String checker) {
        this.checker = checker;
    }

    /**
     * 获取审核时间
     *
     * @return checkTime - 审核时间
     */
    public Date getCheckTime() {
        return checkTime;
    }

    /**
     * 设置审核时间
     *
     * @param checkTime 审核时间
     */
    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    /**
     * 获取审核意见
     *
     * @return checkOpnions - 审核意见
     */
    public String getCheckOpnions() {
        return checkOpnions;
    }

    /**
     * 设置审核意见
     *
     * @param checkOpnions 审核意见
     */
    public void setCheckOpnions(String checkOpnions) {
        this.checkOpnions = checkOpnions;
    }

    public String getApplyerName() {
        return applyerName;
    }

    public void setApplyerName(String applyerName) {
        this.applyerName = applyerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    
}