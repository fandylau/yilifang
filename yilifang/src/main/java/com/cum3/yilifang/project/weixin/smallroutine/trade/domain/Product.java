package com.cum3.yilifang.project.weixin.smallroutine.trade.domain;

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

import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

@Table(name = "yilifang_comunity_product")
@NameStyle(Style.normal)
public class Product extends BaseEntity<Product>{
    /**
     * 描述
     */
    private static final long serialVersionUID = 4761817311813150398L;

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
     * 商品编码
     */
    private String productSn;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品分类
     */
    private String ctgId;

    /**
     * 商品图片
     */
    @Pictures
    private String pictureUrls;

    /**
     * 商品视频
     */
    @Videos
    private String videoUrls;

    /**
     * 商品原价
     */
    private BigDecimal originPrice;

    /**
     * 商品现价
     */
    private BigDecimal price;

    /**
     * 商品单位
     */
    private String unit;

    /**
     * 排序 越小越靠前
     */
    private Integer seq;

    /**
     * 0启用 1禁用
     */
    private Boolean status;

    /**
     * 创建人
     */
    private String userName;

    /**
     * 创建时间
     */
    @CreateTime
    private Date createTime;

    /**
     * 更新时间
     */
    @UpdateTime
    private Date updateTime;

    /**
     * 商品详情
     */
    private String detail;

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
     * 获取商品编码
     *
     * @return productSn - 商品编码
     */
    public String getProductSn() {
        return productSn;
    }

    /**
     * 设置商品编码
     *
     * @param productSn 商品编码
     */
    public void setProductSn(String productSn) {
        this.productSn = productSn;
    }

    /**
     * 获取商品名称
     *
     * @return name - 商品名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置商品名称
     *
     * @param name 商品名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取商品分类
     *
     * @return ctgId - 商品分类
     */
    public String getCtgId() {
        return ctgId;
    }

    /**
     * 设置商品分类
     *
     * @param ctgId 商品分类
     */
    public void setCtgId(String ctgId) {
        this.ctgId = ctgId;
    }

    /**
     * 获取商品图片
     *
     * @return pictureUrls - 商品图片
     */
    public String getPictureUrls() {
        return pictureUrls;
    }

    /**
     * 设置商品图片
     *
     * @param pictureUrls 商品图片
     */
    public void setPictureUrls(String pictureUrls) {
        this.pictureUrls = pictureUrls;
    }

    /**
     * 获取商品视频
     *
     * @return videoUrls - 商品视频
     */
    public String getVideoUrls() {
        return videoUrls;
    }

    /**
     * 设置商品视频
     *
     * @param videoUrls 商品视频
     */
    public void setVideoUrls(String videoUrls) {
        this.videoUrls = videoUrls;
    }

    /**
     * 获取商品原价
     *
     * @return originPrice - 商品原价
     */
    public BigDecimal getOriginPrice() {
        return originPrice;
    }

    /**
     * 设置商品原价
     *
     * @param originPrice 商品原价
     */
    public void setOriginPrice(BigDecimal originPrice) {
        this.originPrice = originPrice;
    }

    /**
     * 获取商品现价
     *
     * @return price - 商品现价
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置商品现价
     *
     * @param price 商品现价
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取商品单位
     *
     * @return unit - 商品单位
     */
    public String getUnit() {
        return unit;
    }

    /**
     * 设置商品单位
     *
     * @param unit 商品单位
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * 获取越小越靠前
     *
     * @return seq - 越小越靠前
     */
    public Integer getSeq() {
        return seq;
    }

    /**
     * 设置越小越靠前
     *
     * @param seq 越小越靠前
     */
    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    /**
     * 获取0启用 1禁用
     *
     * @return status - 0启用 1禁用
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * 设置0启用 1禁用
     *
     * @param status 0启用 1禁用
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     * 获取创建人
     *
     * @return userName - 创建人
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置创建人
     *
     * @param userName 创建人
     */
    public void setUserName(String userName) {
        this.userName = userName;
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
     * 获取更新时间
     *
     * @return updateTime - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取商品详情
     *
     * @return detail - 商品详情
     */
    public String getDetail() {
        return detail;
    }

    /**
     * 设置商品详情
     *
     * @param detail 商品详情
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }
}