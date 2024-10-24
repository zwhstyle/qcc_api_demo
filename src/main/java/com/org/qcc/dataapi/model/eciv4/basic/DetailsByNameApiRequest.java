package com.org.qcc.dataapi.model.eciv4.basic;

import com.org.qcc.dataapi.model.BaseApiRequest;
import lombok.Data;

/**
 * 请求参数类（Query）
 */
@Data
public class DetailsByNameApiRequest extends BaseApiRequest {

    /**
     * 搜索关键字，可以是企业名称、统一社会信用代码、注册号
     * 中国香港和社会组织仅支持企业名称查询
     * 必填
     */
    private String keyword;
}
