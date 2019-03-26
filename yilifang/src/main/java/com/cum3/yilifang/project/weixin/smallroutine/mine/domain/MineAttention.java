package com.cum3.yilifang.project.weixin.smallroutine.mine.domain;

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

@Table(name = "ylf_mine_attention")
@NameStyle(Style.normal)
public class MineAttention  extends BaseEntity<MineAttention>{
    /**
     * 描述
     */
    private static final long serialVersionUID = 7302097303003158583L;

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
     * 关注用户id
     */
    private String atnUserId;

    /**
     * 关注时间
     */
    @CreateTime
    private Date atnTime;

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
     * 获取关注用户id
     *
     * @return atnUserId - 关注用户id
     */
    public String getAtnUserId() {
        return atnUserId;
    }

    /**
     * 设置关注用户id
     *
     * @param atnUserId 关注用户id
     */
    public void setAtnUserId(String atnUserId) {
        this.atnUserId = atnUserId;
    }

    /**
     * 获取关注时间
     *
     * @return atnTime - 关注时间
     */
    public Date getAtnTime() {
        return atnTime;
    }

    /**
     * 设置关注时间
     *
     * @param atnTime 关注时间
     */
    public void setAtnTime(Date atnTime) {
        this.atnTime = atnTime;
    }
}