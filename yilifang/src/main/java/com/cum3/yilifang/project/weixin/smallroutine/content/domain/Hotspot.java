package com.cum3.yilifang.project.weixin.smallroutine.content.domain;

import java.util.Date;
import javax.persistence.*;

import com.cum3.yilifang.framework.common.annotations.ComCode;
import com.cum3.yilifang.framework.common.annotations.CreateTime;
import com.cum3.yilifang.framework.common.annotations.Pictures;
import com.cum3.yilifang.framework.common.annotations.UpdateTime;
import com.cum3.yilifang.framework.common.genid.UUIdGenId;
import com.cum3.yilifang.framework.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

@Table(name = "ylf_content_hotspot")
@NameStyle(Style.normal)
public class Hotspot  extends BaseEntity<Hotspot>{
    /**
     * 描述
     */
    private static final long serialVersionUID = 997119118622341320L;

    /**
     * recNO
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
     * 图片地址
     */
    @Pictures
    private String picUrls;

    /**
     * 浏览数量
     */
    private Integer hitNum = 0;

    /**
     * 点赞数量
     */
    private Integer likeNum = 0;

    /**
     * 回复数量
     */
    private Integer replyNum = 0;
    /**
     * 是否点过赞了
     */
    @Transient 
    private boolean hasPraise = false;
    /**
     * 作者
     */
    private String author;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    @CreateTime
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    
    @UpdateTime
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
    
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 内容
     */
    private String content;

   

    public String getRecNo() {
        return recNo;
    }

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
     * 获取图片地址
     *
     * @return picUrls - 图片地址
     */
    public String getPicUrls() {
        return picUrls;
    }

    /**
     * 设置图片地址
     *
     * @param picUrls 图片地址
     */
    public void setPicUrls(String picUrls) {
        this.picUrls = picUrls;
    }

    /**
     * 获取浏览数量
     *
     * @return hitNum - 浏览数量
     */
    public Integer getHitNum() {
        return hitNum;
    }

    /**
     * 设置浏览数量
     *
     * @param hitNum 浏览数量
     */
    public void setHitNum(Integer hitNum) {
        this.hitNum = hitNum;
    }

    /**
     * 获取点赞数量
     *
     * @return likeNum - 点赞数量
     */
    public Integer getLikeNum() {
        return likeNum;
    }

    /**
     * 设置点赞数量
     *
     * @param likeNum 点赞数量
     */
    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    /**
     * 获取回复数量
     *
     * @return replyNum - 回复数量
     */
    public Integer getReplyNum() {
        return replyNum;
    }

    /**
     * 设置回复数量
     *
     * @param replyNum 回复数量
     */
    public void setReplyNum(Integer replyNum) {
        this.replyNum = replyNum;
    }

    /**
     * 获取创建人
     *
     * @return createBy - 创建人
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * 设置创建人
     *
     * @param createBy 创建人
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
     * 获取内容
     *
     * @return content - 内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置内容
     *
     * @param content 内容
     */
    public void setContent(String content) {
        this.content = content;
    }
    
    public boolean isHasPraise() {
        return hasPraise;
    }

    public void setHasPraise(boolean hasPraise) {
        this.hasPraise = hasPraise;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    
}