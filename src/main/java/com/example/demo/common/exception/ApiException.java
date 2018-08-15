package com.example.demo.common.exception;

import java.text.MessageFormat;


/**
 * 异常
 * Created by zhouwei on 2018/1/3
 */
public class ApiException extends Exception {
	private static final long serialVersionUID = 3526982762751176372L;

	/**
	 * 错误码
	 */
	private String code;
	
	/**
	 * 错误信息
	 */
	private String textMessage;

	public ApiException() {

	}
	
	public ApiException(ExceptionCode excCode, Object... args) {
		code = excCode.code();
		if(args!=null && args.length>0) {
			textMessage = MessageFormat.format(excCode.desc(), args);
		} else {
			textMessage = excCode.desc();
		}
	}
	
	public ApiException(String textMessage) {
		this.textMessage = textMessage;
	}
	
	public ApiException(String code, String textMessage) {
		this.code = code;
		this.textMessage = textMessage;
	}
	
	public ApiException(String textMessage, Throwable cause) {
		super(textMessage, cause);
		this.textMessage = textMessage;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTextMessage() {
		return textMessage;
	}

	public void setTextMessage(String textMessage) {
		this.textMessage = textMessage;
	}
}