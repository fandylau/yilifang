package com.cum3.yilifang.project.weixin.smallroutine.mine.domain;

import java.util.Date;
import javax.persistence.*;

import com.cum3.yilifang.framework.common.annotations.ComCode;
import com.cum3.yilifang.framework.common.annotations.CreateTime;
import com.cum3.yilifang.framework.web.domain.BaseEntity;

import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

@Table(name = "ylf_wechat_authentication_face")
@NameStyle(Style.normal)
public class FaceAuthentication extends BaseEntity<FaceAuthentication> {
    /**
     * 描述
     */
    private static final long serialVersionUID = -8552426183454465461L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String openId;

    private String facePath;

    private Integer status;

    private String remark;

    private Integer delFlag;
    @ComCode
    private String comCode;

    private String createUid;
    @CreateTime
    private Date createDate;

    private String modifyUid;

    private Date modifyDate;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return openId
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * @param openId
     */
    public void setOpenId(String openId) {
        this.openId = openId;
    }

    /**
     * @return facePath
     */
    public String getFacePath() {
        return facePath;
    }

    /**
     * @param facePath
     */
    public void setFacePath(String facePath) {
        this.facePath = facePath;
    }

    /**
     * @return status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
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
}