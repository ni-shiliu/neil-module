package com.neil.pay.wx.notify;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author nihao
 * @date 2023/7/18
 */
@Data
public class WxPayRefundNotifyV3Result implements Serializable, WxPayBaseNotifyV3Result<WxPayRefundNotifyV3Result.DecryptNotifyResult> {

    private static final long serialVersionUID = -1023547538995456260L;

    @ApiModelProperty("源数据")
    private OriginNotifyResponse rawData;

    @ApiModelProperty("解密后的数据")
    private DecryptNotifyResult result;


    @Data
    @NoArgsConstructor
    public static class DecryptNotifyResult implements Serializable {

        private static final long serialVersionUID = 8553924727668602519L;

        @ApiModelProperty("直连商户号")
        @SerializedName(value = "mchid")
        private String mchid;

        @ApiModelProperty("商户订单号")
        @SerializedName(value = "out_trade_no")
        private String outTradeNo;

        @ApiModelProperty("微信支付订单号")
        @SerializedName(value = "transaction_id")
        private String transactionId;

        @ApiModelProperty("商户退款单号")
        @SerializedName(value = "out_refund_no")
        private String outRefundNo;

        @ApiModelProperty("微信支付退款号")
        @SerializedName(value = "refund_id")
        private String refundId;

        @ApiModelProperty("退款状态")
        @SerializedName(value = "refund_status")
        private String refundStatus;

        @ApiModelProperty("退款成功时间")
        @SerializedName(value = "success_time")
        private String successTime;

        @ApiModelProperty("退款入账账户")
        @SerializedName(value = "user_received_account")
        private String userReceivedAccount;

        @ApiModelProperty("金额信息")
        @SerializedName(value = "amount")
        private Amount amount;
    }

    @Data
    @NoArgsConstructor
    public static class Amount implements Serializable {

        private static final long serialVersionUID = -6029761783735282245L;

        @ApiModelProperty("订单金额")
        @SerializedName(value = "total")
        private Integer total;

        @ApiModelProperty("退款金额")
        @SerializedName(value = "refund")
        private Integer refund;

        @ApiModelProperty("用户支付金额")
        @SerializedName(value = "payer_total")
        private Integer payerTotal;

        @ApiModelProperty("用户退款金额")
        @SerializedName(value = "payer_refund")
        private Integer payerRefund;
    }

}
