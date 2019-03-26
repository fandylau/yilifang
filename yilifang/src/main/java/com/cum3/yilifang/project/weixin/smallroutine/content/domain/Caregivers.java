package com.cum3.yilifang.project.weixin.smallroutine.content.domain;

import java.util.Date;
import javax.persistence.*;

import com.cum3.yilifang.framework.common.annotations.ComCode;
import com.cum3.yilifang.framework.common.annotations.CreateTime;
import com.cum3.yilifang.framework.common.annotations.DelFlag;
import com.cum3.yilifang.framework.common.annotations.Pictures;
import com.cum3.yilifang.framework.common.annotations.UpdateTime;
import com.cum3.yilifang.framework.common.genid.UUIdGenId;
import com.cum3.yilifang.framework.web.domain.BaseEntity;

import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

@Table(name = "yilifang_comunity_caregivers")
@NameStyle(Style.normal)
public class Caregivers extends BaseEntity<Caregivers> {
    /**
     * 描述
     */
    private static final long serialVersionUID = 4399147065263111024L;

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
     * openId
     */
    private String openId;
    /**
     * 类型
     */
    private String type;

    /**
     * 姓名
     */
    private String name;
    /**
     * 性别
     */
    private String sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 身份证号码
     */
    private String idCardno;
    /**
     * 电话号码
     */
    private String phoneNo;

    /**
     * 民族
     */
    private String nation;

    /**
     * 籍贯
     */
    private String careNative;

    /**
     * 学历
     */
    private String education;

    /**
     * 照片
     */
    @Pictures
    private String photoUrl;

    /**
     * 工作经验
     */
    private String workExperience;

    /**
     * 特长
     */
    private String speciality;

    /**
     * 现住址
     */
    private String address;

    /**
     * 自我评价
     */
    private String seftEval;

    /**
     * 客户评价
     */
    private String customerEval;
    /**
     * 审核人
     */
    private String checktor;
    /**
     * 审核意见
     */
    private String checkOpnions;
    /**
     * 审核时间
     */
    @UpdateTime
    private Date checkTime;

    /**
     * 创建时间
     */
    @CreateTime
    private Date createTime;
    /**
     * 审核状态 0 待审核  1
     */
    private Integer status;
    
    @DelFlag
    private Integer delFlag;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 创建人
     */
    private String createBy;

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
     * 获取姓名
     *
     * @return name - 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置姓名
     *
     * @param name 姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取年龄
     *
     * @return age - 年龄
     */
    public Integer getAge() {
        return age;
    }

    /**
     * 设置年龄
     *
     * @param age 年龄
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * 获取身份证号码
     *
     * @return idCardno - 身份证号码
     */
    public String getIdCardno() {
        return idCardno;
    }

    /**
     * 设置身份证号码
     *
     * @param idCardno 身份证号码
     */
    public void setIdCardno(String idCardno) {
        this.idCardno = idCardno;
    }

    /**
     * 获取民族
     *
     * @return nation - 民族
     */
    public String getNation() {
        return nation;
    }

    /**
     * 设置民族
     *
     * @param nation 民族
     */
    public void setNation(String nation) {
        this.nation = nation;
    }

    

    public String getCareNative() {
        return careNative;
    }

    public void setCareNative(String careNative) {
        this.careNative = careNative;
    }

    /**
     * 获取学历
     *
     * @return education - 学历
     */
    public String getEducation() {
        return education;
    }

    /**
     * 设置学历
     *
     * @param education 学历
     */
    public void setEducation(String education) {
        this.education = education;
    }

    /**
     * 获取照片
     *
     * @return photoUrl - 照片
     */
    public String getPhotoUrl() {
        return photoUrl;
    }

    /**
     * 设置照片
     *
     * @param photoUrl 照片
     */
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    /**
     * 获取工作经验
     *
     * @return workExperience - 工作经验
     */
    public String getWorkExperience() {
        return workExperience;
    }

    /**
     * 设置工作经验
     *
     * @param workExperience 工作经验
     */
    public void setWorkExperience(String workExperience) {
        this.workExperience = workExperience;
    }

    /**
     * 获取特长
     *
     * @return speciality - 特长
     */
    public String getSpeciality() {
        return speciality;
    }

    /**
     * 设置特长
     *
     * @param speciality 特长
     */
    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    /**
     * 获取现住址
     *
     * @return address - 现住址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置现住址
     *
     * @param address 现住址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取自我评价
     *
     * @return seftEval - 自我评价
     */
    public String getSeftEval() {
        return seftEval;
    }

    /**
     * 设置自我评价
     *
     * @param seftEval 自我评价
     */
    public void setSeftEval(String seftEval) {
        this.seftEval = seftEval;
    }

    /**
     * 获取客户评价
     *
     * @return customerEval - 客户评价
     */
    public String getCustomerEval() {
        return customerEval;
    }

    /**
     * 设置客户评价
     *
     * @param customerEval 客户评价
     */
    public void setCustomerEval(String customerEval) {
        this.customerEval = customerEval;
    }

    /**
     * 获取创建时间
     *
     * @return createTime - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取修改时间
     *
     * @return updateTime - 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置修改时间
     *
     * @param updateTime 修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取创建人
     *
     * @return createBy - 创建人
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * 设置创建人
     *
     * @param createBy 创建人
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getChecktor() {
        return checktor;
    }

    public void setChecktor(String checktor) {
        this.checktor = checktor;
    }

    public String getCheckOpnions() {
        return checkOpnions;
    }

    public void setCheckOpnions(String checkOpnions) {
        this.checkOpnions = checkOpnions;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
    
}