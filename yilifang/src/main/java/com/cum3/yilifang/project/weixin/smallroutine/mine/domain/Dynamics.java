package com.cum3.yilifang.project.weixin.smallroutine.mine.domain;

import java.util.Date;
import javax.persistence.*;

import com.cum3.yilifang.framework.common.annotations.ComCode;
import com.cum3.yilifang.framework.common.annotations.CreateTime;
import com.cum3.yilifang.framework.common.genid.UUIdGenId;
import com.cum3.yilifang.framework.web.domain.BaseEntity;

import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

@Table(name = "ylf_mine_dynamics")
@NameStyle(Style.normal)
public class Dynamics extends BaseEntity<Dynamics>{
    /**
     * 描述
     */
    private static final long serialVersionUID = 5076232555035841362L;
    @Id
    @KeySql(genId = UUIdGenId.class)
    private String recNo;
    /**
     * comCode
     */
    @ComCode
    private String comCode;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 标题
     */
    private String title;

    /**
     * 标题图片
     */
    private String titlePics;

    /**
     * 图片或视频id
     */
    private String picOrVidieo;

    /**
     * 位置
     */
    private String locaction;

    /**
     * 发布时间
     */
    @CreateTime
    private Date createtime;

    /**
     * 内容
     */
    private String content;

    /**
     * @return recNo
     */
    public String getRecNo() {
        return recNo;
    }

    /**
     * @param recNo
     */
    public void setRecNo(String recNo) {
        this.recNo = recNo;
    }

    /**
     * @return comCode
     */
    public String getComCode() {
        return comCode;
    }

    /**
     * @param comCode
     */
    public void setComCode(String comCode) {
        this.comCode = comCode;
    }

    /**
     * 获取用户id
     *
     * @return userId - 用户id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(String userId) {
        this.userId = userId;
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
     * 获取标题图片
     *
     * @return titlePics - 标题图片
     */
    public String getTitlePics() {
        return titlePics;
    }

    /**
     * 设置标题图片
     *
     * @param titlePics 标题图片
     */
    public void setTitlePics(String titlePics) {
        this.titlePics = titlePics;
    }

    /**
     * 获取图片或视频id
     *
     * @return picOrVidieo - 图片或视频id
     */
    public String getPicOrVidieo() {
        return picOrVidieo;
    }

    /**
     * 设置图片或视频id
     *
     * @param picOrVidieo 图片或视频id
     */
    public void setPicOrVidieo(String picOrVidieo) {
        this.picOrVidieo = picOrVidieo;
    }

    /**
     * 获取位置
     *
     * @return locaction - 位置
     */
    public String getLocaction() {
        return locaction;
    }

    /**
     * 设置位置
     *
     * @param locaction 位置
     */
    public void setLocaction(String locaction) {
        this.locaction = locaction;
    }

    /**
     * 获取发布时间
     *
     * @return createtime - 发布时间
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * 设置发布时间
     *
     * @param createtime 发布时间
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
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
}