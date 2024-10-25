package com.org.qcc.dataapi.config.customer;

import lombok.Data;

@Data
public class AlertInfo<T> {
	private String statusCode;
	private String message;
	private T result;
	private Integer level; // 预警级别
	private String alertContent; // 预警内容

	public AlertInfo(String statusCode, String message, String alertContent, T result) {
		this(statusCode, message, 1, alertContent, result);
	}

	public AlertInfo(String statusCode, String message, Integer level, String alertContent, T result) {
		this.statusCode = statusCode;
		this.message = message;
		this.result = result;
		this.level = level;
		this.alertContent = alertContent;
	}
}