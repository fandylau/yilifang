package com.cum3.yilifang.framework.api.ocr.ai.service.impl;

import java.io.IOException;
import java.util.HashMap;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.baidu.aip.ocr.AipOcr;
import com.baidu.aip.speech.AipSpeech;
import com.cum3.yilifang.framework.api.ocr.ai.bean.WordResultRequestBean;
import com.cum3.yilifang.framework.api.ocr.ai.bean.WordResultResponseObject;
import com.cum3.yilifang.framework.api.ocr.ai.service.AipOcrService;
import com.cum3.yilifang.framework.common.utils.JSONUtils;
import com.cum3.yilifang.framework.config.OcrConfig;


@Service("aipOcrService")
public class AipOcrServiceImpl implements AipOcrService {
	private Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	protected OcrConfig ocrConfig;
	private AipOcr aipOcr;
	private AipSpeech aipSpeech;
	
	//bean初始化就初始化它
	@PostConstruct
	public void createAipOcr(){
		aipOcr=new AipOcr(ocrConfig.getAppId(), ocrConfig.getApiKey(), ocrConfig.getSecretKey());
		aipSpeech = new AipSpeech(ocrConfig.getAppId(), ocrConfig.getApiKey(), ocrConfig.getSecretKey());
	}
	
	@Override
	public WordResultResponseObject basicGeneral(WordResultRequestBean requestBean) {
		try {
			int choiceAipByParam=this.ChoiceAipByParam(requestBean.getImagePath(), requestBean.getUrl());
			HashMap<String, String> options = this.createRequestParamHashMap(requestBean);
			log.info("开始解析获取参数:{}",options);
			if (choiceAipByParam == 1) {
				//使用本地地址
				log.info("图片地址:" + requestBean.getImagePath());
				JSONObject basicJsonObject = aipOcr.basicGeneral(requestBean.getImagePath(), options);
				log.info("baidu yun aipOcr jsonObject：" + basicJsonObject);
				
				WordResultResponseObject bean =JSONUtils.getObject(basicJsonObject.toString(),
						WordResultResponseObject.class);
				log.info("analytic picture bean：" + bean);
				return bean;
			} else if (choiceAipByParam == 2) {
				//使用网络地址
				log.info("图片地址:" + requestBean.getUrl());
				JSONObject basicJsonObject = aipOcr.basicGeneralUrl(requestBean.getUrl(), options);
				log.info("baidu yun aipOcr jsonObject：" + basicJsonObject);
				
				WordResultResponseObject bean =JSONUtils.getObject(basicJsonObject.toString(),
						WordResultResponseObject.class);
				
				log.info("analytic picture bean：{}",bean);
				return bean;
			} 
			
		} catch (Exception e) {
			log.error("baidu yun aipOcr error:",e);
		}
		return null;
	}

	@Override
	public WordResultResponseObject enhancedGeneral( WordResultRequestBean requestBean) {

		try {
			int choiceAipByParam=this.ChoiceAipByParam(requestBean.getImagePath(), requestBean.getUrl());
			HashMap<String, String> options = this.createRequestParamHashMap(requestBean);
			log.info("开始解析获取参数:" + options);
			if (choiceAipByParam == 1) {
				//使用本地地址
				log.info("图片地址:" + requestBean.getImagePath());
				JSONObject basicJsonObject = aipOcr.enhancedGeneral(requestBean.getImagePath(), options);
				log.info("baidu yun aipOcr jsonObject：{}",basicJsonObject);
				WordResultResponseObject bean= JSONUtils.getObject(basicJsonObject.toString(), WordResultResponseObject.class);

				log.info("analytic picture bean：" + bean);
				return bean;
			} else if (choiceAipByParam == 2) {
				//使用网络地址
				log.info("图片地址:" + requestBean.getUrl());
				JSONObject basicJsonObject = aipOcr.enhancedGeneralUrl(requestBean.getUrl(), options);
				log.info("baidu yun aipOcr jsonObject：" + basicJsonObject);
				
				WordResultResponseObject bean= JSONUtils.getObject(basicJsonObject.toString(), WordResultResponseObject.class);
				
				log.info("analytic picture bean：" + bean);
				return bean;
			} 
		} catch (Exception e) {
			log.error("baidu yun aipOcr error:",e);
		}
		return null;
	}

	@Override
	public WordResultResponseObject webImage(WordResultRequestBean requestBean) {
		try {
			// TODO Auto-generated method stub
			HashMap<String, String> options = this.createRequestParamHashMap(requestBean);
			log.info("开始解析获取参数:{}" , options);
			//使用本地地址
			log.info("图片地址:{}",requestBean.getImagePath());
			JSONObject basicJsonObject = aipOcr.webImage(requestBean.getImagePath(), options);
			log.info("baidu yun aipOcr jsonObject：{}",basicJsonObject);
			WordResultResponseObject bean =JSONUtils.getObject(basicJsonObject.toString(), WordResultResponseObject.class);
			log.info("analytic picture bean：{}",bean);
			return bean;
		} catch (Exception e) {
			log.error("baidu yun aipOcr error:",e);
		}
		return null;
	}
	
	
	@Override
    public Object idCard(MultipartFile file,String idCardSide) throws IOException {
	    // 传入可选参数调用接口
	    HashMap<String, String> options = new HashMap<String, String>();
	    options.put("detect_direction", "true");
	    options.put("detect_risk", "false");
	    //System.out.println(aipOcr.idcard(file.getBytes(), idCardSide,options).toString());
	    return aipOcr.idcard(file.getBytes(), idCardSide,options).toString();
    }
	
	
	private int ChoiceAipByParam(String path,String url){
		if(StringUtils.isNotEmpty(path)){
			return 1;
		}else if(StringUtils.isNotEmpty(url)){
			return 2;
		}
		return 0;
	}
	
	
	private  HashMap<String, String> createRequestParamHashMap(WordResultRequestBean requestBean){
		if(requestBean!=null){
			HashMap<String, String> paramMap=new HashMap<String, String>();
			if(StringUtils.isNotEmpty(requestBean.getDetectLanguage())){
				paramMap.put("detect_language", requestBean.getDetectLanguage());
			}
			if(StringUtils.isNotEmpty(requestBean.getLanguageType())){
				paramMap.put("language_type", requestBean.getLanguageType());
			}
			if(StringUtils.isNotEmpty(requestBean.getProbability())){
				paramMap.put("probability", requestBean.getProbability());
			}
			if(requestBean.isDetectDirection() == true){
				paramMap.put("detect_direction	", requestBean.isDetectDirection()+"");
			}
		}	
		return new HashMap<String, String>();
	}

    @Override
    public byte[] text2Audio(String text) throws IOException {
        text =  Jsoup.parse(text).text();
       // Preconditions.checkState(StringUtils.isEmpty(text), "文字内容不能为空");
        return aipSpeech.synthesis(text, "zh", 1, null).getData();
    }

    
	
}
