package com.neil.pay.wx.request;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author nihao
 * @date 2023/7/17
 */
@Data
@Accessors(chain = true)
public class WxPayOrderQueryV3Req implements Serializable {
    private static final long serialVersionUID = -5814250498902732504L;

    @ApiModelProperty("直连商户号")
    @SerializedName(value = "mchid")
    private String mchid;

    @ApiModelProperty("微信支付订单号")
    @SerializedName(value = "transaction_id")
    private String transactionId;

    @ApiModelProperty("商户订单号")
    @SerializedName(value = "out_trade_no")
    private String outTradeNo;
}
