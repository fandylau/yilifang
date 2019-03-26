package com.cum3.yilifang.project.weixin.smallroutine.trade.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cum3.yilifang.framework.common.annotations.ComCode;
import com.cum3.yilifang.framework.common.annotations.CreateTime;
import com.cum3.yilifang.framework.common.annotations.Pictures;
import com.cum3.yilifang.framework.common.genid.UUIdGenId;
import com.cum3.yilifang.framework.common.utils.date.DateStyle;
import com.cum3.yilifang.framework.common.utils.date.DateUtil;
import com.cum3.yilifang.framework.web.domain.BaseEntity;

import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

@Table(name = "yilifang_comunity_pbsrequire_bak")
@NameStyle(Style.normal)
public class PbsRequireBak extends BaseEntity<PbsRequireBak>{
    /**
     * 描述
     */
    private static final long serialVersionUID = -269732028544890868L;

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
     * 标题
     */
    private String title;

    /**
     * 描述
     */
    private String description;

    /**
     * 需求类型
     */
    private String requireType;

    /**
     * 赏金
     */
    private BigDecimal money;

    /**
     * 位置
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
     * 图片
     */
    @Pictures
    private String picUrls;

    /**
     * 有效期
     */
    private Date effecEndTime;

    /**
     * 发布人
     */
    private String userName;

    /**
     * 头像
     */
    private String protrait;

    /**
     * openId
     */
    private String openId;

    /**
     * 联系人
     */
    private String contacts;

    /**
     * 联系电话
     */
    private String contactsPhone;

    /**
     * 发布时间
     */
    @CreateTime
    private Date publishTime;

    /**
     * 过期状态 1 有效  2过期
     */
    private Integer status;
    /**
     * 接单状态  0 未接单  1 已接单
     */
    private Integer takingStatus;
    /**
     * 完成状态 0 未完成   1 已完成
     */
    private Integer finishStatus;
    
    /**
     * 有效天数
     */
    @Transient
    private String effectDays;
    
    

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
     * 获取标题
     *
     * @return title - 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取描述
     *
     * @return description - 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置描述
     *
     * @param description 描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取需求类型
     *
     * @return requireType - 需求类型
     */
    public String getRequireType() {
        return requireType;
    }

    /**
     * 设置需求类型
     *
     * @param requireType 需求类型
     */
    public void setRequireType(String requireType) {
        this.requireType = requireType;
    }

    /**
     * 获取赏金
     *
     * @return money - 赏金
     */
    public BigDecimal getMoney() {
        return money;
    }

    /**
     * 设置赏金
     *
     * @param money 赏金
     */
    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    /**
     * 获取位置
     *
     * @return address - 位置
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置位置
     *
     * @param address 位置
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取经度
     *
     * @return longitude - 经度
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * 设置经度
     *
     * @param longitude 经度
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * 获取纬度
     *
     * @return latitude - 纬度
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * 设置纬度
     *
     * @param latitude 纬度
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * 获取有效期
     *
     * @return effecEndTime - 有效期
     */
    public Date getEffecEndTime() {
        return effecEndTime;
    }

    /**
     * 设置有效期
     *
     * @param effecEndTime 有效期
     */
    public void setEffecEndTime(Date effecEndTime) {
        this.effecEndTime = effecEndTime;
    }

    /**
     * 获取发布人
     *
     * @return userName - 发布人
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置发布人
     *
     * @param userName 发布人
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取头像
     *
     * @return protrait - 头像
     */
    public String getProtrait() {
        return protrait;
    }

    /**
     * 设置头像
     *
     * @param protrait 头像
     */
    public void setProtrait(String protrait) {
        this.protrait = protrait;
    }

    /**
     * 获取openId
     *
     * @return openId - openId
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * 设置openId
     *
     * @param openId openId
     */
    public void setOpenId(String openId) {
        this.openId = openId;
    }

    /**
     * 获取联系人
     *
     * @return contacts - 联系人
     */
    public String getContacts() {
        return contacts;
    }

    /**
     * 设置联系人
     *
     * @param contacts 联系人
     */
    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    /**
     * 获取联系电话
     *
     * @return contactsPhone - 联系电话
     */
    public String getContactsPhone() {
        return contactsPhone;
    }

    /**
     * 设置联系电话
     *
     * @param contactsPhone 联系电话
     */
    public void setContactsPhone(String contactsPhone) {
        this.contactsPhone = contactsPhone;
    }

    /**
     * 获取发布时间
     *
     * @return publishTime - 发布时间
     */
    public Date getPublishTime() {
        return publishTime;
    }

    /**
     * 设置发布时间
     *
     * @param publishTime 发布时间
     */
    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    /**
     * 获取状态
     *
     * @return status - 状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态
     * @param status 状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
    
   

    /**
     * 获取有效天数
     *
     * @return effectDays - 有效天数
     */
    public String getEffectDays() {
      
      return  DateUtil.dateDiff( DateUtil.getTime(),DateUtil.dateToString(this.effecEndTime, DateStyle.YYYY_MM_DD_HH_MM_SS));
        
    }

    public String getPicUrls() {
        return picUrls;
    }

    public void setPicUrls(String picUrls) {
        this.picUrls = picUrls;
    }

    public Integer getTakingStatus() {
        return takingStatus;
    }

    public void setTakingStatus(Integer takingStatus) {
        this.takingStatus = takingStatus;
    }

    public Integer getFinishStatus() {
        return finishStatus;
    }

    public void setFinishStatus(Integer finishStatus) {
        this.finishStatus = finishStatus;
    }
    
    

}