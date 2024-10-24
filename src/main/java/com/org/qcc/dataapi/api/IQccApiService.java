package com.org.qcc.dataapi.api;

import com.org.qcc.dataapi.model.eciv4.basic.DetailsByNameApiRequest;
import com.org.qcc.dataapi.model.eciv4.basic.DetailsByNameApiResponse;

public interface IQccApiService {

  /**
   * 企业工商照面<br>
   * <a href="https://openapi.qcc.com/dataApi/410"> 接口方法实现</a>
   *
   * @param request 请求
   * @return 结果
   */
  ApiResponse<DetailsByNameApiResponse> getBasicDetailsByName(DetailsByNameApiRequest request);
}