package com.cum3.yilifang.project.weixin.smallroutine.content.domain;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import com.cum3.yilifang.framework.common.annotations.ComCode;
import com.cum3.yilifang.framework.common.annotations.CreateTime;
import com.cum3.yilifang.framework.common.annotations.TreeId;
import com.cum3.yilifang.framework.common.annotations.TreeParentId;
import com.cum3.yilifang.framework.common.annotations.TreeSort;
import com.cum3.yilifang.framework.common.annotations.TreeText;
import com.cum3.yilifang.framework.common.annotations.UpdateTime;
import com.cum3.yilifang.framework.common.genid.UUIdGenId;
import com.cum3.yilifang.framework.web.domain.TreeEntity;

import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

@Table(name = "ylf_content_floder")
@NameStyle(Style.normal)
public class Floder extends TreeEntity<Floder>{
    /**
     * 描述
     */
    private static final long serialVersionUID = 4875481691656878505L;

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
     * 栏目名称
     */
    @TreeText
    private String floderName;

    /**
     * 栏目KEY
     */
    private String floderKey;

    /**
     * 栏目类型 1文章 2轮播图 3公告
     */
    private String type;

    /**
     * parentId
     */
    @TreeParentId
    private String parentId;

    /**
     * 描述
     */
    private String description;

    /**
     * 状态 1 有效 0废弃
     */
    private Boolean status;

    /**
     * 创建时间
     */
    @CreateTime
    private Date createtime;

    /**
     * 修改时间
     */
    @UpdateTime
    private Date updatetime;
    
    @TreeSort
    private Integer seq;

    /**
     * 内码 祖先的id加自身的id 用.相连
     */
    private String isn;

    /**
     * 层级
     */
    private Integer rank;

    /**
     * 是否是目录
     */
    private String isdir;

    /**
     * 是否是叶子节点0 不是 1是
     */
    private String isleaf;

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
     * 获取栏目名称
     *
     * @return floderName - 栏目名称
     */
    public String getFloderName() {
        return floderName;
    }

    /**
     * 设置栏目名称
     *
     * @param floderName 栏目名称
     */
    public void setFloderName(String floderName) {
        this.floderName = floderName;
    }

    /**
     * 获取栏目KEY
     *
     * @return floderKey - 栏目KEY
     */
    public String getFloderKey() {
        return floderKey;
    }

    /**
     * 设置栏目KEY
     *
     * @param floderKey 栏目KEY
     */
    public void setFloderKey(String floderKey) {
        this.floderKey = floderKey;
    }

    /**
     * 获取栏目类型 1文章 2轮播图 3公告
     *
     * @return type - 栏目类型 1文章 2轮播图 3公告
     */
    public String getType() {
        return type;
    }

    /**
     * 设置栏目类型 1文章 2轮播图 3公告
     *
     * @param type 栏目类型 1文章 2轮播图 3公告
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取parentId
     *
     * @return parentId - parentId
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * 设置parentId
     *
     * @param parentId parentId
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取描述
     *
     * @return description - 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置描述
     *
     * @param description 描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取状态 1 有效 0废弃
     *
     * @return status - 状态 1 有效 0废弃
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * 设置状态 1 有效 0废弃
     *
     * @param status 状态 1 有效 0废弃
     */
    public void setStatus(Boolean status) {
        this.status = status;
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

    /**
     * 获取修改时间
     *
     * @return updatetime - 修改时间
     */
    public Date getUpdatetime() {
        return updatetime;
    }

    /**
     * 设置修改时间
     *
     * @param updatetime 修改时间
     */
    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    /**
     * @return seq
     */
    public Integer getSeq() {
        return seq;
    }

    /**
     * @param seq
     */
    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    /**
     * 获取内码 祖先的id加自身的id 用.相连
     *
     * @return isn - 内码 祖先的id加自身的id 用.相连
     */
    public String getIsn() {
        return isn;
    }

    /**
     * 设置内码 祖先的id加自身的id 用.相连
     *
     * @param isn 内码 祖先的id加自身的id 用.相连
     */
    public void setIsn(String isn) {
        this.isn = isn;
    }

    /**
     * 获取层级
     *
     * @return rank - 层级
     */
    public Integer getRank() {
        return rank;
    }

    /**
     * 设置层级
     *
     * @param rank 层级
     */
    public void setRank(Integer rank) {
        this.rank = rank;
    }

    /**
     * 获取是否是目录
     *
     * @return isdir - 是否是目录
     */
    public String getIsdir() {
        return isdir;
    }

    /**
     * 设置是否是目录
     *
     * @param isdir 是否是目录
     */
    public void setIsdir(String isdir) {
        this.isdir = isdir;
    }

    /**
     * 获取是否是叶子节点0 不是 1是
     *
     * @return isleaf - 是否是叶子节点0 不是 1是
     */
    public String getIsleaf() {
        return isleaf;
    }

    /**
     * 设置是否是叶子节点0 不是 1是
     *
     * @param isleaf 是否是叶子节点0 不是 1是
     */
    public void setIsleaf(String isleaf) {
        this.isleaf = isleaf;
    }
}