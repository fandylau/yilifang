package com.cum3.yilifang.framework.api.ocr.ai.bean;

import java.io.Serializable;

public class WordResultResponseObject implements Serializable{
	
    private static final long serialVersionUID = -5587000353143703330L;

    private String log_id;
	
	private WordsResultBean [] words_result;
	
	private String words_result_num;
	
	private String language;
	
	private String error_msg;
	
	private String errorMsgEn;
	
	private String error_code;
	
	public static class WordsResultBean implements Serializable{
	
        private static final long serialVersionUID = -513326385392736465L;
        
        private String words;

		public String getWords() {
			return words;
		}

		public void setWords(String words) {
			this.words = words;
		}
		
	}
	
	public String getLog_id() {
		return log_id;
	}

	public void setLog_id(String log_id) {
		this.log_id = log_id;
	}

	public WordsResultBean[] getWords_result() {
		return words_result;
	}

	public void setWords_result(WordsResultBean[] words_result) {
		this.words_result = words_result;
	}

	public String getWords_result_num() {
		return words_result_num;
	}

	public void setWords_result_num(String words_result_num) {
		this.words_result_num = words_result_num;
	}

	public String getError_msg() {
		return error_msg;
	}

	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}

	public String getError_code() {
		return error_code;
	}

	public void setError_code(String error_code) {
		this.error_code = error_code;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getErrorMsgEn() {
		return errorMsgEn;
	}

	public void setErrorMsgEn(String errorMsgEn) {
		this.errorMsgEn = errorMsgEn;
	}
}
