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
public class WxPayRefundQueryV3Req implements Serializable {

    private static final long serialVersionUID = -2450938698289589962L;

    @ApiModelProperty("商户退款单号")
    @SerializedName(value = "out_refund_no")
    private String outRefundNo;
}
