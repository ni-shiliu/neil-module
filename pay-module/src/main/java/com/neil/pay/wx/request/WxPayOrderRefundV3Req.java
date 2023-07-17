package com.neil.pay.wx.request;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author nihao
 * @date 2023/7/17
 */
@Data
@Accessors(chain = true)
public class WxPayOrderRefundV3Req implements Serializable {

    private static final long serialVersionUID = -2052108198456817896L;

    @ApiModelProperty("微信支付订单号")
    @SerializedName(value = "transaction_id")
    private String transactionId;

    @ApiModelProperty("商户订单号")
    @SerializedName(value = "out_trade_no")
    private String outTradeNo;

    @ApiModelProperty("商户退款单号")
    @SerializedName(value = "out_refund_no")
    private String outRefundNo;

    @ApiModelProperty("退款原因")
    @SerializedName(value = "reason")
    private String reason;

    @ApiModelProperty("退款结果回调url")
    @SerializedName(value = "notify_url")
    private String notifyUrl;

    @ApiModelProperty("订单金额")
    @SerializedName(value = "amount")
    private Amount amount;

    @ApiModelProperty("退款商品")
    @SerializedName(value = "goods_detail")
    private List<GoodsDetail> goodsDetails;

    @Data
    @NoArgsConstructor
    public static class Amount implements Serializable {

        private static final long serialVersionUID = -3621494424437671821L;

        @ApiModelProperty("退款金额")
        @SerializedName(value = "refund")
        private Integer refund;

        @ApiModelProperty("原订单金额")
        @SerializedName(value = "total")
        private Integer total;

        @ApiModelProperty("币类型")
        @SerializedName(value = "currency")
        private String currency;
    }

    @Data
    @NoArgsConstructor
    public static class GoodsDetail implements Serializable {

        private static final long serialVersionUID = -6358597510227657311L;

        @ApiModelProperty("商户侧商品编码")
        @SerializedName(value = "merchant_goods_id")
        private String merchantGoodsId;

        @ApiModelProperty("微信侧商品编码")
        @SerializedName(value = "wechatpay_goods_id")
        private String wechatpayGoodsId;

        @ApiModelProperty("商品名称")
        @SerializedName(value = "goods_name")
        private String goodsName;

        @ApiModelProperty("商品单价")
        @SerializedName(value = "unit_price")
        private Integer unitPrice;

        @ApiModelProperty("商品退款金额")
        @SerializedName(value = "refund_amount")
        private Integer refundAmount;

        @ApiModelProperty("商品退货数量")
        @SerializedName(value = "refund_quantity")
        private Integer refundQuantity;
    }

    @ApiModelProperty("子商户的商户号")
    @SerializedName(value = "sub_mchid")
    private String subMchid;

}
