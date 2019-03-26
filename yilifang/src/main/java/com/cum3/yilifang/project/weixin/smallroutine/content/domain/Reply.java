package com.cum3.yilifang.project.weixin.smallroutine.content.domain;

import java.util.Date;
import javax.persistence.*;

import com.cum3.yilifang.framework.common.annotations.ComCode;
import com.cum3.yilifang.framework.common.annotations.CreateTime;
import com.cum3.yilifang.framework.common.genid.UUIdGenId;
import com.cum3.yilifang.framework.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

@Table(name = "ylf_content_reply")
@NameStyle(Style.normal)
public class Reply  extends BaseEntity<Reply>{
    /**
     * 描述
     */
    private static final long serialVersionUID = 4558593486451103312L;

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
     * 业务id
     */
    private String bizId;

    /**
     * 回复类型1.针对评论   2.针对回复
     */
    private String replyType;

    /**
     * 回复内容
     */
    private String content;

    /**
     * 回复用户id
     */
    private String fromOpenId;

    /**
     * 目标用户id
     */
    private String toOpenId;
    /**
     * 
     */
    private String fromUserName;
    /**
     * 
     */
    private String toUserName;
    /**
     * 点赞数量
     */
    private Integer likeNum = 0;
    /**
     * 头像
     */
    private String   portrait;
    
    /**
     * 是否点过赞了
     */
    @Transient 
    private boolean hasPraise = false;

    /**
     * 回复时间
     */
    @CreateTime
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

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
    

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    /**
     * 获取回复类型1.针对评论 2.针对回复
     *
     * @return replyType - 回复类型1.针对评论 2.针对回复
     */
    public String getReplyType() {
        return replyType;
    }

    /**
     * 设置回复类型1.针对评论 2.针对回复
     *
     * @param replyType 回复类型1.针对评论 2.针对回复
     */
    public void setReplyType(String replyType) {
        this.replyType = replyType;
    }

    /**
     * 获取回复内容
     *
     * @return content - 回复内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置回复内容
     *
     * @param content 回复内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取回复用户id
     *
     * @return fromOpenId - 回复用户id
     */
    public String getFromOpenId() {
        return fromOpenId;
    }

    /**
     * 设置回复用户id
     *
     * @param fromOpenId 回复用户id
     */
    public void setFromOpenId(String fromOpenId) {
        this.fromOpenId = fromOpenId;
    }

    /**
     * 获取目标用户id
     *
     * @return toOpenId - 目标用户id
     */
    public String getToOpenId() {
        return toOpenId;
    }

    /**
     * 设置目标用户id
     *
     * @param toOpenId 目标用户id
     */
    public void setToOpenId(String toOpenId) {
        this.toOpenId = toOpenId;
    }

    /**
     * 获取回复时间
     *
     * @return createTime - 回复时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置回复时间
     *
     * @param createTime 回复时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public Integer getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public boolean isHasPraise() {
        return hasPraise;
    }

    public void setHasPraise(boolean hasPraise) {
        this.hasPraise = hasPraise;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }
    
    
    
}