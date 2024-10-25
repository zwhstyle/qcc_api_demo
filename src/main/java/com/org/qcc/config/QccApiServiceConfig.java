package com.org.qcc.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.qcc.dataapi.api.ApiClientConfiguration;
import com.org.qcc.dataapi.api.ApiClientConfiguration.ApiClientConfigurationBuilder;
import com.org.qcc.dataapi.api.IQccApiService;
import com.org.qcc.dataapi.api.impl.QccApiServiceImpl;
import com.org.qcc.dataapi.common.encryp.EncryptionAlgorithm;
import com.org.qcc.dataapi.config.ApiStatusCodeConfig;
import com.org.qcc.dataapi.config.customer.impl.DefaultErrorCodeLoader;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class QccApiServiceConfig {

  @Bean(name = "iqccApiService")
  public IQccApiService getQccApiService() {
    ApiClientConfigurationBuilder configuration = ApiClientConfiguration.builder()
        .key("********") // 对应Key
        .secretKey("********") // 对应SecretKey
        .tokeAlgorithm(EncryptionAlgorithm.MD5) // Token 加密方式
        .domain("https://api.qichacha.com") // 域名
        .apiStatusCodeConfig(new ApiStatusCodeConfig(new DefaultErrorCodeLoader("msg/qcc_errors_convert.config"))); // 状态码转义

    // 添加预警处理器
    configuration.alertHandlers(new ArrayList<>());

    // 连接超时时间和读超时时间可以通过参数配置
    RestTemplateBuilder builder = new RestTemplateBuilder();

    // 创建 ObjectMapper 并设置命名策略
    ObjectMapper objectMapper = new ObjectMapper();

    // 大小写不敏感
    objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
    // 忽略未知字段
    objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);

    // 创建消息转换器
    MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter(objectMapper);

    // 创建 RestTemplate 并设置消息转换器
    RestTemplate restTemplate = builder
        .setConnectTimeout(Duration.ofSeconds(50)) // 根据实际进行调整
        .setReadTimeout(Duration.ofSeconds(300))// 根据实际进行调整
        .build();

    // 设置消息转换器
    restTemplate.setMessageConverters(Collections.singletonList(messageConverter));

    // 将 RestTemplate 设置到配置中
    configuration.restTemplate(restTemplate);

    return new QccApiServiceImpl(configuration.build());
  }
}