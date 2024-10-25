package com.org.qcc.dataapi.config.customer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiStatusCode {

	// 业务状态码
	private String code;
	// 错误信息
	private String message;
	// 子状态码
	private String subCode;
	// 子错误信息
	private String subMsg;
	// 状态码状态(有效/无效)
	private String subCodeStatus;
	private boolean needAlert;

	public ApiStatusCode(String code, String message, String subCode, String subMsg, String subCodeStatus) {
		this(code, message, subCode, subMsg, subCodeStatus, false);
	}

	public ApiStatusCode(String code, String message, String subCode, String subMsg, String subCodeStatus, boolean needAlert) {
		this.code = code;
		this.message = message;
		this.subCode = subCode;
		this.subMsg = subMsg;
		this.subCodeStatus = subCodeStatus;
		this.needAlert = needAlert;
	}
}
