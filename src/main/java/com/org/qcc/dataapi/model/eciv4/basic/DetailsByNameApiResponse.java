package com.org.qcc.dataapi.model.eciv4.basic;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.List;


/**
 * 返回参数类
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class DetailsByNameApiResponse {

    /**
     * 主键，最大长度100
     */
    private String keyNo;

    /**
     * 企业名称，最大长度128
     */
    private String name;

    /**
     * 根据企业性质返回不同的值：
     * 0/1/4/6/7/9/10/11/-1 为中国境内企业时返回工商注册号
     * 3 为中国香港企业时返回企业编号
     * 5 为中国台湾企业时返回企业编号
     * 最大长度50
     */
    private String no;

    /**
     * 登记机关，最大长度500
     */
    private String belongOrg;

    /**
     * 法定代表人ID，最大长度100
     */
    private String operId;

    /**
     * 法定代表人名称，最大长度1000
     */
    private String operName;

    /**
     * 成立日期，最大长度50
     */
    private String startDate;

    /**
     * 吊销日期，最大长度50
     */
    private String endDate;

    /**
     * 登记状态，最大长度100
     */
    private String status;

    /**
     * 省份，最大长度32
     */
    private String province;

    /**
     * 更新日期，最大长度50
     */
    private String updatedDate;

    /**
     * 根据企业性质返回不同的值：
     * 中国境内企业返回统一社会信用代码；
     * 中国香港企业返回商业登记号码
     * 最大长度50
     */
    private String creditCode;

    /**
     * 注册资本，最大长度50
     */
    private String registCapi;

    /**
     * 企业类型，最大长度100
     */
    private String econKind;

    /**
     * 注册地址，最大长度1000
     */
    private String address;

    /**
     * 经营范围，最大长度5000
     */
    private String scope;

    /**
     * 营业期限始，最大长度50
     */
    private String termStart;

    /**
     * 营业期限至，最大长度50
     */
    private String termEnd;

    /**
     * 核准日期，最大长度50
     */
    private String checkDate;

    /**
     * 组织机构代码，最大长度50
     */
    private String orgNo;

    /**
     * 是否上市，0-未上市，1-上市，最大长度5
     */
    private String isOnStock;

    /**
     * 股票代码，优先显示A股代码，最大长度32
     */
    private String stockNumber;

    /**
     * 上市类型（A股、港股、美股、新三板、新四板），最大长度10
     */
    private String stockType;

    /**
     * 曾用名列表
     */
    private List<OriginalName> originalName;

    /**
     * 企业Logo地址，最大长度500
     */
    private String imageUrl;

    /**
     * 企业性质，最大长度10
     * 0-大陆企业，1-社会组织，3-中国香港公司，5-中国台湾公司等
     */
    private String entType;

    /**
     * 实缴资本，最大长度50
     */
    private String recCap;

    /**
     * 注销吊销信息
     */
    private RevokeInfo revokeInfo;

    /**
     * 行政区域
     */
    private Area area;
}

/**
 * 曾用名类
 */
@Data
class OriginalName {

    /**
     * 曾用名，最大长度128
     */
    private String name;

    /**
     * 变更日期，最大长度50
     */
    private String changeDate;
}

/**
 * 注销吊销信息类
 */
@Data
class RevokeInfo {

    /**
     * 注销日期，最大长度50
     */
    private String cancelDate;

    /**
     * 注销原因，最大长度2000
     */
    private String cancelReason;

    /**
     * 吊销日期，最大长度50
     */
    private String revokeDate;

    /**
     * 吊销原因，最大长度2000
     */
    private String revokeReason;
}

/**
 * 行政区域类
 */
@Data
class Area {

    /**
     * 省份，最大长度32
     */
    private String province;

    /**
     * 城市，最大长度32
     */
    private String city;

    /**
     * 区域，最大长度32
     */
    private String county;
}
