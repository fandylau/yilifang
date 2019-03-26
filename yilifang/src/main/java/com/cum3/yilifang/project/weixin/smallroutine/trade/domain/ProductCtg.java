package com.cum3.yilifang.project.weixin.smallroutine.trade.domain;

import javax.persistence.Id;
import javax.persistence.Table;

import com.cum3.yilifang.framework.common.annotations.ComCode;
import com.cum3.yilifang.framework.common.annotations.Pictures;
import com.cum3.yilifang.framework.common.annotations.TreeId;
import com.cum3.yilifang.framework.common.annotations.TreeParentId;
import com.cum3.yilifang.framework.common.annotations.TreeSort;
import com.cum3.yilifang.framework.common.annotations.TreeText;
import com.cum3.yilifang.framework.common.genid.UUIdGenId;
import com.cum3.yilifang.framework.web.domain.TreeEntity;

import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

@Table(name = "yilifang_comunity_productctg")
@NameStyle(Style.normal)
public class ProductCtg extends TreeEntity<ProductCtg>{
    /**
     * 描述
     */
    private static final long serialVersionUID = 7183295903757773598L;

    /**
     * recNo
     */
    @Id
    @KeySql(genId = UUIdGenId.class)
    @TreeId
    private String recNo;

    /**
     * comCode
     */
    @ComCode
    private String comCode;

    /**
     * parentId
     */
    @TreeParentId
    private String parentId;

    /**
     * 分类名称
     */
    @TreeText
    private String name;

    /**
     * 分类图片
     */
    @Pictures
    private String picUrls;


    /**
     * 排序
     */
    @TreeSort
    private Integer seq;


    public String getRecNo() {
        return recNo;
    }


    public void setRecNo(String recNo) {
        this.recNo = recNo;
    }


    public String getComCode() {
        return comCode;
    }


    public void setComCode(String comCode) {
        this.comCode = comCode;
    }


    public String getParentId() {
        return parentId;
    }


    public void setParentId(String parentId) {
        this.parentId = parentId;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getPicUrls() {
        return picUrls;
    }


    public void setPicUrls(String picUrls) {
        this.picUrls = picUrls;
    }


    public Integer getSeq() {
        return seq;
    }


    public void setSeq(Integer seq) {
        this.seq = seq;
    }
}