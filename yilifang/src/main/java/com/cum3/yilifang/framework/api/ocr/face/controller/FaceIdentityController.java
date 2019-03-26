package com.cum3.yilifang.framework.api.ocr.face.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cum3.yilifang.framework.api.ocr.face.bean.FaceV3DetectBean;
import com.cum3.yilifang.framework.api.ocr.face.service.FaceIdentifyService;
import com.cum3.yilifang.framework.api.oss.CloudStorageService;
import com.cum3.yilifang.framework.common.utils.FileUtils;
import com.cum3.yilifang.framework.common.utils.JSONUtils;
import com.cum3.yilifang.framework.datasource.DynamicDataSource;
import com.cum3.yilifang.framework.web.controller.BaseController;
import com.cum3.yilifang.project.weixin.smallroutine.mine.domain.FaceAuthentication;
import com.cum3.yilifang.project.weixin.smallroutine.mine.domain.IdCardAuthentication;
import com.cum3.yilifang.project.weixin.smallroutine.mine.service.FaceAuthenticationService;
import com.cum3.yilifang.project.weixin.smallroutine.mine.service.IdCardAuthenticationService;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

@RestController
@RequestMapping("/face")
public class FaceIdentityController  extends BaseController{
    private static final Logger log = LoggerFactory.getLogger(FaceIdentityController.class);
    @Autowired
    private FaceIdentifyService faceIdentifyService;
    @Autowired
    private IdCardAuthenticationService idCardAuthenticationService;
    @Autowired
    private FaceAuthenticationService faceAuthenticationService;
    @Autowired
    protected CloudStorageService cloudStorageService;
    
    @RequestMapping("/detect")
    @ResponseBody
    public Object detect(HttpServletRequest request,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        Map<String, Object> map = new HashMap<String, Object>();

        try {
            if (null != file) {
                // 获得二进制数组
                byte[] in2b =file.getBytes();
                // 调用人脸检测的方法
                String str = faceIdentifyService.detectFace(in2b, "" + 1);
                JSONObject job =JSONObject.parseObject(faceIdentifyService.faceverify(in2b));
                System.out.println(job.toString());
                JSONObject testData = job.getJSONObject("result");
                JSON json = JSON.parseObject(str);
                FaceV3DetectBean bean = JSON.toJavaObject(json, FaceV3DetectBean.class);
                JSONArray arr = new JSONArray();
                // 美丑打分
                map.put("beauty", bean.getResult().getFace_list().get(0).getBeauty());
                // 年龄
                map.put("age", bean.getResult().getFace_list().get(0).getAge());
                // 性别
                map.put("gender", bean.getResult().getFace_list().get(0).getGender().getType());
                // 获取是否带眼睛 0-无眼镜，1-普通眼镜，2-墨镜
                map.put("glasses", bean.getResult().getFace_list().get(0).getGlasses().getType());
                // 获取是否微笑，0，不笑；1，微笑；2，大笑
                map.put("expression", bean.getResult().getFace_list().get(0).getExpression().getType());

                for (int i = 0; i < bean.getResult().getFace_list().size(); i++) {
                    JSONObject jsonObject = new JSONObject();
                    // 获取年龄
                    int ageOne = bean.getResult().getFace_list().get(i).getAge();
                    // 处理年龄
                    String age = String.valueOf(new BigDecimal(ageOne).setScale(0, BigDecimal.ROUND_HALF_UP));
                    jsonObject.put("age", age);

                    // 获取美丑打分
                    Double beautyOne = (Double) bean.getResult().getFace_list().get(i).getBeauty();
                    // 处理美丑打分
                    String beauty = String.valueOf(new BigDecimal(beautyOne).setScale(0, BigDecimal.ROUND_HALF_UP));
                    jsonObject.put("beauty", beauty);

                    // 获取性别 male(男)、female(女)
                    String gender = (String) bean.getResult().getFace_list().get(i).getGender().getType();
                    jsonObject.put("gender", gender);

                    // 获取是否带眼睛 0-无眼镜，1-普通眼镜，2-墨镜
                    String glasses = bean.getResult().getFace_list().get(i).getGlasses().getType();
                    jsonObject.put("glasses", String.valueOf(glasses));

                    // 获取是否微笑，0，不笑；1，微笑；2，大笑
                    String expression = bean.getResult().getFace_list().get(i).getExpression().getType();
                    jsonObject.put("expression", String.valueOf(expression));
                    arr.add(jsonObject);
                }
                map.put("strjson", arr.toString());
                map.put("face_liveness", testData.get("face_liveness"));
                map.put("success", true);
                log.info(
                        "call ajaxRequestInfo result:{}, arr:{}, arr.toString:{}, bean.getResult().getFace_list().size():{}",
                        map.toString(), arr, arr.toString(), bean.getResult().getFace_list().size());
            }
        } catch (Exception e) {
            log.error("call ajaxRequestInfo 检测异常 ******", e);
            e.printStackTrace();
            map.put("success", false);
            map.put("data", e.getMessage());
        }
        return map;
    }
    
    
    @RequestMapping("/match")
    @ResponseBody
    public Object match(@RequestParam(value = "openId", required = true) String openId ,
            @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
        //判断有几个人脸
        byte[] in2b =file.getBytes();
        String str = faceIdentifyService.detectFace(in2b, "" +100);
        JSON json = JSON.parseObject(str);
        FaceV3DetectBean bean = JSON.toJavaObject(json, FaceV3DetectBean.class);
        if(bean.getResult().getFace_num()>1){
            return error("您上传的照片中包含多张人脸，请上传自拍或者清晰的个人生活照！");
        }
        
        IdCardAuthentication auth = new IdCardAuthentication();
        auth.setOpenId(openId);
        DynamicDataSource.setDataSourceKey("auth");
        auth = idCardAuthenticationService.queryOne(auth);
        DynamicDataSource.setDataSourceKey("base");
        String idCardNetPath =  auth.getIdCardFrontPict();
        byte[] idCardByte = FileUtils.getImageFromNetByUrl(idCardNetPath);//从网络地址读取图片byte数组
        String retJson = faceIdentifyService.matchFace(idCardByte, file.getBytes());
       
        String scoreJsonPath = "$.result.score";
        ReadContext context = JsonPath.parse(retJson);
        String score = JSONUtils.toJSONString(context.read(scoreJsonPath), false);
        if(score != null && Float.valueOf(score.replace("\"", ""))<=80f){
           return error("人脸与身份证头像不匹配，请重新上传！");
        }
        //认证成功 上传照片
        String facePath =  cloudStorageService.upload(file, 1);
        //保存照片到人脸库
        FaceAuthentication face = new FaceAuthentication();
        face.setFacePath(facePath);
        face.setDelFlag(0);
        face.setOpenId(openId);
        face.setStatus(2);
        DynamicDataSource.setDataSourceKey("auth");
        Integer ret = faceAuthenticationService.save(face);
        DynamicDataSource.setDataSourceKey("base");
        return success(ret);
    }
}
