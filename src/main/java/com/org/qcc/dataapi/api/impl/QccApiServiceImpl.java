package com.org.qcc.dataapi.api.impl;

import com.org.qcc.dataapi.api.AbstractIQccApiService;
import com.org.qcc.dataapi.api.ApiClientConfiguration;
import com.org.qcc.dataapi.api.ApiResponse;
import com.org.qcc.dataapi.api.IQccApiService;
import com.org.qcc.dataapi.model.eciv4.basic.DetailsByNameApiRequest;
import com.org.qcc.dataapi.model.eciv4.basic.DetailsByNameApiResponse;
import java.util.Map;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

public class QccApiServiceImpl extends AbstractIQccApiService implements IQccApiService {

  public QccApiServiceImpl(ApiClientConfiguration configuration) {
    super(configuration);
  }

  @Retryable(value = {HttpServerErrorException.class, ResourceAccessException.class}, backoff = @Backoff(delay = 2000))
  @Override
  public ApiResponse<DetailsByNameApiResponse> getBasicDetailsByName(DetailsByNameApiRequest request) {
    Assert.notNull(request, "DetailsByNameApiRequest Is Null");
    Map<String, Object> requestParams = convertToRequestMap(request);
    String url = "/ECIV4/GetBasicDetailsByName";
    return get(url, requestParams);
  }
}
