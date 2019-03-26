package com.cum3.yilifang.project.weixin.smallroutine.content.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import com.cum3.yilifang.framework.common.annotations.ComCode;
import com.cum3.yilifang.framework.common.annotations.CreateTime;
import com.cum3.yilifang.framework.common.annotations.Pictures;
import com.cum3.yilifang.framework.common.annotations.UpdateTime;
import com.cum3.yilifang.framework.common.annotations.Videos;
import com.cum3.yilifang.framework.common.genid.UUIdGenId;
import com.cum3.yilifang.framework.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

@Table(name = "ylf_community_renting")
@NameStyle(Style.normal)
public class Renting extends BaseEntity<Renting> {
    /**
     * 描述
     */
    private static final long serialVersionUID = 2674770768617835149L;

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
     * 标题
     */
    private String title;

    /**
     * 地址
     */
    private String address;

    /**
     * 图片url
     */
    @Pictures
    private String picUrls;

    /**
     * 视频Url
     */
    @Videos
    private String videoUrls;

    /**
     * 发布人头像
     */
    private String portrait;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * openId
     */
    private String openId;

    /**
     * 点击数量
     */
    private Integer hitNum=0;

    /**
     * 发布用户名
     */
    private String userName;

    /**
     * 小区名称
     */
    private String villageName;

    /**
     * 门牌号
     */
    private String doorNo;

    /**
     * 面积
     */
    private BigDecimal acreage;

    /**
     * 朝向
     */
    private String position;

    /**
     * 总楼层
     */
    private Integer floors;

    /**
     * 所在楼层
     */
    private Integer inFloor;

    /**
     * 几室
     */
    private Integer roomQuntity;

    /**
     * 几厅
     */
    private Integer hallQuntity;

    /**
     * 几卫
     */
    private Integer washRoomQuntity;

    /**
     * 车位0 无 1 有
     */
    private String havePaking;
    /**
     * 是否有电梯
     */
    private String haveElevator;

    /**
     * 装修类型
     */
    private String decorateType;

    /**
     * 房屋配置
     */
    private String houseHaves;

    /**
     * 租金方式
     */
    private String rentType;
    /**
     * 出租方式
     */
    private String  rentStyle;

    /**
     * 租金
     */
    private Long rentMoney;

    /**
     * 联系人
     */
    private String contacts;

    /**
     * 电话
     */
    private String contactsPhone;
    
    private Integer status = 1;

    /**
     * 发布时间
     */
    @CreateTime
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date publishTime;
    /**
     * 更新时间
     */
    @UpdateTime
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 内容
     */
    private String content;

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
     * 获取标题
     *
     * @return title - 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取地址
     *
     * @return address - 地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置地址
     *
     * @param address 地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取图片url
     *
     * @return picUrls - 图片url
     */
    public String getPicUrls() {
        return picUrls;
    }

    /**
     * 设置图片url
     *
     * @param picUrls 图片url
     */
    public void setPicUrls(String picUrls) {
        this.picUrls = picUrls;
    }

    /**
     * 获取视频Url
     *
     * @return videoUrls - 视频Url
     */
    public String getVideoUrls() {
        return videoUrls;
    }

    /**
     * 设置视频Url
     *
     * @param videoUrls 视频Url
     */
    public void setVideoUrls(String videoUrls) {
        this.videoUrls = videoUrls;
    }

    /**
     * 获取发布人头像
     *
     * @return protrait - 发布人头像
     */
    public String getPortrait() {
        return portrait;
    }

    /**
     * 设置发布人头像
     *
     * @param protrait 发布人头像
     */
    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    /**
     * 获取经度
     *
     * @return longitude - 经度
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * 设置经度
     *
     * @param longitude 经度
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * 获取纬度
     *
     * @return latitude - 纬度
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * 设置纬度
     *
     * @param latitude 纬度
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
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
     * 获取点击数量
     *
     * @return hitNum - 点击数量
     */
    public Integer getHitNum() {
        return hitNum;
    }

    /**
     * 设置点击数量
     *
     * @param hitNum 点击数量
     */
    public void setHitNum(Integer hitNum) {
        this.hitNum = hitNum;
    }

    /**
     * 获取发布用户名
     *
     * @return userName - 发布用户名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置发布用户名
     *
     * @param userName 发布用户名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取小区名称
     *
     * @return villageName - 小区名称
     */
    public String getVillageName() {
        return villageName;
    }

    /**
     * 设置小区名称
     *
     * @param villageName 小区名称
     */
    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    /**
     * 获取门牌号
     *
     * @return doorNo - 门牌号
     */
    public String getDoorNo() {
        return doorNo;
    }

    /**
     * 设置门牌号
     *
     * @param doorNo 门牌号
     */
    public void setDoorNo(String doorNo) {
        this.doorNo = doorNo;
    }

    /**
     * 获取面积
     *
     * @return acreage - 面积
     */
    public BigDecimal getAcreage() {
        return acreage;
    }

