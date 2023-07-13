package com.neil.pay.wx.result;

import com.google.gson.annotations.SerializedName;
import com.neil.pay.wx.enums.WxTradeTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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

    public <T> T getPayInfo(WxTradeTypeEnum tradeTypeEnum, String appid, String mchId, PrivateKey privateKey) {
        switch (tradeTypeEnum) {
            case H5:
                return (T) this.h5Url;
            default:
                throw new RuntimeException("不支持的支付类型");
        }
    }
}
