package com.example.demo.common.exception;

/**
 * 错误码
 * Created by zhouwei on 2018/1/3
 */
public enum ExceptionCode {

	/** 成功 */
	SUCC("0000", "成功"),
	/** 失败(包含验证失败) */
	FAIL("0010", "失败"),
	/** 系统异常 */
	SYS_ERR("0020", "系统错误"),
	/** 请求重复 */
	REQ_DUPLICATE("0030", "请求重复"),
	/** 请求重复 */
	SYS_UPG("0040", "系统维护中，请稍后再试"),
	/** 系统超时 */
	SYS_TIME_OUT("0050", "系统超时，请勿频繁操作"),
	/** 操作失败 */
	OPERATE_FAIL("0060", "操作失败，请稍后重试"),
	/** 请求信息为空 */
	REQ_INFO_EMPTY("0100", "请求信息不能为空"),

	;

	/** 错误编码 */
	private String code;

	/** 错误的响应信息 */
	private String desc;

	private ExceptionCode(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	/**
	 * 根据参数获取对应枚举类型
	 */
	public static ExceptionCode get(String code) {
		for (ExceptionCode ele : values()) {
			if (ele.code.equals(code)) {
				return ele;
			}
		}
		return null;
	}

	/**
	 * 判断是否成功
	 * @return true:成功,false:失败
	 */
	public static boolean isSucc(String code) {
		return SUCC.code.equals(code);
	}

	/**
	 * 判断是否成功
	 * @return true:成功,false:失败
	 */
	public static boolean isSucc(ExceptionCode ele) {
		return SUCC.equals(ele);
	}

	public String code() {
		return code;
	}

	public String desc() {
		return desc;
	}

	public String toString() {
		return this.code + "_" + this.desc;
	}
}
