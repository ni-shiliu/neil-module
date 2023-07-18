package com.neil.pay.wx.notify;

/**
 * @author nihao
 * @date 2023/7/18
 */
public interface WxPayBaseNotifyV3Result<T> {

    /**
     * 设置原始数据
     * @param rawData
     */
    void setRawData(OriginNotifyResponse rawData);

    /**
     * 解密后的数据
     * @param data
     */
    void setResult(T data);
}
