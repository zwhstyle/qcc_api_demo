package com.org.qcc.dataapi.config;

import com.org.qcc.dataapi.config.customer.AbstractErrorCodeLoader;
import com.org.qcc.dataapi.config.customer.ApiStatusCode;
import java.util.Map;

public class ApiStatusCodeConfig {

  private final AbstractErrorCodeLoader errorCodeLoader;

  public ApiStatusCodeConfig(AbstractErrorCodeLoader errorCodeLoader) {
    this.errorCodeLoader = errorCodeLoader;
  }

  public Map<String, ApiStatusCode> getApiStatusMap() {
    return errorCodeLoader.getApiStatusCodeMap();
  }

  public ApiStatusCode getCode(String code) {
    return errorCodeLoader.getCode(code);
  }
}
