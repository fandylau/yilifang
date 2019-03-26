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

@Table(name = "ylf_mine_favourate")
@NameStyle(Style.normal)
public class Favourate extends BaseEntity<Favourate>{
    /**
     * 描述
     */
    private static final long serialVersionUID = -8147659966241653547L;

    @Id
    @KeySql(genId = UUIdGenId.class)
    private String recNo;

    /**
     * comCode
     */
    @ComCode
    private String comCode;

    /**
     * 收藏类别（动态或文章）
     */
    private String fvtType;

    /**
     * 文章id或动态id
     */
    private String fvtId;
    /**
     * 用户Id
     */
    private String openId;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    /**
     * 收藏时间
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
     * 获取收藏类别（动态或文章）
     *
     * @return fvtType - 收藏类别（动态或文章）
     */
    public String getFvtType() {
        return fvtType;
    }

    /**
     * 设置收藏类别（动态或文章）
     *
     * @param fvtType 收藏类别（动态或文章）
     */
    public void setFvtType(String fvtType) {
        this.fvtType = fvtType;
    }

    /**
     * 获取文章id或动态id
     *
     * @return fvtId - 文章id或动态id
     */
    public String getFvtId() {
        return fvtId;
    }

    /**
     * 设置文章id或动态id
     *
     * @param fvtId 文章id或动态id
     */
    public void setFvtId(String fvtId) {
        this.fvtId = fvtId;
    }

    /**
     * 获取收藏时间
     *
     * @return createtime - 收藏时间
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * 设置收藏时间
     *
     * @param createtime 收藏时间
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}