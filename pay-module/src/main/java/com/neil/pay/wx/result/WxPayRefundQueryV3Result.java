package com.neil.pay.wx.result;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author nihao
 * @date 2023/7/17
 */
@Data
public class WxPayRefundQueryV3Result implements Serializable {

    private static final long serialVersionUID = 5407484900326492958L;

    @ApiModelProperty("微信支付退款号")
    @SerializedName(value = "refund_id")
    private String refundId;

    @ApiModelProperty("商户退款单号")
    @SerializedName(value = "out_refund_no")
    private String outRefundNo;

    @ApiModelProperty("微信支付订单号")
    @SerializedName(value = "transaction_id")
    private String transactionId;

    @ApiModelProperty("商户订单号")
    @SerializedName(value = "out_trade_no")
    private String outTradeNo;

    @ApiModelProperty("退款渠道")
    @SerializedName(value = "channel")
    private String channel;

    @ApiModelProperty("退款入账账户")
    @SerializedName(value = "user_received_account")
    private String userReceivedAccount;

    @ApiModelProperty("退款成功时间")
    @SerializedName(value = "success_time")
    private String successTime;

    @ApiModelProperty("退款创建时间")
    @SerializedName(value = "create_time")
    private String createTime;

    @ApiModelProperty("退款状态")
    @SerializedName(value = "status")
    private String status;

    @ApiModelProperty("资金账户")
    @SerializedName(value = "funds_account")
    private String fundsAccount;

    @ApiModelProperty("金额信息")
    @SerializedName(value = "amount")
    private Amount amount;

    @ApiModelProperty("优惠退款信息")
    @SerializedName(value = "promotion_detail")
    public List<PromotionDetail> promotionDetail;

    @Data
    @NoArgsConstructor
    public static class Amount implements Serializable {

        private static final long serialVersionUID = -8140119743970352585L;
        @ApiModelProperty("订单金额")
        @SerializedName(value = "total")
        private Integer total;

        @ApiModelProperty("退款金额")
        @SerializedName(value = "refund")
        private Integer refund;

        @ApiModelProperty("退款出资账户及金额")
        @SerializedName(value = "from")
        private List<From> from;

        @ApiModelProperty("用户支付金额")
        @SerializedName(value = "payer_total")
        private Integer payerTotal;

        @ApiModelProperty("用户退款金额")
        @SerializedName(value = "payer_refund")
        private Integer payerRefund;

        @ApiModelProperty("应结退款金额")
        @SerializedName(value = "settlement_refund")
        private Integer settlementRefund;

        @ApiModelProperty("应结订单金额")
        @SerializedName(value = "settlement_total")
        private Integer settlementTotal;

        @ApiModelProperty("优惠退款金额")
        @SerializedName(value = "discount_refund")
        private Integer discountRefund;

        @ApiModelProperty("退款币种")
        @SerializedName(value = "currency")
        private String currency;
    }

    @Data
    @NoArgsConstructor
    public static class PromotionDetail implements Serializable {
        private static final long serialVersionUID = 1L;

        @ApiModelProperty("券ID")
        @SerializedName(value = "promotion_id")
        private String promotionId;

        @ApiModelProperty("优惠范围")
        @SerializedName(value = "scope")
        private String scope;

        @ApiModelProperty("优惠类型")
        @SerializedName(value = "type")
        private String type;

        @ApiModelProperty("优惠券面额")
        @SerializedName(value = "amount")
        private Integer amount;

        @ApiModelProperty("优惠退款金额")
        @SerializedName(value = "refund_amount")
        private Integer refundAmount;

        @ApiModelProperty("商品列表")
        @SerializedName(value = "goods_detail")
        private List<GoodsDetail> goodsDetail;
    }

    @Data
    @NoArgsConstructor
    public static class From implements Serializable {
        private static final long serialVersionUID = 1L;

        @ApiModelProperty("出资账户类型")
        @SerializedName(value = "account")
        private String account;

        @ApiModelProperty("出资金额")
        @SerializedName(value = "amount")
        private Integer amount;
    }

    @Data
    @NoArgsConstructor
    public static class GoodsDetail implements Serializable {
        private static final long serialVersionUID = 1L;

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
}
