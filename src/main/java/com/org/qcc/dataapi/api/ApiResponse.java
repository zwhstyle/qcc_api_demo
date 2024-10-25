package com.org.qcc.dataapi.api;

import lombok.Data;

@Data
public class ApiResponse<T> {

	protected String subCode;
	protected String subMsg;
	protected String code;
	protected String msg;
	protected String codeStatus;
	protected T result;

	public ApiResponse(String code, String msg, String subCode, String subMsg, String codeStatus, T result) {
		this.code = code;
		this.msg = msg;
		this.subCode = subCode;
		this.subMsg = subMsg;
		this.codeStatus = codeStatus;
		this.result = result;
	}


	public static <T> ApiResponse<T> of(String code, String msg, String subCode, String subMsg, String codeStatus, T result) {
		return new ApiResponse<>(code, msg, subCode, subMsg, codeStatus, result);
	}
}
