package com.neil.pay.wx.config;

import cn.hutool.core.util.StrUtil;
import com.neil.pay.exception.PayException;
import com.neil.pay.utils.PemUtil;
import com.neil.pay.wx.WechatPayHttpClientBuilder;
import com.neil.pay.wx.auth.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

/**
 * @author nihao
 * @date 2023/7/11
 */
@Data
@Slf4j
public class WxPayConfig {

    private static final String DEFAULT_PAY_BASE_URL = "https://api.mch.weixin.qq.com";

    @ApiModelProperty("公众号appid")
    private String appId;
    @ApiModelProperty("服务商模式下的子商户公众账号ID")
    private String subAppId;
    @ApiModelProperty("商户号")
    private String mchId;
    @ApiModelProperty("商户密钥")
    private String mchKey;
    @ApiModelProperty("企业支付密钥")
    private String entPayKey;
    @ApiModelProperty("服务商模式下的子商户号")
    private String subMchId;
    @ApiModelProperty("微信支付异步回掉地址，通知url必须为直接可访问的url，不能携带参数")
    private String notifyUrl;
    @ApiModelProperty("p12证书文件的绝对路径或者以classpath:开头的类路径")
    private String keyPath;
    @ApiModelProperty("apiV3 秘钥值")
    private String apiV3Key;
    @ApiModelProperty("apiV3 证书序列号值")
    private String certSerialNo;
    @ApiModelProperty("微信支付分serviceId")
    private String serviceId;
    @ApiModelProperty("微信支付分回调地址")
    private String payScoreNotifyUrl;
    @ApiModelProperty("apiclient_key.pem证书文件类路径")
    private String privateKeyPath;
    @ApiModelProperty("apiclient_cert.pem证书文件类路径")
    private String privateCertPath;
    @ApiModelProperty("微信支付是否使用仿真测试环境")
    private boolean useSandboxEnv = false;
    @ApiModelProperty("微信支付接口请求地址域名部分")
    private String payBaseUrl = DEFAULT_PAY_BASE_URL;

    /**
     * http请求连接超时时间.
     */
    private int httpConnectionTimeout = 5000;

    /**
     * http请求数据读取等待时间.
     */
    private int httpTimeout = 10000;

    private PrivateKey privateKey;
    private Verifier verifier;
    private CloseableHttpClient v3HttpClient;

    public CloseableHttpClient initV3HttpClient() throws PayException {
        validConfig();

        try {
            InputStream keyInputStream = this.loadConfigInputStream(this.getPrivateKeyPath());
            InputStream certInputStream = this.loadConfigInputStream(this.getPrivateCertPath());
            PrivateKey privateKey = PemUtil.loadPrivateKey(keyInputStream);
            X509Certificate certificate = PemUtil.loadCertificate(certInputStream);
            if (StrUtil.isBlank(this.getCertSerialNo())) {
                this.certSerialNo = certificate.getSerialNumber().toString(16).toUpperCase();
            }

            CertificatesManager certificatesManager = CertificatesManager.getInstance();
            certificatesManager.putMerchant(mchId, new WechatPayCredentials(mchId, new PrivateKeySigner(certSerialNo, privateKey)), apiV3Key.getBytes(StandardCharsets.UTF_8));
            Verifier verifier = certificatesManager.getVerifier(mchId);

            WechatPayHttpClientBuilder wechatPayHttpClientBuilder = WechatPayHttpClientBuilder.create()
                    .withMerchant(mchId, certSerialNo, privateKey)
                    .withValidator(new WechatPayValidator(verifier));
            CloseableHttpClient httpClient = wechatPayHttpClientBuilder.build();

            this.v3HttpClient = httpClient;
            this.privateKey = privateKey;
            this.verifier = verifier;

            return httpClient;
        } catch (Exception e) {
            throw new PayException("微信v3HttpClient请求构造失败", e);
        }
    }

    private void validConfig() throws PayException {
        if (StrUtil.isBlank(this.getAppId())) {
            throw new PayException("appId未配置，请检查");
        }
        if (StrUtil.isBlank(this.getMchId())) {
            throw new PayException("mchId未配置，请检查");
        }
        if (StrUtil.isBlank(this.getApiV3Key())) {
            throw new PayException("apiV3Key未配置，请检查");
        }
        if (StrUtil.isBlank(this.getPrivateKeyPath())) {
            throw new PayException("apiclient_key.pem证书未配置，请检查");
        }
        if (StrUtil.isBlank(this.getPrivateCertPath())) {
            throw new PayException("apiclient_cert.pem证书未配置，请检查");
        }
    }

    private InputStream loadConfigInputStream(String configPath) throws PayException {
        ClassPathResource classPathResource = new ClassPathResource(configPath);
        try {
            return classPathResource.getInputStream();
        } catch (IOException e) {
            log.error("read config error", e);
            throw new PayException(e.getMessage());
        }
    }
}
