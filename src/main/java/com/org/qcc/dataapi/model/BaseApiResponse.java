package com.org.qcc.dataapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseApiResponse<T> {

  private Integer status;

  private String message;

  private String orderNumber;

  private T result;
}
