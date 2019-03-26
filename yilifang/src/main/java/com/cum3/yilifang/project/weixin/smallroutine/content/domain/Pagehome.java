package com.cum3.yilifang.project.weixin.smallroutine.content.domain;

import java.util.Date;
import javax.persistence.*;

import com.cum3.yilifang.framework.common.annotations.ComCode;
import com.cum3.yilifang.framework.common.annotations.CreateTime;
import com.cum3.yilifang.framework.common.annotations.UpdateTime;
import com.cum3.yilifang.framework.common.genid.UUIdGenId;
import com.cum3.yilifang.framework.web.domain.BaseEntity;

import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

@Table(name = "ylf_medical_pagehome")
@NameStyle(Style.normal)
public class Pagehome extends BaseEntity<Pagehome> {
    /**
     * 描述
     */
    private static final long serialVersionUID = -5190594131308917308L;

    /**
     * recNo
     */
    @Id
    @KeySql(genId = UUIdGenId.class)
    private String recNo;
    @ComCode
    private String comCode;
    @CreateTime
    private Date createDate;

    private String createUid;
    @UpdateTime
    private Date modifyDate;

    private String modifyUid;

    private Integer delFlag;

    /**
     * 音频文件地址
     */
    private String audioUrls;

    /**
     * 展示标题
     */
    private String displayTitle;

    /**
     * 简介
     */
    private String synopsis;

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
     * @return createDate
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return createUid
     */
    public String getCreateUid() {
        return createUid;
    }

    /**
     * @param createUid
     */
    public void setCreateUid(String createUid) {
        this.createUid = createUid;
    }

    /**
     * @return modifyDate
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * @param modifyDate
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * @return modifyUid
     */
    public String getModifyUid() {
        return modifyUid;
    }

    /**
     * @param modifyUid
     */
    public void setModifyUid(String modifyUid) {
        this.modifyUid = modifyUid;
    }

    /**
     * @return delFlag
     */
    public Integer getDelFlag() {
        return delFlag;
    }

    /**
     * @param delFlag
     */
    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    /**
     * 获取音频文件地址
     *
     * @return audioUrls - 音频文件地址
     */
    public String getAudioUrls() {
        return audioUrls;
    }

    /**
     * 设置音频文件地址
     *
     * @param audioUrls
     *            音频文件地址
     */
    public void setAudioUrls(String audioUrls) {
        this.audioUrls = audioUrls;
    }

    /**
     * 获取展示标题
     *
     * @return displayTitle - 展示标题
     */
    public String getDisplayTitle() {
        return displayTitle;
    }

    /**
     * 设置展示标题
     *
     * @param displayTitle
     *            展示标题
     */
    public void setDisplayTitle(String displayTitle) {
        this.displayTitle = displayTitle;
    }

    /**
     * 获取简介
     *
     * @return synopsis - 简介
     */
    public String getSynopsis() {
        return synopsis;
    }

    /**
     * 设置简介
     *
     * @param synopsis
     *            简介
     */
    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }
}