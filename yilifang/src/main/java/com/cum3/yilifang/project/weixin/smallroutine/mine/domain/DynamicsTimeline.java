package com.cum3.yilifang.project.weixin.smallroutine.mine.domain;

import java.util.Date;
import javax.persistence.*;

import com.cum3.yilifang.framework.common.annotations.ComCode;
import com.cum3.yilifang.framework.common.annotations.CreateTime;
import com.cum3.yilifang.framework.common.genid.UUIdGenId;
import com.cum3.yilifang.framework.web.domain.BaseEntity;

import tk.mybatis.mapper.annotation.KeySql;

@Table(name = "ylf_mine_daynamics_timeline")
public class DynamicsTimeline  extends BaseEntity<DynamicsTimeline> {
    /**
     * 描述
     */
    private static final long serialVersionUID = 7590445244425437783L;

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
     * 用户id
     */
    private String userId;

    /**
     * 朋友（关注的人）Id
     */
    private String atnUerId;

    /**
     * 是否是自己的
     */
    private String isOwn;

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
     * 获取朋友（关注的人）Id
     *
     * @return atnUerId - 朋友（关注的人）Id
     */
    public String getAtnUerId() {
        return atnUerId;
    }

    /**
     * 设置朋友（关注的人）Id
     *
     * @param atnUerId 朋友（关注的人）Id
     */
    public void setAtnUerId(String atnUerId) {
        this.atnUerId = atnUerId;
    }

    /**
     * 获取是否是自己的
     *
     * @return isOwn - 是否是自己的
     */
    public String getIsOwn() {
        return isOwn;
    }

    /**
     * 设置是否是自己的
     *
     * @param isOwn 是否是自己的
     */
    public void setIsOwn(String isOwn) {
        this.isOwn = isOwn;
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
}