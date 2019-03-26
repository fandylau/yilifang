package com.cum3.yilifang.project.weixin.smallroutine.content.domain;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import com.cum3.yilifang.framework.common.annotations.ComCode;
import com.cum3.yilifang.framework.common.annotations.CreateTime;
import com.cum3.yilifang.framework.common.annotations.UpdateTime;
import com.cum3.yilifang.framework.common.annotations.Voices;
import com.cum3.yilifang.framework.common.genid.UUIdGenId;
import com.cum3.yilifang.framework.web.domain.BaseEntity;

import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;
/**
 * 社区广播
 * @author Fandy Liu
 * @created 2018年10月2日 下午9:47:32
 */
@Table(name = "ylf_community_broadcast")
@NameStyle(Style.normal)
public class Broadcast  extends BaseEntity<Broadcast>{

    /**
     * 描述
     */
    private static final long serialVersionUID = 7201098638823431258L;

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
     * openId
     */
    private String openId;

    /**
     * userName
     */
    private String userName;

    /**
     * createBy
     */
    private String createBy;

    /**
     * createTime
     */
    @CreateTime
    private Date createTime;

    /**
     * updateTime
     */
    @UpdateTime
    private Date updateTime;

    /**
     * voiceUrls
     */
    @Voices
    private String voiceUrls;
    /**
     * 语音时长
     */
    private Integer seconds;

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
     * 获取userName
     *
     * @return userName - userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置userName
     *
     * @param userName userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
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
     * @param createBy createBy
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
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
     * @param createTime createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取updateTime
     *
     * @return updateTime - updateTime
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置updateTime
     *
     * @param updateTime updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取voiceUrls
     *
     * @return voiceUrls - voiceUrls
     */
    public String getVoiceUrls() {
        return voiceUrls;
    }

    /**
     * 设置voiceUrls
     *
     * @param voiceUrls voiceUrls
     */
    public void setVoiceUrls(String voiceUrls) {
        this.voiceUrls = voiceUrls;
    }

    public Integer getSeconds() {
        return seconds;
    }

    public void setSeconds(Integer seconds) {
        this.seconds = seconds;
    }
    
    
}