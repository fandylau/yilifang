package com.cum3.yilifang.project.weixin.smallroutine.content.domain;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import com.cum3.yilifang.framework.common.annotations.ComCode;
import com.cum3.yilifang.framework.common.genid.UUIdGenId;
import com.cum3.yilifang.framework.web.domain.BaseEntity;

import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

@Table(name = "ylf_content_article")
@NameStyle(Style.normal)
public class Article extends BaseEntity<Article>{
    /**
     * 描述
     */
    private static final long serialVersionUID = -8395800083994870523L;

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
     * 所属栏目
     */
    private String topicId;

    /**
     * 标题
     */
    private String title;

    /**
     * 标题图片
     */
    private String titlePic;

    /**
     * 轮播图文件地址
     */
    private String rollPicUrl;

    /**
     * 是否设置轮播 1是 0否
     */
    private String isRollPic;

    /**
     * 轮播图视频地址
     */
    private String rollVideoUrl;

    /**
     * 是否转换音频1是 0否
     */
    private String isAudio;

    /**
     * 文章附件
     */
    private String attachment;

    /**
     * 音频文件地址
     */
    private String audioUrl;

    /**
     * 排序
     */
    private Integer seq;

    /**
     * 是否置顶
     */
    private String isTop;

    /**
     * 是否最新 1是 0不是
     */
    private String isNew;

    /**
     * 是否热门 1是 0不是
     */
    private String isHot;

    /**
     * 是否推荐 1是 0不是
     */
    private String isRec;

    /**
     * 点击次数
     */
    private Integer hitNum;

    /**
     * 发布时间
     */
    private Date publishTime;

    /**
     * 发布人
     */
    private String publishBy;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 更新时间
     */
    private Date updatetime;

    /**
     * 文章内容
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
     * 获取所属栏目
     *
     * @return topicId - 所属栏目
     */
    public String getTopicId() {
        return topicId;
    }

    /**
     * 设置所属栏目
     *
     * @param topicId 所属栏目
     */
    public void setTopicId(String topicId) {
        this.topicId = topicId;
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
     * 获取标题图片
     *
     * @return titlePic - 标题图片
     */
    public String getTitlePic() {
        return titlePic;
    }

    /**
     * 设置标题图片
     *
     * @param titlePic 标题图片
     */
    public void setTitlePic(String titlePic) {
        this.titlePic = titlePic;
    }

    /**
     * 获取轮播图文件地址
     *
     * @return rollPicUrl - 轮播图文件地址
     */
    public String getRollPicUrl() {
        return rollPicUrl;
    }

    /**
     * 设置轮播图文件地址
     *
     * @param rollPicUrl 轮播图文件地址
     */
    public void setRollPicUrl(String rollPicUrl) {
        this.rollPicUrl = rollPicUrl;
    }

    /**
     * 获取是否设置轮播 1是 0否
     *
     * @return isRollPic - 是否设置轮播 1是 0否
     */
    public String getIsRollPic() {
        return isRollPic;
    }

    /**
     * 设置是否设置轮播 1是 0否
     *
     * @param isRollPic 是否设置轮播 1是 0否
     */
    public void setIsRollPic(String isRollPic) {
        this.isRollPic = isRollPic;
    }

    /**
     * 获取轮播图视频地址
     *
     * @return rollVideoUrl - 轮播图视频地址
     */
    public String getRollVideoUrl() {
        return rollVideoUrl;
    }

    /**
     * 设置轮播图视频地址
     *
     * @param rollVideoUrl 轮播图视频地址
     */
    public void setRollVideoUrl(String rollVideoUrl) {
        this.rollVideoUrl = rollVideoUrl;
    }

    /**
     * 获取是否转换音频1是 0否
     *
     * @return isAudio - 是否转换音频1是 0否
     */
    public String getIsAudio() {
        return isAudio;
    }

    /**
     * 设置是否转换音频1是 0否
     *
     * @param isAudio 是否转换音频1是 0否
     */
    public void setIsAudio(String isAudio) {
        this.isAudio = isAudio;
    }

    /**
     * 获取文章附件
     *
     * @return attachment - 文章附件
     */
    public String getAttachment() {
        return attachment;
    }

    /**
     * 设置文章附件
     *
     * @param attachment 文章附件
     */
    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    /**
     * 获取音频文件地址
     *
     * @return audioUrl - 音频文件地址
     */
    public String getAudioUrl() {
        return audioUrl;
    }

    /**
     * 设置音频文件地址
     *
     * @param audioUrl 音频文件地址
     */
    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
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
     * 获取是否置顶
     *
     * @return isTop - 是否置顶
     */
    public String getIsTop() {
        return isTop;
    }

    /**
     * 设置是否置顶
     *
     * @param isTop 是否置顶
     */
    public void setIsTop(String isTop) {
        this.isTop = isTop;
    }

    /**
     * 获取是否最新 1是 0不是
     *
     * @return isNew - 是否最新 1是 0不是
     */
    public String getIsNew() {
        return isNew;
    }

    /**
     * 设置是否最新 1是 0不是
     *
     * @param isNew 是否最新 1是 0不是
     */
    public void setIsNew(String isNew) {
        this.isNew = isNew;
    }

    /**
     * 获取是否热门 1是 0不是
     *
     * @return isHot - 是否热门 1是 0不是
     */
    public String getIsHot() {
        return isHot;
    }

    /**
     * 设置是否热门 1是 0不是
     *
     * @param isHot 是否热门 1是 0不是
     */
    public void setIsHot(String isHot) {
        this.isHot = isHot;
    }

    /**
     * 获取是否推荐 1是 0不是
     *
     * @return isRec - 是否推荐 1是 0不是
     */
    public String getIsRec() {
        return isRec;
    }

    /**
     * 设置是否推荐 1是 0不是
     *
     * @param isRec 是否推荐 1是 0不是
     */
    public void setIsRec(String isRec) {
        this.isRec = isRec;
    }

    /**
     * 获取点击次数
     *
     * @return hitNum - 点击次数
     */
    public Integer getHitNum() {
        return hitNum;
    }

    /**
     * 设置点击次数
     *
     * @param hitNum 点击次数
     */
    public void setHitNum(Integer hitNum) {
        this.hitNum = hitNum;
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
     * 获取发布人
     *
     * @return publishBy - 发布人
     */
    public String getPublishBy() {
        return publishBy;
    }

    /**
     * 设置发布人
     *
     * @param publishBy 发布人
     */
    public void setPublishBy(String publishBy) {
        this.publishBy = publishBy;
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
     * 获取更新时间
     *
     * @return updatetime - 更新时间
     */
    public Date getUpdatetime() {
        return updatetime;
    }

    /**
     * 设置更新时间
     *
     * @param updatetime 更新时间
     */
    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    /**
     * 获取文章内容
     *
     * @return content - 文章内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置文章内容
     *
     * @param content 文章内容
     */
    public void setContent(String content) {
        this.content = content;
    }
}