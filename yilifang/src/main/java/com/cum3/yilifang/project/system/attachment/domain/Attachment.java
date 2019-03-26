package com.cum3.yilifang.project.system.attachment.domain;

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

@Table(name = "sys_attachment")
@NameStyle(Style.normal)
public class Attachment extends BaseEntity<Attachment>{
    /**
     * 描述
     */
    private static final long serialVersionUID = 5095577502964036929L;

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
     * 文件原名
     */
    private String fileOriginalName;

    /**
     * 文件名 （uuid生成的文件名）
     */
    private String fileName;

    /**
     * 文件存储路径
     */
    private String filePath;

    /**
     * 文件类型
     */
    private String fileType;
    /**
     * 文件大小
     */
    private Long fileSize;
    
    /**
     * 文件状态 1 有效 0 无效
     */
    private Integer status;

    /**
     * 创建时间
     */
    @CreateTime
    private Date createTime;
    
    public Attachment(){
        
    }
    
    public Attachment(String fileOriginalName,String fileName,String fileType,String filePath,Integer status){
        this.fileOriginalName = fileOriginalName;
        this.fileName = fileName;
        this.fileType = fileType;
        this.filePath = filePath;
        this.status = status;
    }

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
     * 获取文件原名
     *
     * @return fileOriginalName - 文件原名
     */
    public String getFileOriginalName() {
        return fileOriginalName;
    }

    /**
     * 设置文件原名
     *
     * @param fileOriginalName 文件原名
     */
    public void setFileOriginalName(String fileOriginalName) {
        this.fileOriginalName = fileOriginalName;
    }

    /**
     * 获取文件名 （uuid生成的文件名）
     *
     * @return fileName - 文件名 （uuid生成的文件名）
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 设置文件名 （uuid生成的文件名）
     *
     * @param fileName 文件名 （uuid生成的文件名）
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * 获取文件存储路径
     *
     * @return filePath - 文件存储路径
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * 设置文件存储路径
     *
     * @param filePath 文件存储路径
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * 获取文件类型
     *
     * @return fileType - 文件类型
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * 设置文件类型
     *
     * @param fileType 文件类型
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
    
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
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
}