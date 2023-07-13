package com.neil.pay.wx.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author nihao
 * @date 2023/7/8
 */
@Getter
@AllArgsConstructor
public enum WxTradeTypeEnum {

    /**
     * H5
     */
    H5("/v3/pay/transactions/h5", "/v3/combine-transactions/h5", "/v3/pay/partner/transactions/h5");

    /**
     * 直连商户下单url
     */
    private final String partnerUrl;

    /**
     * 合并下单url
     */
    private final String combineUrl;

    /**
     * 服务商下单
     */
    private final String basePartnerUrl;
}
