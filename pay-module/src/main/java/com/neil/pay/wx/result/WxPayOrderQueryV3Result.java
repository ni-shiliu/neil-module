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
public class WxPayOrderQueryV3Result implements Serializable {
    private static final long serialVersionUID = 8067359923632716105L;

    @ApiModelProperty("应用ID")
    @SerializedName(value = "appid")
    private String appid;

    @ApiModelProperty("直连商户号")
    @SerializedName(value = "mchid")
    private String mchid;

    @ApiModelProperty("商户订单号")
    @SerializedName(value = "out_trade_no")
    private String outTradeNo;

    @ApiModelProperty("微信支付订单号")
    @SerializedName(value = "transaction_id")
    private String transactionId;

    @ApiModelProperty("交易类型")
    @SerializedName(value = "trade_type")
    private String tradeType;

    @ApiModelProperty("交易状态")
    @SerializedName(value = "trade_state")
    private String tradeState;

    @ApiModelProperty("交易状态描述")
    @SerializedName(value = "trade_state_desc")
    private String tradeStateDesc;

    @ApiModelProperty("付款银行")
    @SerializedName(value = "bank_type")
    private String bankType;

    @ApiModelProperty("附加数据")
    @SerializedName(value = "attach")
    private String attach;

    @ApiModelProperty("支付完成时间")
    @SerializedName(value = "success_time")
    private String successTime;

    @ApiModelProperty("支付者")
    private Payer payer;

    @ApiModelProperty("订单金额")
    @SerializedName(value = "amount")
    private Amount amount;

    @ApiModelProperty("场景信息")
    @SerializedName(value = "scene_info")
    private SceneInfo sceneInfo;

    @ApiModelProperty("优惠功能")
    @SerializedName(value = "promotion_detail")
    private List<PromotionDetail> promotionDetails;

    @Data
    @NoArgsConstructor
    public static class Payer implements Serializable {
        private static final long serialVersionUID = 1L;

        @ApiModelProperty("用户标识")
        @SerializedName(value = "openid")
        private String openid;
    }

    @Data
    @NoArgsConstructor
    public static class Amount implements Serializable {
        private static final long serialVersionUID = 1L;

        @ApiModelProperty("总金额")
        @SerializedName(value = "total")
        private Integer total;

        @ApiModelProperty("用户支付金额")
        @SerializedName(value = "payer_total")
        private Integer payerTotal;

        @ApiModelProperty("货币类型")
        @SerializedName(value = "currency")
        private String currency;

        @ApiModelProperty("用户支付币种")
        @SerializedName(value = "payer_currency")
        private String payerCurrency;
    }

    @Data
    @NoArgsConstructor
    public static class SceneInfo implements Serializable {
        private static final long serialVersionUID = 1L;

        @ApiModelProperty("商户端设备号")
        @SerializedName(value = "device_id")
        private String deviceId;
    }

    @Data
    @NoArgsConstructor
    public static class PromotionDetail implements Serializable {

        @ApiModelProperty("券ID")
        @SerializedName(value = "coupon_id")
        private String couponId;

        @ApiModelProperty("优惠名称")
        @SerializedName(value = "name")
        private String name;

        @ApiModelProperty("优惠范围")
        @SerializedName(value = "scope")
        private String scope;

        @ApiModelProperty("优惠类型")
        @SerializedName(value = "type")
        private String type;

        @ApiModelProperty("优惠券面额")
        @SerializedName(value = "amount")
        private Integer amount;

        @ApiModelProperty("活动ID")
        @SerializedName(value = "stock_id")
        private String stockId;

        @ApiModelProperty("微信出资")
        @SerializedName(value = "wechatpay_contribute")
        private Integer wechatpayContribute;

        @ApiModelProperty("商户出资")
        @SerializedName(value = "merchant_contribute")
        private Integer merchantContribute;

        @ApiModelProperty("其他出资")
        @SerializedName(value = "other_contribute")
        private Integer otherContribute;

        @ApiModelProperty("优惠币种")
        @SerializedName(value = "currency")
        private String currency;

        @ApiModelProperty("单品列表")
        @SerializedName(value = "goods_detail")
        private List<GoodsDetail> goodsDetails;
    }

    @Data
    @NoArgsConstructor
    public static class GoodsDetail implements Serializable {
        private static final long serialVersionUID = 1L;

        @ApiModelProperty("商品编码")
        @SerializedName(value = "goods_id")
        private String goodsId;

        @ApiModelProperty("商品数量")
        @SerializedName(value = "quantity")
        private Integer quantity;

        @ApiModelProperty("商品单价")
        @SerializedName(value = "unit_price")
        private Integer unitPrice;

        @ApiModelProperty("商品优惠金额")
        @SerializedName(value = "discount_amount")
        private Integer discountAmount;

        @ApiModelProperty("商品备注")
        @SerializedName(value = "goods_remark")
        private String goodsRemark;
    }
}
