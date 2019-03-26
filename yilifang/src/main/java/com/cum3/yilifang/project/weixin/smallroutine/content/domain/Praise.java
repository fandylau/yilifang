package com.cum3.yilifang.project.weixin.smallroutine.content.domain;

import javax.persistence.*;

import com.cum3.yilifang.framework.common.annotations.ComCode;
import com.cum3.yilifang.framework.common.genid.UUIdGenId;
import com.cum3.yilifang.framework.web.domain.BaseEntity;

import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;
/**
 * 
 * 点赞
 * @author Fandy Liu
 * @created 2018年9月23日 下午9:58:56
 */
@Table(name = "ylf_content_praise")
@NameStyle(Style.normal)
public class Praise  extends BaseEntity<Praise>{
    /**
     * 描述
     */
    private static final long serialVersionUID = -4034681944518541684L;

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
     * 点赞类型  1评论点赞  2 文章点赞  3....
     */
    private String type;

    /**
     * 对应的作品或评论的id
     */
    private String bizId;

    /**
     * openId
     */
    private String openId;

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
     * 获取点赞类型  1评论点赞  2 文章点赞  3....
     *
     * @return type - 点赞类型  1评论点赞  2 文章点赞  3....
     */
    public String getType() {
        return type;
    }

    /**
     * 设置点赞类型  1评论点赞  2 文章点赞  3....
     *
     * @param type 点赞类型  1评论点赞  2 文章点赞  3....
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取对应的作品或评论的id
     *
     * @return bizId - 对应的作品或评论的id
     */
    public String getBizId() {
        return bizId;
    }

    /**
     * 设置对应的作品或评论的id
     *
     * @param bizId 对应的作品或评论的id
     */
    public void setBizId(String bizId) {
        this.bizId = bizId;
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

  
}