package com.cum3.yilifang.project.weixin.smallroutine.mine.domain;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cum3.yilifang.framework.common.annotations.ComCode;
import com.cum3.yilifang.framework.common.annotations.CreateTime;
import com.cum3.yilifang.framework.common.annotations.UpdateTime;
import com.cum3.yilifang.framework.web.domain.BaseEntity;

import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

@Table(name = "ylf_wechat_authentication")
@NameStyle(Style.normal)
public class IdCardAuthentication extends BaseEntity<IdCardAuthentication>{
    /**
     * 描述
     */
    private static final long serialVersionUID = -7287728428265895598L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String openId;

    private String memberName;

    private String idCard;
    
    private String nation;

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    private String sex;

    private Date birthday;

    private String address;

    private String idCardFrontPict;

    private String idCardSidePict;

    private Integer status;

    private String remark;

    private Integer delFlag;
    @ComCode
    private String comCode;

    private String createUid;
    @CreateTime
    private Date createDate;

    private String modifyUid;
    @UpdateTime
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
     * @return memberName
     */
    public String getMemberName() {
        return memberName;
    }

    /**
     * @param memberName
     */
    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    /**
     * @return idCard
     */
    public String getIdCard() {
        return idCard;
    }

    /**
     * @param idCard
     */
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    /**
     * @return sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * @param sex
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * @return birthday
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * @param birthday
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return idCardFrontPict
     */
    public String getIdCardFrontPict() {
        return idCardFrontPict;
    }

    /**
     * @param idCardFrontPict
     */
    public void setIdCardFrontPict(String idCardFrontPict) {
        this.idCardFrontPict = idCardFrontPict;
    }

    /**
     * @return idCardSidePict
     */
    public String getIdCardSidePict() {
        return idCardSidePict;
    }

    /**
     * @param idCardSidePict
     */
    public void setIdCardSidePict(String idCardSidePict) {
        this.idCardSidePict = idCardSidePict;
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