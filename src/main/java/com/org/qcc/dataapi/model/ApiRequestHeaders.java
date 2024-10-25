package com.org.qcc.dataapi.model;

import lombok.Data;

@Data
public class ApiRequestHeaders {

    /**
     * 验证加密值，格式为Md5(key + Timespan + SecretKey)的32位大写字符串
     * 必填
     */
    private String token;

    /**
     * Unix时间戳，精确到秒
     * 必填
     */
    private String timeSpan;
}
