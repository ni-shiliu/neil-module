package com.neil.pay.wx.request;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author nihao
 * @date 2023/7/8
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class WxPayUnifiedOrderV3Req implements Serializable {

    private static final long serialVersionUID = 8454403564205151053L;

    /**
     * 字段名：应用ID
     * 是否必填：是
     * 类型：string[1,32]
     * 描述：
     *  由微信生成的应用ID，全局唯一。请求统一下单接口时请注意APPID的应用属性，例如公众号场景下，需使用应用属性为公众号的APPID
     *  示例值：wxd678efh567hg6787
     */
    @SerializedName(value = "appid")
    protected String appId;

    /**
     * 字段名：直连商户号
     * 是否必填：是
     * 类型：string[1,32]
     * 描述：
     *  直连商户的商户号，由微信支付生成并下发。
     *  示例值：1230000109
     */
    @SerializedName(value = "mchid")
    protected String mchId;

    /**
     * 字段名：商品描述
     * 是否必填：是
     * 类型：string[1,127]
     * 描述：
     *  商品描述
     *  示例值：Image形象店-深圳腾大-QQ公仔
     */
    @SerializedName(value = "description")
    protected String description;

    /**
     * 字段名：商户订单号
     * 是否必填：是
     * 类型：string[6,32]
     * 描述：
     *  商户系统内部订单号，只能是数字、大小写字母_-*且在同一个商户号下唯一
     *  示例值：1217752501201407033233368018
     */
    @SerializedName(value = "out_trade_no")
    protected String outTradeNo;

    /**
     * 字段名：交易结束时间
     * 是否必填：是
     * 类型：string[1,64]
     * 描述：
     *  订单失效时间，遵循rfc3339标准格式，格式为YYYY-MM-DDTHH:mm:ss+TIMEZONE，YYYY-MM-DD表示年月日，T出现在字符串中，表示time元素的开头，HH:mm:ss表示时分秒，TIMEZONE表示时区（+08:00表示东八区时间，领先UTC 8小时，即北京时间）。例如：2015-05-20T13:29:35+08:00表示，北京时间2015年5月20日 13点29分35秒。
     *  示例值：2018-06-08T10:34:56+08:00
     */
    @SerializedName(value = "time_expire")
    protected String timeExpire;

    /**
     * 字段名：附加数据
     * 是否必填：否
     * 类型：string[1,128]
     * 描述：
     *  附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用
     *  示例值：自定义数据
     */
    @SerializedName(value = "attach")
    protected String attach;

    /**
     * 字段名：通知地址
     * 是否必填：是
     * 类型：string[1,256]
     * 描述：
     *  通知URL必须为直接可访问的URL，不允许携带查询串，要求必须为https地址。
     *  格式：URL
     *  示例值：https://www.weixin.qq.com/wxpay/pay.php
     */
    @SerializedName(value = "notify_url")
    private String notifyUrl;

    /**
     * 字段名：订单优惠标记
     * 是否必填：否
     * 类型：string[1,256]
     * 描述：
     *  订单优惠标记
     *  示例值：WXG
     */
    @SerializedName(value = "goods_tag")
    private String goodsTag;

    /**
     * 字段名：电子发票入口开放标识
     * 是否必填：否
     * 类型：boolean
     * 描述：传入true时，支付成功消息和支付详情页将出现开票入口。需要在微信支付商户平台或微信公众平台开通电子发票功能，传此字段才可生效。
     */
    @SerializedName(value = "support_fapiao")
    private Boolean supportFapiao;

    /**
     * 字段名：订单金额
     * 是否必填：是
     * 类型：object
     * 描述：
     *  订单金额信息
     */
    @SerializedName(value = "amount")
    private Amount amount;

    /**
     * 字段名：支付者
     * 是否必填：是
     * 类型：object
     * 描述：
     *  支付者信息
     */
    @SerializedName(value = "payer")
    private Payer payer;

    /**
     * 字段名：优惠功能
     * 是否必填：否
     * 类型：object
     * 描述：
     *  优惠功能
     */
    @SerializedName(value = "detail")
    private Discount detail;

    /**
     * 字段名：场景信息
     * 是否必填：否
     * 类型：object
     * 描述：
     *  支付场景描述
     */
    @SerializedName(value = "scene_info")
    private SceneInfo sceneInfo;

    /**
     * 字段名：结算信息
     * 是否必填：否
     * 类型：Object
     * 描述：结算信息
     */
    @SerializedName(value = "settle_info")
    private SettleInfo settleInfo;

    @Data
    @NoArgsConstructor
    public static class Amount implements Serializable {

        private static final long serialVersionUID = -2181151189927385261L;

        /**
         * 字段名：总金额
         * 是否必填：是
         * 类型：int
         * 描述：
         *  订单总金额，单位为分。
         *  示例值：100
         */
        @SerializedName(value = "total")
        private Integer total;

        /**
         * 字段名：币类型
         * 是否必填：否
         * 类型：string[1,16]
         * 描述：
         *  CNY：人民币，境内商户号仅支持人民币。
         *  示例值：CNY
         */
        @SerializedName(value = "currency")
        private String currency;
    }

    @Data
    @NoArgsConstructor
    public static class Payer implements Serializable {
        private static final long serialVersionUID = -8108165926956442774L;

        /**
         * 字段名：用户标识
         * 是否必填：是
         * 类型：string[1,128]
         * 描述：
         *  用户在直连商户appid下的唯一标识。
         *  示例值：oUpF8uMuAJO_M2pxb1Q9zNjWeS6o
         */
        @SerializedName(value = "openid")
        private String openId;
    }

    @Data
    @NoArgsConstructor
    public static class Discount implements Serializable {
        private static final long serialVersionUID = -395497624561106490L;

        /**
         * 字段名：订单原价
         * 是否必填：否
         * 类型：int
         * 描述：
         *  1、商户侧一张小票订单可能被分多次支付，订单原价用于记录整张小票的交易金额。
         *  2、当订单原价与支付金额不相等，则不享受优惠。
         *  3、该字段主要用于防止同一张小票分多次支付，以享受多次优惠的情况，正常支付订单不必上传此参数。
         *  示例值：608800
         */
        @SerializedName(value = "cost_price")
        private Integer costPrice;

        /**
         * 字段名：商品小票ID
         * 是否必填：否
         * 类型：string[1,32]
         * 描述：
         *  商品小票ID
         *  示例值：微信123
         */
        @SerializedName(value = "invoice_id")
        private String invoiceId;

        /**
         * 字段名：单品列表
         * 是否必填：否
         * 类型：array
         * 描述：
         *  单品列表信息
         *  条目个数限制：【1，6000】
         */
        @SerializedName(value = "goods_detail")
        private List<GoodsDetail> goodsDetails;
    }

    @Data
    @NoArgsConstructor
    public static class GoodsDetail implements Serializable {
        private static final long serialVersionUID = 6119341973137076831L;

        /**
         * 字段名：商户侧商品编码
         * 是否必填：是
         * 类型：string[1,32]
         * 描述：
         *  由半角的大小写字母、数字、中划线、下划线中的一种或几种组成。
         *  示例值：商品编码
         */
        @SerializedName(value = "merchant_goods_id")
        private String merchantGoodsId;

        /**
         * 字段名：微信侧商品编码
         * 是否必填：否
         * 类型：string[1,32]
         * 描述：
         *  微信支付定义的统一商品编号（没有可不传）
         *  示例值：1001
         */
        @SerializedName(value = "wechatpay_goods_id")
        private String wechatPayGoodsId;

        /**
         * 字段名：商品名称
         * 是否必填：否
         * 类型：string[1,256]
         * 描述：
         *  商品的实际名称
         *  示例值：iPhoneX 256G
         */
        @SerializedName(value = "goods_name")
        private String goodsName;

        /**
         * 字段名：商品数量
         * 是否必填：是
         * 类型：int
         * 描述：
         *  用户购买的数量
         *  示例值：1
         */
        @SerializedName(value = "quantity")
        private Integer quantity;

        /**
         * 字段名：商品单价
         * 是否必填：是
         * 类型：int
         * 描述：
         *  商品单价，单位为分
         *  示例值：828800
         */
        @SerializedName(value = "unit_price")
        private Integer unitPrice;
    }

    @Data
    @NoArgsConstructor
    public static class SceneInfo implements Serializable {
        private static final long serialVersionUID = 7604693592144736632L;

        /**
         * 字段名：用户终端IP
         * 是否必填：是
         * 类型：string[1,45]
         * 描述：
         *  用户的客户端IP，支持IPv4和IPv6两种格式的IP地址。
         *  示例值：14.23.150.211
         */
        @SerializedName(value = "payer_client_ip")
        private String payerClientIp;

        /**
         * 字段名：商户端设备号
         * 是否必填：否
         * 类型：string[1,32]
         * 描述：
         *  商户端设备号（门店号或收银设备ID）。
         *  示例值：013467007045764
         */
        @SerializedName(value = "device_id")
        private String deviceId;

        /**
         * 字段名：商户门店信息
         * 是否必填：否
         * 类型：object
         * 描述：
         *  商户门店信息
         */
        @SerializedName(value = "store_info")
        private StoreInfo storeInfo;

        /**
         * 字段名：H5场景信息
         * 是否必填：否(H5支付必填)
         * 类型：object
         * 描述：
         *  H5场景信息
         */
        @SerializedName(value = "h5_info")
        private H5Info h5Info;
    }

    @Data
    @NoArgsConstructor
    public static class StoreInfo implements Serializable {
        private static final long serialVersionUID = -8324219161155066788L;

        /**
         * 字段名：门店编号
         * 是否必填：是
         * 类型：string[1,32]
         * 描述：
         *  商户侧门店编号
         *  示例值：0001
         */
        @SerializedName(value = "id")
        private String id;

        /**
         * 字段名：门店名称
         * 是否必填：否
         * 类型：string[1,256]
         * 描述：
         *  商户侧门店名称
         *  示例值：腾讯大厦分店
         */
        @SerializedName(value = "name")
        private String name;

        /**
         * 字段名：地区编码
         * 是否必填：否
         * 类型：string[1,32]
         * 描述： 地区编码, <a href="https://pay.weixin.qq.com/wiki/doc/apiv3/wxpay/ecommerce/applyments/chapter4_1.shtml">详细请见省市区编号对照表</a>。
         * 示例值：440305
         */
        @SerializedName(value = "area_code")
        private String areaCode;

        /**
         * 字段名：详细地址
         * 是否必填：是
         * 类型：string[1,512]
         * 描述：
         *  详细的商户门店地址
         *  示例值：广东省深圳市南山区科技中一道10000号
         */
        @SerializedName(value = "address")
        private String address;
    }

    @Data
    @NoArgsConstructor
    public static class H5Info implements Serializable {
        private static final long serialVersionUID = -5272364782239880067L;

        /**
         * 字段名：场景类型
         * 是否必填：是
         * 类型：string[1,32]
         * 描述：
         *  场景类型
         *  示例值：iOS, Android, Wap
         */
        @SerializedName(value = "type")
        private String type;

        /**
         * 字段名：应用名称
         * 是否必填：否
         * 类型：string[1,64]
         * 描述：
         *  应用名称
         *  示例值：王者荣耀
         */
        @SerializedName(value = "app_name")
        private String appName;

        /**
         * 字段名：网站URL
         * 是否必填：否
         * 类型：string[1,128]
         * 描述：
         *  网站URL
         *  示例值：https://pay.qq.com
         */
        @SerializedName(value = "app_url")
        private String appUrl;

        /**
         * 字段名：iOS平台BundleID
         * 是否必填：否
         * 类型：string[1,128]
         * 描述：
         *  iOS平台BundleID
         *  示例值：com.tencent.wzryiOS
         */
        @SerializedName(value = "bundle_id")
        private String bundleId;

        /**
         * 字段名：Android平台PackageName
         * 是否必填：否
         * 类型：string[1,128]
         * 描述：
         *  Android平台PackageName
         *  示例值：com.tencent.tmgp.sgame
         */
        @SerializedName(value = "package_name")
        private String packageName;
    }

    @Data
    @NoArgsConstructor
    public static class SettleInfo implements Serializable {
        private static final long serialVersionUID = 6245475102237519899L;

        /**
         * 字段名：是否指定分账
         * 是否必填：否
         * 类型：boolean
         * 描述：
         *  是否指定分账
         *  示例值：false
         */
        @SerializedName(value = "profit_sharing")
        private Boolean profitSharing;
    }
}
