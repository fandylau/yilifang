package com.cum3.yilifang.project.weixin.smallroutine.mine.domain;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import com.cum3.yilifang.framework.common.annotations.ComCode;
import com.cum3.yilifang.framework.common.annotations.CreateTime;
import com.cum3.yilifang.framework.common.genid.UUIdGenId;
import com.cum3.yilifang.framework.web.domain.BaseEntity;

import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

@Table(name = "ylf_mine_user")
@NameStyle(Style.normal)
public class MineUser  extends BaseEntity<MineUser>{
    /**
     * 描述
     */
    private static final long serialVersionUID = 2126897257170799012L;

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
     * 微信头像
     */
    private String wxPortrait;

    /**
     * 微信昵称
     */
    private String wxNickName;

    /**
     * 性别
     */
    private String sex;

    /**
     * 国家
     */
    private Integer country;

    /**
     * 省份
     */
    private Integer province;

    /**
     * 城市
     */
    private Integer city;

    /**
     * 注册时间
     */
    @CreateTime
    private Date registTime;

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

    /**
     * 获取微信头像
     *
     * @return wxPortrait - 微信头像
     */
    public String getWxPortrait() {
        return wxPortrait;
    }

    /**
     * 设置微信头像
     *
     * @param wxPortrait 微信头像
     */
    public void setWxPortrait(String wxPortrait) {
        this.wxPortrait = wxPortrait;
    }

    /**
     * 获取微信昵称
     *
     * @return wxNickName - 微信昵称
     */
    public String getWxNickName() {
        return wxNickName;
    }

    /**
     * 设置微信昵称
     *
     * @param wxNickName 微信昵称
     */
    public void setWxNickName(String wxNickName) {
        this.wxNickName = wxNickName;
    }

    /**
     * 获取性别
     *
     * @return sex - 性别
     */
    public String getSex() {
        return sex;
    }

    /**
     * 设置性别
     *
     * @param sex 性别
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * 获取国家
     *
     * @return country - 国家
     */
    public Integer getCountry() {
        return country;
    }

    /**
     * 设置国家
     *
     * @param country 国家
     */
    public void setCountry(Integer country) {
        this.country = country;
    }

    /**
     * 获取省份
     *
     * @return province - 省份
     */
    public Integer getProvince() {
        return province;
    }

    /**
     * 设置省份
     *
     * @param province 省份
     */
    public void setProvince(Integer province) {
        this.province = province;
    }

    /**
     * 获取城市
     *
     * @return city - 城市
     */
    public Integer getCity() {
        return city;
    }

    /**
     * 设置城市
     *
     * @param city 城市
     */
    public void setCity(Integer city) {
        this.city = city;
    }

    /**
     * 获取注册时间
     *
     * @return registTime - 注册时间
     */
    public Date getRegistTime() {
        return registTime;
    }

    /**
     * 设置注册时间
     *
     * @param registTime 注册时间
     */
    public void setRegistTime(Date registTime) {
        this.registTime = registTime;
    }
}