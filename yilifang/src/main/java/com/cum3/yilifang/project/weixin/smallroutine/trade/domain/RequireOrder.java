package com.cum3.yilifang.project.weixin.smallroutine.trade.domain;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import com.cum3.yilifang.framework.common.annotations.ComCode;
import com.cum3.yilifang.framework.common.annotations.CreateTime;
import com.cum3.yilifang.framework.common.genid.UUIdGenId;
import com.cum3.yilifang.framework.web.domain.BaseEntity;

import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

@Table(name = "yilifang_require_order")
@NameStyle(Style.normal)
public class RequireOrder extends BaseEntity<RequireOrder>{
    /**
     * 描述
     */
    private static final long serialVersionUID = 3823471586713359620L;

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
     * 需求id
     */
    private String reqId;

    /**
     * 接单人openId
     */
    private String receiverOpenId;

    /**
     * 接单人姓名
     */
    private String receiverName;

    /**
     * 接单人头像
     */
    private String receiverProtrait;

    /**
     * 接单人电话
     */
    private String receiverPhone;

    /**
     * 订单号
     */
    private String orderSn;

    /**
     * 订单金额
     */
    private Long orderPrice;

    /**
     * 订单状态 0 待确认 1 进行中 2已完成 3取消
     */
    private Integer orderStatus;

    /**
     * 订单创建时间
     */
    @CreateTime
    private Date createTime;
    /**
     * 订单确认时间
     */
    private Date confirmTime;

    /**
     * 订单完成时间
     */
    private Date finishedTime;

    /**
     * 订单取消时间
     */
    private Date cancelTime;
    
    /**
     * 订单类型 1 供货订单 2需求订单
     */
    private Integer orderType;

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
     * 获取需求id
     *
     * @return reqId - 需求id
     */
    public String getReqId() {
        return reqId;
    }

    /**
     * 设置需求id
     *
     * @param reqId 需求id
     */
    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    /**
     * 获取接单人openId
     *
     * @return receiverOpenId - 接单人openId
     */
    public String getReceiverOpenId() {
        return receiverOpenId;
    }

    /**
     * 设置接单人openId
     *
     * @param receiverOpenId 接单人openId
     */
    public void setReceiverOpenId(String receiverOpenId) {
        this.receiverOpenId = receiverOpenId;
    }

    /**
     * 获取接单人姓名
     *
     * @return receiverName - 接单人姓名
     */
    public String getReceiverName() {
        return receiverName;
    }

    /**
     * 设置接单人姓名
     *
     * @param receiverName 接单人姓名
     */
    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    /**
     * 获取接单人头像
     *
     * @return receiverProtrait - 接单人头像
     */
    public String getReceiverProtrait() {
        return receiverProtrait;
    }

    /**
     * 设置接单人头像
     *
     * @param receiverProtrait 接单人头像
     */
    public void setReceiverProtrait(String receiverProtrait) {
        this.receiverProtrait = receiverProtrait;
    }

    /**
     * 获取接单人电话
     *
     * @return receiverPhone - 接单人电话
     */
    public String getReceiverPhone() {
        return receiverPhone;
    }

    /**
     * 设置接单人电话
     *
     * @param receiverPhone 接单人电话
     */
    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    /**
     * 获取订单号
     *
     * @return orderSn - 订单号
     */
    public String getOrderSn() {
        return orderSn;
    }

    /**
     * 设置订单号
     *
     * @param orderSn 订单号
     */
    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    /**
     * 获取订单金额
     *
     * @return orderPrice - 订单金额
     */
    public Long getOrderPrice() {
        return orderPrice;
    }

    /**
     * 设置订单金额
     *
     * @param orderPrice 订单金额
     */
    public void setOrderPrice(Long orderPrice) {
        this.orderPrice = orderPrice;
    }

    /**
     * 获取订单状态 0 待确认 1 进行中 2已完成 3取消
     *
     * @return orderStatus - 订单状态 0 待确认 1 进行中 2已完成 3取消
     */
    public Integer getOrderStatus() {
        return orderStatus;
    }

    /**
     * 设置订单状态 0 待确认 1 进行中 2已完成 3取消
     *
     * @param orderStatus 订单状态 0 待确认 1 进行中 2已完成 3取消
     */
    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * 获取订单创建时间
     *
     * @return createTime - 订单创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置订单创建时间
     *
     * @param createTime 订单创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取订单完成时间
     *
     * @return finishedTime - 订单完成时间
     */
    public Date getFinishedTime() {
        return finishedTime;
    }

    /**
     * 设置订单完成时间
     *
     * @param finishedTime 订单完成时间
     */
    public void setFinishedTime(Date finishedTime) {
        this.finishedTime = finishedTime;
    }

    /**
     * 获取订单取消时间
     *
     * @return cacelTime - 订单取消时间
     */
    public Date getCancelTime() {
        return cancelTime;
    }

    /**
     * 设置订单取消时间
     *
     * @param cacelTime 订单取消时间
     */
    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }
    
    
}