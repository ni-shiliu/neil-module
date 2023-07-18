package com.neil.pay.wx.service;

import com.neil.pay.exception.PayException;
import com.neil.pay.wx.config.WxPayConfig;
import com.neil.pay.wx.enums.WxTradeTypeEnum;
import com.neil.pay.wx.notify.SignatureHeader;
import com.neil.pay.wx.notify.WxPayBaseNotifyV3Result;
import com.neil.pay.wx.notify.WxPayNotifyV3Result;
import com.neil.pay.wx.notify.WxPayRefundNotifyV3Result;
import com.neil.pay.wx.request.*;
import com.neil.pay.wx.result.WxPayOrderQueryV3Result;
import com.neil.pay.wx.result.WxPayOrderRefundV3Result;
import com.neil.pay.wx.result.WxPayRefundQueryV3Result;
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
     *
     * @param mchId
     * @return
     */
    boolean switchover(String mchId);

    /**
     * 配置config
     *
     * @param configMap
     */
    void setMultiConfig(Map<String, WxPayConfig> configMap);

    /**
     * 配置config
     *
     * @param configMap
     * @param defaultMchId
     */
    void setMultiConfig(Map<String, WxPayConfig> configMap, String defaultMchId);

    /**
     * 获取配置信息
     *
     * @return
     */
    WxPayConfig getConfig();

    /**
     * 调用微信统一下单api
     *
     * @param tradeTypeEnum
     * @param request
     * @return
     */
    WxPayUnifiedOrderV3Result unifiedOrderV3(WxTradeTypeEnum tradeTypeEnum, WxPayUnifiedOrderV3Req request) throws PayException;

    /**
     * v3预下单
     *
     * @param tradeTypeEnum
     * @param request
     * @return
     * @param <T>
     */
    <T> T createOrderV3(WxTradeTypeEnum tradeTypeEnum, WxPayUnifiedOrderV3Req request) throws PayException;

    String getPayBaseUrl();

    String postV3(String url, String request) throws PayException;

    /**
     * v3订单查询
     *
     * @param req
     * @return
     * @throws PayException
     */
    WxPayOrderQueryV3Result queryOrderV3(WxPayOrderQueryV3Req req) throws PayException;

    String getV3(String url) throws PayException;

    String requestV3(String url, HttpGet httpGet) throws PayException;

    /**
     * v3关闭订单
     *
     * @param req
     * @throws PayException
     */
    void closeOrderV3(WxPayOrderCloseV3Req req) throws PayException;

    /**
     * v3退款
     *
     * @param req
     * @return
     * @throws PayException
     */
    WxPayOrderRefundV3Result refundV3(WxPayOrderRefundV3Req req) throws PayException;

    /**
     * v3退款查询
     *
     * @param req
     * @return
     * @throws PayException
     */
    WxPayRefundQueryV3Result refundQueryV3(WxPayRefundQueryV3Req req) throws PayException;

    /**
     * 直连模式-解析支付通知
     * @param notifyData
     * @param header
     * @return
     * @throws PayException
     */
    WxPayNotifyV3Result parseOrderNotifyV3Result(String notifyData, SignatureHeader header) throws PayException;

    /**
     * 直连模式-解析退款通知
     *
     * @param notifyData
     * @param header
     * @return
     */
    WxPayRefundNotifyV3Result parseRefundNotifyV3Result(String notifyData, SignatureHeader header) throws PayException;

    /**
     * 通用解析支付、退款通知，支持直连模式和服务商模式
     *
     * @param notifyData 通知数据
     * @param header 通知头部数据，不传则表示不校验头
     * @param resultType 结果类型
     * @param dataType 结果数据类型
     * @return
     * @param <T>
     * @param <E>
     */
    <T extends WxPayBaseNotifyV3Result<E>, E> T baseParseOrderNotifyV3Result(String notifyData, SignatureHeader header, Class<T> resultType, Class<E> dataType) throws PayException;
}
