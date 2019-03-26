package com.cum3.yilifang.project.weixin.smallroutine.content.domain;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import com.cum3.yilifang.framework.common.annotations.ComCode;
import com.cum3.yilifang.framework.common.annotations.CreateTime;
import com.cum3.yilifang.framework.common.annotations.Pictures;
import com.cum3.yilifang.framework.common.annotations.UpdateTime;
import com.cum3.yilifang.framework.common.annotations.Voices;
import com.cum3.yilifang.framework.common.genid.UUIdGenId;
import com.cum3.yilifang.framework.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

@Table(name = "ylf_community_staff")
@NameStyle(Style.normal)
public class Staff  extends BaseEntity<Staff>{
    /**
     * 描述
     */
    private static final long serialVersionUID = -2978898371220612927L;

    /**
     * 主键
     */
    @Id
    @KeySql(genId = UUIdGenId.class)
    private String recNo;

    /**
     * comcode
     */
    @ComCode
    private String comCode;

    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户身份证
     */
    private String idCardno;

    /**
     * 姓名
     */
    private String staffName;
    /**
     * 性别
     */
    private String sex;
    /**
     * 职务
     */
    private String staffPost;

    /**
     * 电话
     */
    private String staffPhoneNo;

    /**
     * 头像
     */
    @Pictures
    private String portrait;

    /**
     * 党员介绍
     */
    private String intro;
    /**
     * 语音文件地址
     */
    @Voices
    private String audioUrls;

    /**
     * 排序
     */
    private Integer seq;

    /**
     * 创建时间
     */
    @CreateTime
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 修改时间
     */
    @UpdateTime
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 获取主键
     *
     * @return recNo - 主键
     */
    public String getRecNo() {
        return recNo;
    }

    /**
     * 设置主键
     *
     * @param recNo 主键
     */
    public void setRecNo(String recNo) {
        this.recNo = recNo;
    }

    /**
     * 获取comcode
     *
     * @return comCode - comcode
     */
    public String getComCode() {
        return comCode;
    }

    /**
     * 设置comcode
     *
     * @param comCode comcode
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
     * 获取姓名
     *
     * @return staffName - 姓名
     */
    public String getStaffName() {
        return staffName;
    }

    /**
     * 设置姓名
     *
     * @param staffName 姓名
     */
    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    /**
     * 获取职务
     *
     * @return staffPost - 职务
     */
    public String getStaffPost() {
        return staffPost;
    }

    /**
     * 设置职务
     *
     * @param staffPost 职务
     */
    public void setStaffPost(String staffPost) {
        this.staffPost = staffPost;
    }

    /**
     * 获取电话
     *
     * @return staffPhoneNo - 电话
     */
    public String getStaffPhoneNo() {
        return staffPhoneNo;
    }

    /**
     * 设置电话
     *
     * @param staffPhoneNo 电话
     */
    public void setStaffPhoneNo(String staffPhoneNo) {
        this.staffPhoneNo = staffPhoneNo;
    }

    /**
     * 获取头像
     *
     * @return portrait - 头像
     */
    public String getPortrait() {
        return portrait;
    }

    /**
     * 设置头像
     *
     * @param portrait 头像
     */
    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    /**
     * 获取党员介绍
     *
     * @return intro - 党员介绍
     */
    public String getIntro() {
        return intro;
    }

    /**
     * 设置党员介绍
     *
     * @param intro 党员介绍
     */
    public void setIntro(String intro) {
        this.intro = intro;
    }

    /**
     * 获取排序
     *
     * @return seq - 排序
     */
    public Integer getSeq() {
        return seq;
    }

    /**
     * 设置排序
     *
     * @param seq 排序
     */
    public void setSeq(Integer seq) {
        this.seq = seq;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAudioUrls() {
        return audioUrls;
    }

    public void setAudioUrls(String audioUrls) {
        this.audioUrls = audioUrls;
    }

    public String getIdCardno() {
        return idCardno;
    }

    public void setIdCardno(String idCardno) {
        this.idCardno = idCardno;
    }
    
}