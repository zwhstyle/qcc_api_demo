package com.org.qcc.dataapi.config.customer;

import java.util.Map;
import lombok.Getter;

@Getter
public abstract class AbstractErrorCodeLoader {

  public abstract ApiStatusCode getCode(String code);

  public abstract Map<String, ApiStatusCode> getApiStatusCodeMap();
}
