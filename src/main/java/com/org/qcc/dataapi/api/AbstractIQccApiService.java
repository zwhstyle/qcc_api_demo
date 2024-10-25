package com.org.qcc.dataapi.api;

import com.org.qcc.dataapi.CustomApiException;
import com.org.qcc.dataapi.common.constant.QccParamKeys;
import com.org.qcc.dataapi.common.encryp.EncryptionFactory;
import com.org.qcc.dataapi.common.encryp.EncryptionStrategy;
import com.org.qcc.dataapi.common.utils.QccObjectToMapUtil;
import com.org.qcc.dataapi.config.ApiStatusCodeConfig;
import com.org.qcc.dataapi.config.customer.AlertHandler;
import com.org.qcc.dataapi.config.customer.AlertInfo;
import com.org.qcc.dataapi.config.customer.ApiStatusCode;
import com.org.qcc.dataapi.model.BaseApiResponse;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public abstract class AbstractIQccApiService {

	protected static final Logger logger = LoggerFactory.getLogger(AbstractIQccApiService.class);

	protected final ApiClientConfiguration configuration;
	protected final RestTemplate restTemplate;
	protected final ApiStatusCodeConfig apiStatusCodeConfig;

	private final List<AlertHandler> alertHandlers;

	public AbstractIQccApiService(ApiClientConfiguration configuration) {
		this.configuration = configuration;
		this.restTemplate = configuration.getRestTemplate();
		this.apiStatusCodeConfig = configuration.getApiStatusCodeConfig();
		this.alertHandlers = configuration.getAlertHandlers();
	}

	protected <T> ApiResponse<T> get(String url, Map<String, Object> requestParams) {
		try {
			HttpHeaders httpHeaders = builderHeaders();
			String requestUri = convertToUri(getRequestUrl(url), requestParams);
			HttpEntity<Map<String, Object>> headerEntity = new HttpEntity<>(httpHeaders);
			ResponseEntity<BaseApiResponse<T>> response = restTemplate.exchange(requestUri, HttpMethod.GET, headerEntity,
					new ParameterizedTypeReference<BaseApiResponse<T>>() {
					});
			logger.debug("Request URI: {}", requestUri);
			logger.debug("Response Body: {}", response.getBody());

			// 处理API响应
			BaseApiResponse<T> apiResponse = handleResponse(response);

			// 处理API响应的状态码
			return handleApiResponseStatus(apiResponse);
    } catch (HttpClientErrorException e) {
      logger.error("客户端错误", e);
      throw new CustomApiException(e.getStatusCode().value(), "客户端错误");
    } catch (HttpServerErrorException e) {
      throw new CustomApiException(e.getStatusCode().value(), "服务器错误");
    }
  }

  private String convertToUri(String baseUrl, Map<String, Object> requestParams) {
    UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl);
    for (Map.Entry<String, Object> entry : requestParams.entrySet()) {
      uriBuilder.queryParam(entry.getKey(), entry.getValue());
    }
    return uriBuilder.build().toString();
  }

	private String getRequestUrl(String url) {
		return configuration.getDomain() + url;
	}

	private HttpHeaders builderHeaders() {
		String timeSpan = String.valueOf(System.currentTimeMillis() / 1000);
		HttpHeaders headers = new HttpHeaders();
		headers.set(QccParamKeys.TOKEN, createToken(timeSpan).toUpperCase());
		headers.set(QccParamKeys.TIME_SPAN, timeSpan);
		return headers;
	}

	private String createToken(String timeSpan) {
		EncryptionStrategy encryptionStrategy = EncryptionFactory.getEncryptionStrategy(configuration.getTokeAlgorithm());
		Assert.notNull(encryptionStrategy, "不支持的加密算法");
		try {
			return encryptionStrategy.encrypt((configuration.getKey()) + timeSpan + configuration.getSecretKey(), configuration.getKey());
		} catch (Exception e) {
			throw new CustomApiException(500, "系统加解密算法错误");
		}
	}

	protected <T> BaseApiResponse<T> handleResponse(ResponseEntity<BaseApiResponse<T>> response) {
		HttpStatus statusCode = response.getStatusCode();
		if (statusCode.is2xxSuccessful()) {
			return response.getBody();
		} else if (statusCode == HttpStatus.UNAUTHORIZED) {
			throw new CustomApiException(statusCode.value(), "未授权访问");
		} else {
			throw new CustomApiException(statusCode.value(), "API返回异常");
		}
	}

	/**
	 * 将对象转换为请求参数Map
	 *
	 * @param obj 需要转换的对象
	 * @return 请求参数Map
	 */
	protected Map<String, Object> convertToRequestMap(Object obj) {
		Map<String, Object> map = new LinkedHashMap<>();
		map.put(QccParamKeys.KEY, configuration.getKey());
		return QccObjectToMapUtil.convertToMap(map, obj);
	}

	/**
	 * 处理API响应的状态码 转化API状态码为系统业务状态码，统一管理，并添加预警机制
	 *
	 * @param response 返回结果
	 * @param <T>      返回参数
	 */
	private <T> ApiResponse<T> handleApiResponseStatus(BaseApiResponse<T> response) {

		// 检查是否有预配置的状态码转换规则
		if (Objects.nonNull(apiStatusCodeConfig)) {
			ApiStatusCode code = apiStatusCodeConfig.getCode(String.valueOf(response.getStatus()));
			if (Objects.nonNull(code)) {

				// 对状态码进行预警处理
				alert(code, response);

				// 返回转换后的结果
				return ApiResponse.of(code.getCode(), code.getMessage(), code.getSubCode(), code.getSubMsg(), code.getSubCodeStatus(), response.getResult());
			} else {
				// 对未知状态码进行预警处理
				alert(null, response);
			}
		}

		int statusCode = response.getStatus();
		return ApiResponse.of(String.valueOf(statusCode), response.getMessage(),
				String.valueOf(statusCode), response.getMessage(), null,
				response.getResult());
	}

	public <T> void alert(ApiStatusCode code, BaseApiResponse<T> response) {
		try {
			if (Objects.isNull(code)) {
				alertHandlers.forEach(e -> e.handleAlert(new AlertInfo<>(String.valueOf(response.getStatus()),
						response.getMessage(), 1000, "API新增状态码预警",
						response.getResult())));
			} else {
				// 判断是否需要预警
				if (code.isNeedAlert()) {
					alertHandlers.forEach(e ->
							e.handleAlert(new AlertInfo<>(code.getSubCode(), code.getSubMsg(), "API状态码预警", response.getResult())));
				}
			}
		} catch (Exception e) {
			logger.error("预警处理异常", e);
		}
	}
}
