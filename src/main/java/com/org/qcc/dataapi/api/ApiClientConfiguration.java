package com.org.qcc.dataapi.api;

import com.org.qcc.dataapi.common.encryp.EncryptionAlgorithm;
import com.org.qcc.dataapi.config.ApiStatusCodeConfig;
import com.org.qcc.dataapi.config.customer.AlertHandler;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.client.RestTemplate;

@Data
@Builder
public class ApiClientConfiguration {

  // 用户Key
  private String key;

  // 用户密钥
  private String secretKey;

  // 请求域名
  private String domain;

  // 密钥加密算法
  private EncryptionAlgorithm tokeAlgorithm;

  // 超时时间
  private RestTemplate restTemplate;

  private ApiStatusCodeConfig apiStatusCodeConfig;

  // 告警处理器
  private List<AlertHandler> alertHandlers;
}
