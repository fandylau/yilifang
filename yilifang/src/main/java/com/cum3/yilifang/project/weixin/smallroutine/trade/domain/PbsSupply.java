package com.cum3.yilifang.project.weixin.smallroutine.trade.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cum3.yilifang.framework.common.annotations.ComCode;
import com.cum3.yilifang.framework.common.annotations.CreateTime;
import com.cum3.yilifang.framework.common.annotations.Pictures;
import com.cum3.yilifang.framework.common.annotations.UpdateTime;
import com.cum3.yilifang.framework.common.annotations.Videos;
import com.cum3.yilifang.framework.common.genid.UUIdGenId;
import com.cum3.yilifang.framework.common.utils.date.DateStyle;
import com.cum3.yilifang.framework.common.utils.date.DateUtil;
import com.cum3.yilifang.framework.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

@Table(name = "yilifang_comunity_pbssupply")
@NameStyle(Style.normal)
public class PbsSupply  extends BaseEntity<PbsSupply>{
    /**
     * 描述
     */
    private static final long serialVersionUID = -2677330462947021532L;

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
     * 数量
     */
    private String title;

    /**
     * 商品编码
     */
    private String productSn;

    /**
     * 商品id
     */
    private String productId;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品分类
     */
    private String ctgId;
    /**
     * 分类名称
     */
    private String ctgName;

    /**
     * 商品图片
     */
    @Pictures
    private String pictureUrls;

    /**
     * 商品视频
     */
    @Videos
    private String videoUrls;

    /**
     * 商品原价
     */
    private BigDecimal originPrice;

    /**
     * 商品现价
     */
    private BigDecimal price;

    /**
     * 商品单位
     */
    private String unit;
    
    /**
     * 数量
     */
    private BigDecimal amount;
    /**
     * 商品描述
     */
    private String description;
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
     * openId
     */
    private String openId;

    /**
     * 头像
     */
    private String protrait;

    /**
     * 有效时间--结束
     */
    private Date effecEndTime;
    /**
     * 交易方式
     */
    private String shippingType;
    /**
     * 有效天数
     */
    @Transient
    private String effectDays;

    /**
     * 0已过期 1未过期
     */
    private Integer status;

    /**
     * 创建人
     */
    private String userName;
    /**
     * 联系人
     */
    private String contacts;
    /**
     * 联系方式
     */
    private String contactsPhone;
    
    /**
     * 接单状态  0 未接单  1 已接单
     */
    private Integer takingStatus;
    
    /**
     * 完成状态 0 未完成   1 已完成
     */
    private Integer finishStatus;

    /**
     * 发布时间
     */
    @CreateTime
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date publishTime;

    /**
     * 创建时间
     */
    @CreateTime
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 更新时间
     */
    @UpdateTime
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 商品详情
     */
    private String detail;

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
     * 获取商品编码
     *
     * @return productSn - 商品编码
     */
    public String getProductSn() {
        return productSn;
    }

    /**
     * 设置商品编码
     *
     * @param productSn 商品编码
     */
    public void setProductSn(String productSn) {
        this.productSn = productSn;
    }

    /**
     * 获取商品id
     *
     * @return productId - 商品id
     */
    public String getProductId() {
        return productId;
    }

    /**
     * 设置商品id
     *
     * @param productId 商品id
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
     * 获取商品名称
     *
     * @return name - 商品名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置商品名称
     *
     * @param name 商品名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取商品分类
     *
     * @return ctgId - 商品分类
     */
    public String getCtgId() {
        return ctgId;
    }

    /**
     * 设置商品分类
     *
     * @param ctgId 商品分类
     */
    public void setCtgId(String ctgId) {
        this.ctgId = ctgId;
    }

    /**
     * 获取商品图片
     *
     * @return pictureUrls - 商品图片
     */
    public String getPictureUrls() {
        return pictureUrls;
    }

    /**
     * 设置商品图片
     *
     * @param pictureUrls 商品图片
     */
    public void setPictureUrls(String pictureUrls) {
        this.pictureUrls = pictureUrls;
    }

    /**
     * 获取商品视频
     *
     * @return videoUrls - 商品视频
     */
    public String getVideoUrls() {
        return videoUrls;
    }

    /**
     * 设置商品视频
     *
     * @param videoUrls 商品视频
     */
    public void setVideoUrls(String videoUrls) {
        this.videoUrls = videoUrls;
    }

    /**
     * 获取商品原价
     *
     * @return originPrice - 商品原价
     */
    public BigDecimal getOriginPrice() {
        return originPrice;
    }

    /**
     * 设置商品原价
     *
     * @param originPrice 商品原价
     */
    public void setOriginPrice(BigDecimal originPrice) {
        this.originPrice = originPrice;
    }

    /**
     * 获取商品现价
     *
     * @return price - 商品现价
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置商品现价
     *
     * @param price 商品现价
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取商品单位
     *
     * @return unit - 商品单位
     */
    public String getUnit() {
        return unit;
    }

    /**
     * 设置商品单位
     *
     * @param unit 商品单位
     */
    public void setUnit(String unit) {
        this.unit = unit;
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
     * 获取有效时间--结束
     *
     * @return effecEndTime - 有效时间--结束
     */
    public Date getEffecEndTime() {
        return effecEndTime;
    }

    /**
     * 设置有效时间--结束
     *
     * @param effecEndTime 有效时间--结束
     */
    public void setEffecEndTime(Date effecEndTime) {
        this.effecEndTime = effecEndTime;
    }

    /**
     * 获取有效天数
     *
     * @return effectDays - 有效天数
     */
    public String getEffectDays() {
      
      return  DateUtil.dateDiff( DateUtil.getTime(),DateUtil.dateToString(this.effecEndTime, DateStyle.YYYY_MM_DD_HH_MM_SS));
        
    }

 
    /**
     * 0过期 1有效
     *
     * @return status - 0下架 1上架
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置0下架 1上架
     *
     * @param status 0下架 1上架
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取创建人
     *
     * @return userName - 创建人
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置创建人
     *
     * @param userName 创建人
     */
    public void setUserName(String userName) {
        this.userName = userName;
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
     * 获取商品详情
     *
     * @return detail - 商品详情
     */
    public String getDetail() {
        return detail;
    }

    /**
     * 设置商品详情
     *
     * @param detail 商品详情
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getShippingType() {
        return shippingType;
    }

    public void setShippingType(String shippingType) {
        this.shippingType = shippingType;
    }

    public void setEffectDays(String effectDays) {
        this.effectDays = effectDays;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCtgName() {
        return ctgName;
    }

    public void setCtgName(String ctgName) {
        this.ctgName = ctgName;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getContactsPhone() {
        return contactsPhone;
    }

    public void setContactsPhone(String contactsPhone) {
        this.contactsPhone = contactsPhone;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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