package com.cum3.yilifang.framework.api.ocr.ai.bean;

/**
 * 请求的requestBean
 * @author Fandy Liu
 * @created 2018年9月17日 下午1:30:54
 */
public class WordResultRequestBean{
	
	/**
	 * 和url二选一
	 * 图像数据，base64编码后进行urlencode，要求base64编码和urlencode后大小不超过4M，
	 * 最短边至少15px，最长边最大4096px,支持jpg/png/bmp格式，当image字段存在时url字段失效
	 * 
	 */
	private String imagePath;
	
	/**
	 * 和imagePath二选一
	 * 图片完整URL，URL长度不超过1024字节，URL对应的图片base64编码后大小不超过4M，
	 * 最短边至少15px，最长边最大4096px,支持jpg/png/bmp格式，当image字段存在时url字段失效，不支持https的图片链接
	 */
	private String url;

	/**
	 * 识别语言类型，默认为CHN_ENG。可选值包括：
	- CHN_ENG：中英文混合；
	- ENG：英文；
	- POR：葡萄牙语；
	- FRE：法语；
	- GER：德语；
	- ITA：意大利语；
	- SPA：西班牙语；
	- RUS：俄语；
	- JAP：日语；
	- KOR：韩语
	 */
	private String languageType;
	
	
	/**
	 * 是否检测图像朝向，默认不检测，即：false。朝向是指输入图像是正常方向、逆时针旋转90/180/270度。可选值包括:
		- true：检测朝向；
		- false：不检测朝向。
	 */
	private boolean detectDirection;
	
	/**
	 * 	是否检测语言，默认不检测。当前支持（中文、英语、日语、韩语）
	 */
	private String detectLanguage;
	
	
	/**
	 * 是否返回识别结果中每一行的置信度
	 */
	private String probability;


	


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getLanguageType() {
		return languageType;
	}


	public void setLanguageType(String languageType) {
		this.languageType = languageType;
	}


	public boolean isDetectDirection() {
		return detectDirection;
	}


	public void setDetectDirection(boolean detectDirection) {
		this.detectDirection = detectDirection;
	}


	public String getDetectLanguage() {
		return detectLanguage;
	}


	public void setDetectLanguage(String detectLanguage) {
		this.detectLanguage = detectLanguage;
	}


	public String getProbability() {
		return probability;
	}


	public void setProbability(String probability) {
		this.probability = probability;
	}


	public String getImagePath() {
		return imagePath;
	}


	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
}
