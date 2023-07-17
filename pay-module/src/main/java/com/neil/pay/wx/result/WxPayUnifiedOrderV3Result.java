package com.neil.pay.wx.result;

import com.google.gson.annotations.SerializedName;
import com.neil.pay.utils.SignUtil;
import com.neil.pay.wx.enums.WxTradeTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.security.PrivateKey;

/**
 * @author nihao
 * @date 2023/7/11
 */
@Data
public class WxPayUnifiedOrderV3Result implements Serializable {
    private static final long serialVersionUID = -3617041234004924969L;

    @ApiModelProperty("支付跳转链接（H5支付 会返回）")
    @SerializedName("h5_url")
    private String h5Url;

    @ApiModelProperty("预支付交易会话标识（APP支付、JSAPI支付 会返回）")
    @SerializedName("prepay_id")
    private String prepayId;

    @Data
    @Accessors(chain = true)
    public static class JsapiResult implements Serializable {
        private static final long serialVersionUID = 4465376277943307271L;
        private String appId;
        private String timeStamp;
        private String nonceStr;
        private String packageValue;
        private String signType;
        private String paySign;

        private String getSignStr() {
            return String.format("%s\n%s\n%s\n%s\n", appId, timeStamp, nonceStr, packageValue);
        }
    }

    public <T> T getPayInfo(WxTradeTypeEnum tradeTypeEnum, String appid, String mchId, PrivateKey privateKey) {
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String nonceStr = SignUtil.genRandomStr();
        switch (tradeTypeEnum) {
            case H5:
                return (T) this.h5Url;
            case JSAPI:
                JsapiResult jsapiResult = new JsapiResult();
                jsapiResult.setAppId(appid).setTimeStamp(timestamp)
                        .setPackageValue("prepay_id=" + this.prepayId).setNonceStr(nonceStr)
                        //签名类型，默认为RSA，仅支持RSA。
                        .setSignType("RSA").setPaySign(SignUtil.sign(jsapiResult.getSignStr(), privateKey));
                return (T) jsapiResult;
            default:
                throw new RuntimeException("不支持的支付类型");
        }
    }
}
