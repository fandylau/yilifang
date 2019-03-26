package com.cum3.yilifang.project.system.attachment.controller;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cum3.yilifang.framework.api.oss.CloudStorageService;
import com.cum3.yilifang.framework.api.oss.FtpCloudStorageService;
import com.cum3.yilifang.framework.web.controller.AjaxResult;
import com.cum3.yilifang.framework.web.controller.BaseController;
import com.cum3.yilifang.framework.web.controller.IndexController;
import com.cum3.yilifang.framework.web.domain.Media;
import com.cum3.yilifang.project.system.attachment.domain.MultipartFileParam;
import com.cum3.yilifang.project.system.attachment.service.StorageService;
import com.cum3.yilifang.project.system.attachment.utils.Constants;
import com.cum3.yilifang.project.system.attachment.utils.ResultStatus;

/**
 * 文件上传控制层
 * 
 * @author Fandy Liu
 * @created 2018年9月9日 上午12:36:48
 */
@Controller
@RequestMapping(value = "/attach")
public class AttathmentController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private StorageService storageService;
   
    @Autowired
    private  CloudStorageService cloudStorageService;
     /**
      * 上传文件
      * @author Fandy Liu
      * @created 2018年9月16日 下午11:11:49
      * @param file
      * @return
      * @throws Exception
      */
     @RequestMapping("/upload")
     @ResponseBody
     public AjaxResult upload(@RequestParam("file") MultipartFile file) throws Exception {
         cloudStorageService.upload(file,CloudStorageService.ATT_DB_NOEFFECT);
         String url = cloudStorageService.getAttachment().getFilePath();
         return success(new Media(url,CloudStorageService.ATT_DB_NOEFFECT));
     }
     
    //这是个测试
    @GetMapping("/ftp")
    @ResponseBody
    public Object ftp() throws Exception {
      FtpCloudStorageService ftpCloudStorageService = (FtpCloudStorageService) cloudStorageService;
      long start=System.currentTimeMillis(); //获取开始时间
      for (int i = 0; i < 10; i++) {
            boolean b = ftpCloudStorageService.upload("/upload/test4", "qbz222.jpg", "d://qbz.jpg");
            System.out.println("上传文件==>" + b);
           // boolean b1 = ftpCloudStorageService.download("/upload/test4", "qbz222.jpg", "e://");
           // System.out.println("下载文件==>" + b1);
      } 
      //要测试的程序或方法
      long end=System.currentTimeMillis(); //获取结束时间
      System.out.println("程序运行时间： "+(end-start)+"ms");
      System.out.println(ftpCloudStorageService.retrieveFileNames("/upload/test4"));
      return success("成功");
    }

    /**
     * 秒传判断，断点判断
     * 
     * @param md5
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "checkFileMd5", method = RequestMethod.POST)
    @ResponseBody
    public Object checkFileMd5(String md5) throws IOException {
        Object processingObj = stringRedisTemplate.opsForHash().get(Constants.FILE_UPLOAD_STATUS, md5);
        if (processingObj == null) {// 表示该文件还未上传过
            return success(ResultStatus.NO_HAVE);
        }
        String processingStr = processingObj.toString();
        boolean processing = Boolean.parseBoolean(processingStr);
        String value = stringRedisTemplate.opsForValue().get(Constants.FILE_MD5_KEY + md5);
        if (processing) {
            return success(ResultStatus.IS_HAVE);
        } else {
            File confFile = new File(value);
            byte[] completeList = FileUtils.readFileToByteArray(confFile);
            List<String> missChunkList = new LinkedList<>();
            for (int i = 0; i < completeList.length; i++) {
                if (completeList[i] != Byte.MAX_VALUE) {
                    missChunkList.add(i + "");
                }
            }
            return success(ResultStatus.ING_HAVE.ordinal(), missChunkList);
        }
    }

    /**
     * 上传大文件
     *
     * @param param
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/uploadLarge", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult fileUpload(MultipartFileParam param, HttpServletRequest request) {
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (isMultipart) {
            logger.info("upload attachment start....");
            try {
                // 方法1
                // storageService.uploadFileRandomAccessFile(param);
                // 方法2 这个更快点
                storageService.uploadFileByMappedByteBuffer(param);
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("attachment upload failed {}", param.toString());
            }
            logger.info("upload attachment end....");
        }
        return success("上传成功");
    }

}
