package com.cum3.yilifang.framework.web.controller;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cum3.yilifang.framework.api.ocr.ai.bean.WordResultRequestBean;
import com.cum3.yilifang.framework.api.ocr.ai.bean.WordResultResponseObject.WordsResultBean;
import com.cum3.yilifang.framework.api.ocr.ai.service.AipOcrService;
import com.cum3.yilifang.framework.api.oss.CloudStorageService;
import com.cum3.yilifang.framework.common.utils.FileUtils;
import com.cum3.yilifang.framework.common.utils.JSONUtils;
import com.cum3.yilifang.framework.common.utils.ServletUtils;
import com.cum3.yilifang.framework.common.utils.date.DateUtil;
import com.cum3.yilifang.framework.datasource.DynamicDataSource;
import com.cum3.yilifang.project.weixin.smallroutine.mine.domain.IdCardAuthentication;
import com.cum3.yilifang.project.weixin.smallroutine.mine.service.IdCardAuthenticationService;
import com.google.common.collect.ImmutableMap;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;


/**
 * 文件上传控制层
 * @author Fandy Liu
 * @created 2018年9月9日 上午12:36:48
 */
@RestController
@RequestMapping("/ocr")
public class OcrController extends BaseController{
    
   @Autowired
   private AipOcrService aipOcrService;
   
   @Autowired
   protected CloudStorageService cloudStorageService;
   
   @Autowired
   protected IdCardAuthenticationService idCardAuthenticationService;
   /**姓名**/
   private  static final String PATH_IDCARD_NAME = "$.words_result.姓名.words";
   /**名族**/
   private  static final String PATH_IDCARD_NATION = "$.words_result.民族.words";
   /**住址**/
   private static final String PATH_IDCARD_ADRESS = "$.words_result.住址.words";
   /**公民身份号码**/
   private static final String PATH_IDCARD_IDCARDNO = "$.words_result.公民身份号码.words";
   /**出生**/
   private static final String PATH_IDCARD_BIRTHDAY = "$.words_result.出生.words";
   /**性别**/
   private static final String PATH_IDCARD_SEX = "$.words_result.性别.words";
   /**签发机关**/
   private static final String PATH_IDCARD_ACCEPTORG = "$.words_result.签发机关.words";
   /**签发日期**/
   private static final String PATH_IDCARD_ACCEPTDATE = "$.words_result.签发日期.words";
   /**失效日期**/
   private static final String PATH_IDCARD_INVALIDATEDATE = "$.words_result.失效日期.words";
    /**
     * 上传文件
     * @author Fandy Liu
     * @created 2018年9月16日 下午11:11:49
     * @param file
     * @return
     * @throws Exception
     */
    @RequestMapping("/idCard")
    @ResponseBody
    public Object idCard(@RequestParam("file") MultipartFile file,
                         @RequestParam("idCardSide") String idCardSide,
                         @RequestParam(value="id" ,required=false) Integer id,
                         @RequestParam(value="memberName" ,required=false) String memberName,
                         @RequestParam(value="idCard",required=false) String idCard) throws Exception {
        Object o = aipOcrService.idCard(file, idCardSide);
        System.out.println(o.toString());
        IdCardAuthentication idcard = new IdCardAuthentication();
        if("front".equals(idCardSide)){
            String name = getStrByPath(PATH_IDCARD_NAME,o.toString()).replace("\"", "");
            String nation = getStrByPath(PATH_IDCARD_NATION,o.toString()).replace("\"", "");
            String adress = getStrByPath(PATH_IDCARD_ADRESS,o.toString()).replace("\"", "");
            String idCardNo = getStrByPath(PATH_IDCARD_IDCARDNO,o.toString()).replace("\"", "");
            String birthDay = getStrByPath(PATH_IDCARD_BIRTHDAY, o.toString()).replace("\"", "");
            String sex = getStrByPath(PATH_IDCARD_SEX,o.toString()).replace("\"", "");
            if(StringUtils.isNotEmpty(memberName)&&!memberName.equals(name)||
                    StringUtils.isNotEmpty(idCard)&&!idCard.equals(idCardNo)     ){
                return  error("身份证认证失败[姓名或身份证号码与证件不匹配]!");
            }
            idcard.setOpenId(ServletUtils.getParameter("openId"));
            idcard.setMemberName(name);
            idcard.setAddress(adress);
            idcard.setIdCard(idCardNo);
            idcard.setNation(nation);
            idcard.setBirthday(DateUtil.stringToDate(birthDay));
            idcard.setStatus(2);
            idcard.setDelFlag(0);
            idcard.setSex(sex);
            String filePath = cloudStorageService.upload(file, CloudStorageService.ATT_DB_EFFECT);
            idcard.setIdCardFrontPict(filePath);
            DynamicDataSource.setDataSourceKey("auth");
            idCardAuthenticationService.save(idcard);
            DynamicDataSource.setDataSourceKey("base");
            return success(ImmutableMap.of("pkColumn","id","pkValue",idcard.getId(),"uploadedUrls",filePath));
        }
        if("back".equals(idCardSide)){
            String filePath = cloudStorageService.upload(file, CloudStorageService.ATT_DB_EFFECT);
            DynamicDataSource.setDataSourceKey("auth");
            idcard = idCardAuthenticationService.queryById(id);
            idcard.setIdCardSidePict(filePath);
            idCardAuthenticationService.updateSelective(idcard);
            DynamicDataSource.setDataSourceKey("base");
        }
        return  success(o);
    }
    
    private String getStrByPath(String path,String jsonStr){
        try {
            ReadContext context = JsonPath.parse(jsonStr);
            return JSONUtils.toJSONString(context.read(path), false);
        } catch (Exception e) {
           return "";
        }
       
    }
    @RequestMapping("/audio")
    public Object audio() throws IOException{
        String text = "请各位村民马上到粮食站取免费最新杂交水稻育苗！";
       /// InputStream input = new ByteArrayInputStream( );
        byte[] b = aipOcrService.text2Audio(text);
        FileUtils.byte2File(b, "e:\\","test.mp3");
        return null;
    }
    
    @RequestMapping(value="/imageWord",method = {RequestMethod.POST, RequestMethod.GET})
    public Object readImageWord() throws IOException{
        WordResultRequestBean requestBean = new WordResultRequestBean();
        requestBean.setLanguageType("CHN_ENG");
        requestBean.setImagePath("C:\\Users\\fandylau\\Desktop\\鼓楼智脑\\环保图\\行政处罚分类图-5(2).jpg");
        WordsResultBean[]  rets = aipOcrService.basicGeneral(requestBean).getWords_result();;
        for(WordsResultBean wret : rets){
            System.out.println(wret.getWords());
        }
        return null;
    }
    
}
