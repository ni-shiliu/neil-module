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
public class OriginNotifyResponse implements Serializable {
    private static final long serialVersionUID = 2874249828383352988L;

    @ApiModelProperty("通知ID")
    @SerializedName(value = "id")
    private String id;

    @ApiModelProperty("通知创建时间")
    @SerializedName(value = "create_time")
    private String createTime;

    @ApiModelProperty("通知类型")
    @SerializedName(value = "event_type")
    private String eventType;

    @ApiModelProperty("通知简要说明")
    @SerializedName(value = "summary")
    private String summary;

    @ApiModelProperty("通知数据类型")
    @SerializedName(value = "resource_type")
    private String resourceType;

    @ApiModelProperty("通知数据")
    @SerializedName(value = "resource")
    private Resource resource;

    @Data
    @NoArgsConstructor
    public static class Resource implements Serializable {

        private static final long serialVersionUID = -9137229547261279091L;

        @ApiModelProperty("加密算法类型")
        @SerializedName(value = "algorithm")
        private String algorithm;

        @ApiModelProperty("原始类型")
        @SerializedName(value = "original_type")
        private String originalType;

        @ApiModelProperty("数据密文")
        @SerializedName(value = "ciphertext")
        private String ciphertext;

        @ApiModelProperty("附加数据")
        @SerializedName(value = "associated_data")
        private String associatedData;

        @ApiModelProperty("随机串")
        @SerializedName(value = "nonce")
        private String nonce;
    }

}
