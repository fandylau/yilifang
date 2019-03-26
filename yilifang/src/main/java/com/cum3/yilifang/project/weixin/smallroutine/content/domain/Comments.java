package com.cum3.yilifang.project.weixin.smallroutine.content.domain;

import java.util.Date;
import javax.persistence.*;

import com.cum3.yilifang.framework.common.annotations.ComCode;
import com.cum3.yilifang.framework.common.annotations.CreateTime;
import com.cum3.yilifang.framework.common.genid.UUIdGenId;
import com.cum3.yilifang.framework.web.domain.BaseEntity;

import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

@Table(name = "ylf_content_comments")
@NameStyle(Style.normal)
public class Comments extends BaseEntity<Comments>{
    /**
     * 描述
     */
    private static final long serialVersionUID = 86574138947181469L;

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
     * 评论类型1 动态Id 2新闻 3热点 4...
     */
    private String type;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 头像
     */
    private String portrait;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 评论用户id
     */
    private String openId;

    /**
     * 业务id 
     */
    private String bizId;
    /**
     * 点赞数量
     */
    private Integer likeNum = 0;
    /**
     * 回复数量
     */
    private Integer replyNum = 0;
    /**
     * 当前用户是否点过赞了
     */
    @Transient
    private boolean hasPrise;

    public Integer getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public boolean isHasPrise() {
        return hasPrise;
    }

    public void setHasPrise(boolean hasPrise) {
        this.hasPrise = hasPrise;
    }

    /**
     * 评论内容
     */
    private String content;

    
    /**
     * 创建时间
     */
    @CreateTime
    private Date createtime;

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

   
    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    /**
     * 获取评论内容
     *
     * @return content - 评论内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置评论内容
     *
     * @param content 评论内容
     */
    public void setContent(String content) {
        this.content = content;
    }
    /**
     * 获取创建时间
     *
     * @return createtime - 创建时间
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * 设置创建时间
     *
     * @param createtime 创建时间
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(Integer replyNum) {
        this.replyNum = replyNum;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }
    
    
}