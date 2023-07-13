package com.neil.module.config.wx;

import com.google.common.collect.Maps;
import com.neil.module.properties.wx.WxProperties;
import com.neil.module.properties.wx.WxPropertiesInfo;
import com.neil.pay.wx.config.WxPayConfig;
import com.neil.pay.wx.service.WxPayService;
import com.neil.pay.wx.service.impl.WxPayServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author nihao
 * @date 2023/7/8
 */
@Configuration
@EnableConfigurationProperties(WxProperties.class)
@ConditionalOnClass(WxPayService.class)
@ConditionalOnProperty(prefix = "wx.pay", value = "enabled", matchIfMissing = true)
@RequiredArgsConstructor
public class WxConfiguration {

    private final WxProperties wxProperties;

    @Bean
    @ConditionalOnMissingBean(WxPayService.class)
    public WxPayService wxPayService() {
        final WxPayService wxPayService = new WxPayServiceImpl();
        Map<String, WxPayConfig> wxPayConfigMap = Maps.newHashMap();

        for (Map.Entry<String, WxPropertiesInfo> wxConfigEntry : wxProperties.getConfigMap().entrySet()) {
            WxPropertiesInfo wxConfig = wxConfigEntry.getValue();
            WxPayConfig payConfig = new WxPayConfig();
            BeanUtils.copyProperties(wxConfig, payConfig);
            wxPayConfigMap.put(wxConfigEntry.getKey(), payConfig);
        }

        wxPayService.setMultiConfig(wxPayConfigMap);
        return wxPayService;
    }
}
