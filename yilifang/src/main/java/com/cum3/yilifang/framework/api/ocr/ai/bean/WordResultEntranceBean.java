package com.cum3.yilifang.framework.api.ocr.ai.bean;

/**
 *	请求的入口Bean
 */
public class WordResultEntranceBean extends WordResultRequestBean {
	
	/**
	 * 1基础版 2生僻字版本 3网络图片
	 */
	private String entranceSecond;

	public String getEntranceSecond() {
		return entranceSecond;
	}

	public void setEntranceSecond(String entranceSecond) {
		this.entranceSecond = entranceSecond;
	}

}
