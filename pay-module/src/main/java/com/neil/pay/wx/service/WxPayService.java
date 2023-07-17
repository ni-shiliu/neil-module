package com.neil.pay.wx.service;

import com.neil.pay.exception.PayException;
import com.neil.pay.wx.config.WxPayConfig;
import com.neil.pay.wx.enums.WxTradeTypeEnum;
import com.neil.pay.wx.request.WxPayOrderCloseV3Req;
import com.neil.pay.wx.request.WxPayOrderQueryV3Req;
import com.neil.pay.wx.request.WxPayUnifiedOrderV3Req;
import com.neil.pay.wx.result.WxPayOrderQueryV3Result;
import com.neil.pay.wx.result.WxPayUnifiedOrderV3Result;
import org.apache.http.client.methods.HttpGet;

import java.util.Map;

/**
 * @author nihao
 * @date 2023/7/8
 */
public interface WxPayService {

    /**
     * 切换商户,以便获取多商户时的配置信息
     * @param mchId
     * @return
     */
    boolean switchover(String mchId);

    void setMultiConfig(Map<String, WxPayConfig> configMap);

    void setMultiConfig(Map<String, WxPayConfig> configMap, String defaultMchId);

    /**
     * 获取配置信息
     * @return
     */
    WxPayConfig getConfig();

    /**
     * 调用微信统一下单api
     * @param tradeTypeEnum
     * @param request
     * @return
     */
    WxPayUnifiedOrderV3Result unifiedOrderV3(WxTradeTypeEnum tradeTypeEnum, WxPayUnifiedOrderV3Req request) throws PayException;

    /**
     * 调用微信统一下单api，并返回对应支付所需参数
     * @param tradeTypeEnum
     * @param request
     * @return
     * @param <T>
     */
    <T> T createOrderV3(WxTradeTypeEnum tradeTypeEnum, WxPayUnifiedOrderV3Req request) throws PayException;

    String getPayBaseUrl();

    String postV3(String url, String request) throws PayException;

    WxPayOrderQueryV3Result queryOrderV3(WxPayOrderQueryV3Req req) throws PayException;

    String getV3(String url) throws PayException;

    String requestV3(String url, HttpGet httpGet) throws PayException;

    void closeOrderV3(WxPayOrderCloseV3Req req) throws PayException;
}