    /**
     * 设置面积
     *
     * @param acreage 面积
     */
    public void setAcreage(BigDecimal acreage) {
        this.acreage = acreage;
    }

    /**
     * 获取朝向
     *
     * @return position - 朝向
     */
    public String getPosition() {
        return position;
    }

    /**
     * 设置朝向
     *
     * @param position 朝向
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * 获取总楼层
     *
     * @return floors - 总楼层
     */
    public Integer getFloors() {
        return floors;
    }

    /**
     * 设置总楼层
     *
     * @param floors 总楼层
     */
    public void setFloors(Integer floors) {
        this.floors = floors;
    }

    /**
     * 获取所在楼层
     *
     * @return inFloor - 所在楼层
     */
    public Integer getInFloor() {
        return inFloor;
    }

    /**
     * 设置所在楼层
     *
     * @param inFloor 所在楼层
     */
    public void setInFloor(Integer inFloor) {
        this.inFloor = inFloor;
    }

    /**
     * 获取几室
     *
     * @return roomQuntity - 几室
     */
    public Integer getRoomQuntity() {
        return roomQuntity;
    }

    /**
     * 设置几室
     *
     * @param roomQuntity 几室
     */
    public void setRoomQuntity(Integer roomQuntity) {
        this.roomQuntity = roomQuntity;
    }

    /**
     * 获取几厅
     *
     * @return hallQuntity - 几厅
     */
    public Integer getHallQuntity() {
        return hallQuntity;
    }

    /**
     * 设置几厅
     *
     * @param hallQuntity 几厅
     */
    public void setHallQuntity(Integer hallQuntity) {
        this.hallQuntity = hallQuntity;
    }

    /**
     * 获取几卫
     *
     * @return washRoomQuntity - 几卫
     */
    public Integer getWashRoomQuntity() {
        return washRoomQuntity;
    }

    /**
     * 设置几卫
     *
     * @param washRoomQuntity 几卫
     */
    public void setWashRoomQuntity(Integer washRoomQuntity) {
        this.washRoomQuntity = washRoomQuntity;
    }

    /**
     * 获取车位0 无 1 有
     *
     * @return havePaking - 车位0 无 1 有
     */
    public String getHavePaking() {
        return havePaking;
    }

    /**
     * 设置车位0 无 1 有
     *
     * @param havePaking 车位0 无 1 有
     */
    public void setHavePaking(String havePaking) {
        this.havePaking = havePaking;
    }

    /**
     * 获取装修类型
     *
     * @return decorateType - 装修类型
     */
    public String getDecorateType() {
        return decorateType;
    }

    /**
     * 设置装修类型
     *
     * @param decorateType 装修类型
     */
    public void setDecorateType(String decorateType) {
        this.decorateType = decorateType;
    }

    /**
     * 获取房屋配置
     *
     * @return houseHaves - 房屋配置
     */
    public String getHouseHaves() {
        return houseHaves;
    }

    /**
     * 设置房屋配置
     *
     * @param houseHaves 房屋配置
     */
    public void setHouseHaves(String houseHaves) {
        this.houseHaves = houseHaves;
    }

    /**
     * 获取租金方式
     *
     * @return rentType - 租金方式
     */
    public String getRentType() {
        return rentType;
    }

    /**
     * 设置租金方式
     *
     * @param rentType 租金方式
     */
    public void setRentType(String rentType) {
        this.rentType = rentType;
    }

    /**
     * 获取租金
     *
     * @return rentMoney - 租金
     */
    public Long getRentMoney() {
        return rentMoney;
    }

    /**
     * 设置租金
     *
     * @param rentMoney 租金
     */
    public void setRentMoney(Long rentMoney) {
        this.rentMoney = rentMoney;
    }

    /**
     * 获取联系人
     *
     * @return contacts - 联系人
     */
    public String getContacts() {
        return contacts;
    }

    /**
     * 设置联系人
     *
     * @param contacts 联系人
     */
    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    /**
     * 获取电话
     *
     * @return contactsPhone - 电话
     */
    public String getContactsPhone() {
        return contactsPhone;
    }

    /**
     * 设置电话
     *
     * @param contactsPhone 电话
     */
    public void setContactsPhone(String contactsPhone) {
        this.contactsPhone = contactsPhone;
    }

    /**
     * 获取发布时间
     *
     * @return publishTime - 发布时间
     */
    public Date getPublishTime() {
        return publishTime;
    }

    /**
     * 设置发布时间
     *
     * @param publishTime 发布时间
     */
    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    /**
     * 获取内容
     *
     * @return content - 内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置内容
     *
     * @param content 内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    public String getRentStyle() {
        return rentStyle;
    }

    public void setRentStyle(String rentStyle) {
        this.rentStyle = rentStyle;
    }

    public String getHaveElevator() {
        return haveElevator;
    }

    public void setHaveElevator(String haveElevator) {
        this.haveElevator = haveElevator;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}