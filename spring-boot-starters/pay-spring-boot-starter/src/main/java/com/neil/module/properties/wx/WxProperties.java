package com.neil.module.properties.wx;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * @author nihao
 * @date 2023/7/8
 */
@Data
@ConfigurationProperties(prefix = "wx.pay")
public class WxProperties {

    private Map<String, WxPropertiesInfo> configMap;

}
