package com.neil.pay.wx.notify;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author nihao
 * @date 2023/7/18
 */
@Data
@Builder
public class SignatureHeader implements Serializable {
    private static final long serialVersionUID = -3179394795426938136L;

    @ApiModelProperty("时间戳")
    private String timeStamp;

    @ApiModelProperty("随机串")
    private String nonce;

    @ApiModelProperty("已签名字符串")
    private String signature;

    @ApiModelProperty("证书序列号")
    private String serial;

}
