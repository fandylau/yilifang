package com.cum3.yilifang.framework.api.oss;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.http.entity.ContentType;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.Getter;
@Getter
public class UploadFileCache {
    
    private Map<String,Map<String,List<MultipartFile>>> tempFilePool=  Maps.newLinkedHashMap();
    /**
     * 缓存对象
     */
    private static UploadFileCache uploadFileCache = null;
    
    /**
     * 描述 获取缓存实例--单例
     * 
     * @author Fandy Liu
     * @created 2014年12月16日 上午11:46:35
     * @return
     */
    public static synchronized UploadFileCache getInstance() {
        if (uploadFileCache == null) {
            uploadFileCache = new UploadFileCache();
        }
        return uploadFileCache;
    }
    /**
     * 构造函数
     */
    private UploadFileCache() {}
    /**
     * 
     * 添加一个文件到缓存中
     * @param recNo
     * @param attField
     * @param multipartFile
     * @throws IOException 
     */
    public void add(String recNo,String attField,MultipartFile multipartFile) throws IOException{
        
      MultipartFile transferFile = new MockMultipartFile(multipartFile.getOriginalFilename(),multipartFile.getOriginalFilename(),ContentType.APPLICATION_OCTET_STREAM.toString(),
                multipartFile.getInputStream()); //转换下 
       if( !tempFilePool.containsKey(recNo)){
           Map<String,List<MultipartFile>> map = Maps.newHashMap();
           List<MultipartFile> lt = Lists.newArrayList();
           lt.add(transferFile);
           map.put(attField, lt);
           tempFilePool.put(recNo, map);
       }else{
           Map<String,List<MultipartFile>> map =  tempFilePool.get(recNo);
           if(!map.containsKey(attField)){
               List<MultipartFile> lt = Lists.newArrayList();
               lt.add(transferFile);
               map.put(attField, lt);
           }else{
               List<MultipartFile> lt = map.get(attField);
               lt.add(transferFile);
           }
       }
    }
    /**
     * 
     * 移除
     * @param recNo
     */
    public void remove(String recNo){
        tempFilePool.remove(recNo);
    }
    /**
     * 
     * 移除
     * @param recNo
     * @param attField
     */
    public void remove(String recNo,String attField){
        if(tempFilePool.containsKey(recNo)){
            tempFilePool.get(recNo).remove(attField);
        }
    }
    /**
     * 
     * 移除
     * @param recNo
     * @param attField
     * @param multipartFile
     * @throws IOException 
     */
    public void remove(String recNo,String attField,MultipartFile multipartFile) throws IOException{
        MultipartFile transferFile = new MockMultipartFile(multipartFile.getOriginalFilename(),multipartFile.getOriginalFilename(),ContentType.APPLICATION_OCTET_STREAM.toString(),
                multipartFile.getInputStream()); 
        if(tempFilePool.containsKey(recNo)){
            Map<String,List<MultipartFile>> map  =  tempFilePool.get(recNo);
            if(map.containsKey(attField)){
                List<MultipartFile> lt = map.get(attField);
                if(lt.contains(transferFile))
                    lt.remove(transferFile);
            }
        }
    }
    
    

}
