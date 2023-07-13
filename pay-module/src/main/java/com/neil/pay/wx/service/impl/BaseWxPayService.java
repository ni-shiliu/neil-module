package com.neil.pay.wx.service.impl;

import com.google.common.collect.Maps;
import com.neil.pay.exception.PayException;
import com.neil.pay.wx.config.WxPayConfig;
import com.neil.pay.wx.config.WxPayConfigHolder;
import com.neil.pay.wx.enums.WxTradeTypeEnum;
import com.neil.pay.wx.request.WxPayUnifiedOrderV3Request;
import com.neil.pay.wx.result.WxPayUnifiedOrderV3Result;
import com.neil.pay.wx.service.WxPayService;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

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
    public WxPayUnifiedOrderV3Result unifiedOrderV3(WxTradeTypeEnum tradeType, WxPayUnifiedOrderV3Request request) throws PayException {
        String url = this.getPayBaseUrl() + tradeType.getPartnerUrl();
        String response = this.postV3(url, GSON.toJson(request));
        return GSON.fromJson(response, WxPayUnifiedOrderV3Result.class);
    }

    @Override
    public <T> T createOrderV3(WxTradeTypeEnum tradeTypeEnum, WxPayUnifiedOrderV3Request request) throws PayException {
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

}
