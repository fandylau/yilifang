package com.cum3.yilifang.framework.api.oss;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.web.multipart.MultipartFile;

import com.cum3.yilifang.framework.common.utils.FileUtils;
import com.cum3.yilifang.framework.common.utils.SpringUtils;
import com.cum3.yilifang.framework.common.utils.date.DateStyle;
import com.cum3.yilifang.framework.common.utils.date.DateUtil;
import com.cum3.yilifang.project.system.attachment.domain.Attachment;
import com.cum3.yilifang.project.system.attachment.service.AttachmentService;
import com.google.common.base.Preconditions;
import com.qcloud.vod.VodApi;
import com.qcloud.vod.response.VodUploadCommitResponse;

import lombok.Data;

/**
 * 云存储(支持七牛、阿里云、腾讯云、ftp)
 * 
 * @author Fandy Liu
 * @created 2018年9月14日 下午9:48:51
 */
@Data
public abstract class CloudStorageService {
   
    private static final Logger log = LoggerFactory.getLogger(CloudStorageService.class);
    /**
     * 上传附件数据库状态--无效 附件和表单分开提交时 未提交表单的情况下 数据库保存的状态为0 表示脏附件
     */
    public static final int ATT_DB_NOEFFECT = 0;
    
    /**
     * 上传附件数据库状态--有效
     */
    public static final int ATT_DB_EFFECT = 1;
    
   
    /**配置信息 子类负责初始化**/
    protected CloudStorageConfig config ;
    /**附件域名**/
    protected String domain;
    
    @Autowired
    private AttachmentService attachmentService; 
  
    private Attachment attachment;
    
    private String uploadPath;


    /**
     * 文件路径
     *
     * @param prefix
     *            前缀
     * @return 返回上传路径
     */
    public String genUploadFilePath(String fileName) {
        String fileType = FileUtils.getFileExt(fileName);
        String uploadFloder = DateUtil.dateToString(new Date(), DateStyle.YYYYMM);
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String uploadFileName = DateUtil.dateToString(new Date(), DateStyle.HHmmssS) + uuid.substring(0, 5)+"."+fileType;
        return "/"+uploadFloder+"/"+uploadFileName;
    }
    
    /**
     * 
     * 保存 一批附件
     * @param files
     * @param uploadStatus
     * @return
     * @throws Exception 
     */
    public String upload(List<MultipartFile> files,int uploadStatus) throws Exception{
        StringBuffer uploadedFilePath = new StringBuffer();//已上传的附件
        for(MultipartFile file:files){
            uploadedFilePath.append(",");
            try {
                uploadedFilePath.append(upload(file,uploadStatus));
            } catch (Exception e) {
                uploadedFilePath.deleteCharAt(0);
                log.error("附件：{}上传失败！回滚已上传的附件",file.getOriginalFilename());
                delete(uploadedFilePath.toString().split(","));
                throw e;
            }
        }
        if(uploadedFilePath.length()>0){
            uploadedFilePath.deleteCharAt(0);
        }
        return uploadedFilePath.toString();
    }
    
    /**
     * 上传文件
     * @param uploadStatus
     * @return
     * @throws IOException
     */
    public String upload(MultipartFile file ,int uploadStatus) throws Exception{
        String fileName = file.getOriginalFilename();
        uploadPath = genUploadFilePath(fileName);
        //上传到云文件服务器 （抽象方法 依赖子类实现）
        String path = upload(file.getInputStream(),FileUtils.getPathPart(uploadPath), FileUtils.getFileName(uploadPath));
        //保存文件信息
        attachment = new Attachment(fileName,FileUtils.getFileName(uploadPath)
                ,FileUtils.getFileExt(fileName),path,uploadStatus);
        attachment.setFileSize(file.getSize());//文件大小
        attachmentService.save(attachment);
        return path;
    }
    
