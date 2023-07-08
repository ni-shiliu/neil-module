package com.neil.module.config.wx;

import com.neil.module.properties.wx.WxProperties;
import com.neil.pay.service.WxPayService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author nihao
 * @date 2023/7/8
 */
@Configuration
@EnableConfigurationProperties(WxProperties.class)
@ConditionalOnClass(WxPayService.class)
@ConditionalOnProperty(prefix = "wx.pay", value = "enabled", matchIfMissing = true)
public class WxConfiguration {


}
