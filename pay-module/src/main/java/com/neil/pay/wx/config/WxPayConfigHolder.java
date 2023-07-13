package com.neil.pay.wx.config;

/**
 * 微信支付配置持有者
 * 通过持有者，获取对应的微信支付配置
 * @author nihao
 * @date 2023/7/11
 */
public class WxPayConfigHolder {

    private static final ThreadLocal<String> WX_PAY_CONFIG_HOLDER = ThreadLocal.withInitial(() -> "default");

    public static void set(final String mchId) {
        WX_PAY_CONFIG_HOLDER.set(mchId);
    }

    public static String get() {
        return WX_PAY_CONFIG_HOLDER.get();
    }

    public static void remove() {
        WX_PAY_CONFIG_HOLDER.remove();
    }
}
