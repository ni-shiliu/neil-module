package com.neil.pay.wx.service.impl;

import com.google.common.collect.Maps;
import com.neil.pay.exception.PayException;
import com.neil.pay.wx.config.WxPayConfig;
import com.neil.pay.wx.config.WxPayConfigHolder;
import com.neil.pay.wx.enums.WxTradeTypeEnum;
import com.neil.pay.wx.request.*;
import com.neil.pay.wx.result.WxPayOrderQueryV3Result;
import com.neil.pay.wx.result.WxPayOrderRefundV3Result;
import com.neil.pay.wx.result.WxPayRefundQueryV3Result;
import com.neil.pay.wx.result.WxPayUnifiedOrderV3Result;
import com.neil.pay.wx.service.WxPayService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Objects;

import static com.neil.pay.utils.GsonParser.GSON;

/**
 * @author nihao
 * @date 2023/7/8
 */
@Slf4j
public abstract class BaseWxPayService implements WxPayService {

    /**
     * 微信配置
     */
    protected Map<String, WxPayConfig> configMap;

    private static final String SANDBOX_URL = "/xdc/apiv2sandbox";
    private static final String QUERY_ORDER_TRADE_NO_V3_FORMAT = "%s/v3/pay/transactions/out-trade-no/%s";
    private static final String QUERY_ORDER_TRANSACTION_ID_V3_FORMAT = "%s/v3/pay/transactions/id/%s";
    private static final String CLOSE_ORDER_V3_FORMAT = "%s/v3/pay/transactions/out-trade-no/%s/close";
    private static final String REFUND_V3_FORMAT = "%s/v3/refund/domestic/refunds";
    private static final String REFUND_QUERY_V3_FORMAT = "%s/v3/refund/domestic/refunds/%s";


    @Override
    public boolean switchover(String mchId) {
        if (this.configMap.containsKey(mchId)) {
            WxPayConfigHolder.set(mchId);
            return true;
        }
        log.error("未找到商户号：{}对应的配置信息，请核实！", mchId);
        return false;
    }

    @Override
    public void setMultiConfig(Map<String, WxPayConfig> configMap) {
        this.setMultiConfig(configMap, configMap.keySet().iterator().next());
    }

    @Override
    public void setMultiConfig(Map<String, WxPayConfig> configMap, String defaultMchId) {
        this.configMap = Maps.newHashMap(configMap);
        WxPayConfigHolder.set(defaultMchId);
    }

    @Override
    public WxPayConfig getConfig() {
        if (this.configMap.size() == 1) {
            return this.configMap.values().iterator().next();
        }
        return this.configMap.get(WxPayConfigHolder.get());
    }

    @Override
    public WxPayUnifiedOrderV3Result unifiedOrderV3(WxTradeTypeEnum tradeType, WxPayUnifiedOrderV3Req request) throws PayException {
        String url = this.getPayBaseUrl() + tradeType.getPartnerUrl();
        String response = this.postV3(url, GSON.toJson(request));
        return GSON.fromJson(response, WxPayUnifiedOrderV3Result.class);
    }

    @Override
    public <T> T createOrderV3(WxTradeTypeEnum tradeTypeEnum, WxPayUnifiedOrderV3Req request) throws PayException {
        WxPayUnifiedOrderV3Result wxPayUnifiedOrderV3Result = this.unifiedOrderV3(tradeTypeEnum, request);
        return wxPayUnifiedOrderV3Result.getPayInfo(tradeTypeEnum, request.getAppId(), request.getMchId(), this.getConfig().getPrivateKey());
    }

    @Override
    public String getPayBaseUrl() {
        if (this.getConfig().isUseSandboxEnv()) {
            return this.getConfig().getPayBaseUrl() + SANDBOX_URL;
        }
        return this.getConfig().getPayBaseUrl();
    }

    @Override
    public WxPayOrderQueryV3Result queryOrderV3(WxPayOrderQueryV3Req req) throws PayException {
        if (StringUtils.isBlank(req.getMchid())) {
            req.setMchid(this.getConfig().getMchId());
        }
        String url = String.format(QUERY_ORDER_TRADE_NO_V3_FORMAT, this.getPayBaseUrl(), req.getOutTradeNo());
        if (Objects.isNull(req.getOutTradeNo())) {
            url = String.format(QUERY_ORDER_TRANSACTION_ID_V3_FORMAT, this.getPayBaseUrl(), req.getTransactionId());
        }
        String query = String.format("?mchid=%s", req.getMchid());
        String response = this.getV3(url + query);
        return GSON.fromJson(response, WxPayOrderQueryV3Result.class);
    }

    @Override
    public void closeOrderV3(WxPayOrderCloseV3Req req) throws PayException {
        if (StringUtils.isBlank(req.getMchid())) {
            req.setMchid(this.getConfig().getMchId());
        }
        String url = String.format(CLOSE_ORDER_V3_FORMAT, this.getPayBaseUrl(), req.getOutTradeNo());
        this.postV3(url, GSON.toJson(req));
    }

    @Override
    public WxPayOrderRefundV3Result refundV3(WxPayOrderRefundV3Req req) throws PayException {
        String url = String.format(REFUND_V3_FORMAT, this.getPayBaseUrl());
        String response = this.postV3(url, GSON.toJson(req));
        return GSON.fromJson(response, WxPayOrderRefundV3Result.class);
    }

    @Override
    public WxPayRefundQueryV3Result refundQueryV3(WxPayRefundQueryV3Req req) throws PayException {
        String url = String.format(REFUND_QUERY_V3_FORMAT, this.getPayBaseUrl(), req.getOutRefundNo());
        String response = this.getV3(url);
        return GSON.fromJson(response, WxPayRefundQueryV3Result.class);
    }
}
