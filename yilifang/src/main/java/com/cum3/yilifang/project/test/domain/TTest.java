package com.cum3.yilifang.project.test.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cum3.yilifang.framework.common.annotations.ComCode;
import com.cum3.yilifang.framework.common.annotations.CreateTime;
import com.cum3.yilifang.framework.common.annotations.Pictures;
import com.cum3.yilifang.framework.common.annotations.UpdateTime;
import com.cum3.yilifang.framework.common.genid.UUIdGenId;
import com.cum3.yilifang.framework.web.domain.BaseEntity;

import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

@Table(name = "t_test")
@NameStyle(Style.normal)
public class TTest extends BaseEntity<TTest> {
    /**
     * 描述
     */
    private static final long serialVersionUID = -6262257863385699334L;

    @Id
    @KeySql(genId = UUIdGenId.class)
    private String id;
    
    @ComCode
    private String comCode;

    @Column
    private String filed1;
    @Column
    @Pictures
    private String filed2;
    @Column
    @CreateTime
    private Date createTime;
    @Column
    @UpdateTime
    private Date updateTime;

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return filed1
     */
    public String getFiled1() {
        return filed1;
    }

    /**
     * @param filed1
     */
    public void setFiled1(String filed1) {
        this.filed1 = filed1;
    }

    /**
     * @return filed2
     */
    public String getFiled2() {
        return filed2;
    }

    /**
     * @param filed2
     */
    public void setFiled2(String filed2) {
        this.filed2 = filed2;
    }

    public String getComCode() {
        return comCode;
    }

    public void setComCode(String comCode) {
        this.comCode = comCode;
    }
    
    
}