    /**
     * 上传文件
     * @param uploadStatus
     * @return
     * @throws Exception 
     */
    public String upload(InputStream inputStream ,String fileName,int uploadStatus) throws Exception{
        MultipartFile multipartFile = new MockMultipartFile(fileName,fileName,ContentType.APPLICATION_OCTET_STREAM.toString(),
                inputStream);
        return upload(multipartFile,uploadStatus);
    }
    
    /**
     * 上传文件
     * @param uploadStatus
     * @return
     * @throws Exception 
     */
    public String upload(byte[] bytes ,String fileName,int uploadStatus) throws Exception{
        MultipartFile multipartFile = new MockMultipartFile(fileName,fileName,ContentType.APPLICATION_OCTET_STREAM.toString(),
                new ByteArrayInputStream(bytes));
        return upload(multipartFile,uploadStatus);
    }
    
    
    /**
     *获取当前jar包所在系统中的目录
     */
    public File getBaseJarPath() {
        ApplicationHome home = new ApplicationHome(getClass());
        File jarFile = home.getSource();
        return jarFile.getParentFile();
    }
    
    /**
     * 
     * 描述 上传到腾讯云
     * @author Fandy Liu
     * @return
     * @throws Exception
     */
    public String uploadVideo(MultipartFile file ,int uploadStatus) throws Exception{
        String tempPath =  getBaseJarPath().getPath()+File.separator+UUID.randomUUID().toString()+file.getOriginalFilename();
        try {
            file.transferTo(new File(tempPath));
            config = SpringUtils.getBean(CloudStorageConfig.class);
            VodApi vodApi = new VodApi(config.getQcloudSecretId(), config.getQcloudSecretKey());
            VodUploadCommitResponse response = vodApi.upload(tempPath);
            String vUrl = response.getVideo().getUrl();
            attachment = new Attachment(file.getOriginalFilename(),vUrl
                    ,FileUtils.getFileExt(file.getOriginalFilename()),vUrl,uploadStatus);
            attachment.setFileSize(file.getSize());
            attachmentService.save(attachment);
            return vUrl;
        } catch (Exception e) {
           throw e;
        }finally{
            FileUtils.deleteFile(tempPath);
        }
    }
    

    /**
     * 
     * 文件上传抽象方法 依赖子类实现
     * @param inputStream 字节流
     * @param uploadPath  文件路径 不包含文件名
     * @param fileName    文件名
     * @return
     * @throws IOException 
     * @throws Exception 
     */
    public abstract String upload(InputStream inputStream,String uploadPath ,String fileName) throws  Exception;
    
    /**
     * 
     * 下载文件
     * @param pathname
     * @param filename
     * @param localpath
     * @return
     * @throws Exception
     */
    public abstract boolean download(String pathname, String filename, String localpath) throws Exception;
    
    /**
     * 
     * 删除文件
     * @param pathname
     * @param filename
     * @return
     * @throws Exception
     */
    public abstract boolean delete(String pathname, String filename) throws Exception;
    
    /**
     * 删除一个附件
     * @param netPath
     *           网络图片地址
     * @return
     * @throws Exception 
     */
    public boolean delete(String netPath) throws Exception{
        try {
            String pathName = FileUtils.getPathPart(netPath).replace(domain, "");
            String fileName = FileUtils.getFileName(netPath);
            delete(pathName,fileName);
            //删除数据库附件信息
            Attachment att= new Attachment();
            att.setFilePath(netPath);
            attachmentService.deleteByWhere(att);
            return true;
        } catch (Exception e) {
           log.debug("附件：[{}]删除失败",netPath);
           log.error("删除附件异常",e.getCause());
           throw e;
        }
    }
    
    /**
     * 删除一批附件
     * @param filePath
     * @return
     * @throws Exception 
     */
    public boolean delete(String[] netPaths) throws Exception{
        Preconditions.checkNotNull(netPaths, "删除的文件路径：netPaths不能为空!");
        for(String netPath : netPaths){
            if(StringUtils.isNotBlank(netPath))
               delete(netPath);
        }
        return  Boolean.TRUE;
    }
    

}
