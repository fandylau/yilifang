package com.cum3.yilifang.project.weixin.smallroutine.content.domain;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import com.cum3.yilifang.framework.common.annotations.ComCode;
import com.cum3.yilifang.framework.common.annotations.CreateTime;
import com.cum3.yilifang.framework.common.genid.UUIdGenId;
import com.cum3.yilifang.framework.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

@Table(name = "ylf_content_notice")
@NameStyle(Style.normal)
public class Notice extends BaseEntity<Notice> {
    /**
     * 描述
     */
    private static final long serialVersionUID = 6615572325739669493L;

    /**
     * 主键
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
     * 内容
     */
    private String content;

    /**
     * 图片链接地址
     */
    private String picUrls;
    /**
     * 音频地址
     */
    private String audioUrls;
    /**
     * 公告附件地址
     */
    private String attUrls;

    /**
     * 类型1 通知 2公告
     */
    private String type;
    /**
     * 点击数
     */
    private Integer hitNum = 0;
    
    

    /**
     * createTime
     */
    @CreateTime
    @JsonFormat(pattern = "YYYY-MM-dd", timezone = "GMT+8")
    private Date createTime;
    
    

    /**
     * createBy
     */
    private String createBy;

    /**
     * 获取主键
     *
     * @return recNo - 主键
     */
    public String getRecNo() {
        return recNo;
    }

    /**
     * 设置主键
     *
     * @param recNo
     *            主键
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
     * @param comCode
     *            comCode
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
     * @param title
     *            标题
     */
    public void setTitle(String title) {
        this.title = title;
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
     * @param content
     *            内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取图片链接地址
     *
     * @return picUrls - 图片链接地址
     */
    public String getPicUrls() {
        return picUrls;
    }

    /**
     * 设置图片链接地址
     *
     * @param picUrls
     *            图片链接地址
     */
    public void setPicUrls(String picUrls) {
        this.picUrls = picUrls;
    }

    /**
     * 获取类型1 通知 2公告
     *
     * @return type - 类型1 通知 2公告
     */
    public String getType() {
        return type;
    }

    /**
     * 设置类型1 通知 2公告
     *
     * @param type
     *            类型1 通知 2公告
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取createTime
     *
     * @return createTime - createTime
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置createTime
     *
     * @param createTime
     *            createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取createBy
     *
     * @return createBy - createBy
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * 设置createBy
     *
     * @param createBy
     *            createBy
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Integer getHitNum() {
        return hitNum;
    }

    public void setHitNum(Integer hitNum) {
        this.hitNum = hitNum;
    }

    public String getAudioUrls() {
        return audioUrls;
    }

    public void setAudioUrls(String audioUrls) {
        this.audioUrls = audioUrls;
    }

    public String getAttUrls() {
        return attUrls;
    }

    public void setAttUrls(String attUrls) {
        this.attUrls = attUrls;
    }
    
}