package com.org.qcc.dataapi.api.impl;

import com.org.qcc.dataapi.BaseApiTest;
import com.org.qcc.dataapi.api.ApiResponse;
import com.org.qcc.dataapi.api.IQccApiService;
import com.org.qcc.dataapi.model.eciv4.basic.DetailsByNameApiRequest;
import com.org.qcc.dataapi.model.eciv4.basic.DetailsByNameApiResponse;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class QccApiServiceImplTest extends BaseApiTest {

  @Autowired
  @Qualifier("iqccApiService")
  private IQccApiService iqccApiService;

  @Test
  public void testGetEciv4BasicDetailsByNameApiResponse() {
    DetailsByNameApiRequest request = new DetailsByNameApiRequest();

    // TODO 使用实际参数查询结果
    request.setKeyword("企查查科技有限公司");
    ApiResponse<DetailsByNameApiResponse> response = iqccApiService.getBasicDetailsByName(request);
    String s = convertToJson(response);
    System.out.println(s);
  }
}