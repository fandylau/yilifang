package com.cum3.yilifang.framework.api.ocr.ai.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.cum3.yilifang.framework.api.ocr.ai.bean.WordResultRequestBean;
import com.cum3.yilifang.framework.api.ocr.ai.bean.WordResultResponseObject;
/**
 * 保留百度云基础版本的图片识别接口
 * @author Fandy Liu
 * @created 2018年9月17日 下午1:27:24
 */
public interface AipOcrService {

	/**
	 * 通用的文字识别接口 基础版
	 * @param path 图片路径,当图片为本地图片时，url为null，path和url二选一
	 * @param url 图片地址，目前仅支持PNG、JPG、JPEG、BMP，不支持https的图片
	 * @param requestBean
	 * @return
	 */
	public WordResultResponseObject basicGeneral(WordResultRequestBean requestBean);
	
	
	/**
	 * 通用的文字识别接口  生僻字版本
	 * @param path 图片路径,当图片为本地图片时，url为null，path和url二选一
	 * @param url 图片地址，目前仅支持PNG、JPG、JPEG、BMP，不支持https的图片
	 * @param requestBean
	 * @return
	 */
	public WordResultResponseObject enhancedGeneral(WordResultRequestBean requestBean);
	
	/**
	 * 网络图片文字识别接口  
	 * @param path 图片路径,图片为本地图片
	 * @param requestBean内仅有detectDirection、detectLanguage两个参数可用
	 * @return
	 */
	public WordResultResponseObject webImage(WordResultRequestBean requestBean);
	
    /**
     * 身份证识别
     * @param file
     * @return 返回http地址
     */
    public Object idCard(MultipartFile file,String idCardSide) throws IOException;
    
    
    /**
     * 文字转语音
     * @param file
     * @return 返回http地址
     */
    public byte[] text2Audio(String text) throws IOException;
    
	

}
