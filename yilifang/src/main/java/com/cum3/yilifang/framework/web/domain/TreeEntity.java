package com.cum3.yilifang.framework.web.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.common.collect.Lists;

import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

/**
 * 树基类 所有树的表格必须继承该类
 * 
 * @author Fandy Lau
 */
@NameStyle(Style.normal)
public class TreeEntity<T> extends BaseEntity<T> {
    /**
     * 描述
     */
    private static final long serialVersionUID = 6972436715035641092L;
    @Column
    private String isn;//内码
    @Column
    private Integer rank; //层级
    @Column
    private String isleaf; //是否是叶子节点
    @Column
    private String isdir;   //是否是目录
    @Transient // 不是数据库字段
    @JsonInclude(Include.NON_EMPTY) //不为空的时候才序列号json
    private List<T> children = Lists.newArrayList();//孩子节点列表

    public String getIsn() {
        return isn;
    }

    public void setIsn(String isn) {
        this.isn = isn;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getIsleaf() {
        return isleaf;
    }

    public void setIsleaf(String isleaf) {
        this.isleaf = isleaf;
    }

    public String getIsdir() {
        return isdir;
    }

    public void setIsdir(String isdir) {
        this.isdir = isdir;
    }

    public List<T> getChildren() {
        return children;
    }

    public void setChildren(List<T> children) {
        this.children = children;
    }

}
