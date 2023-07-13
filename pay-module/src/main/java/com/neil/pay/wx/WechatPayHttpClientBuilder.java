package com.neil.pay.wx;

import com.neil.pay.wx.auth.CertificatesVerifier;
import com.neil.pay.wx.auth.PrivateKeySigner;
import com.neil.pay.wx.auth.WechatPayCredentials;
import com.neil.pay.wx.auth.WechatPayValidator;
import org.apache.http.HttpHost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.execchain.ClientExecChain;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.List;

/**
 * @author nihao
 * @date 2023/7/12
 */
public class WechatPayHttpClientBuilder extends HttpClientBuilder {

    private static final String OS = System.getProperty("os.name") + "/" + System.getProperty("os.version");
    private static final String VERSION = System.getProperty("java.version");
    private Credentials credentials;
    private Validator validator;


    private WechatPayHttpClientBuilder() {
        super();

        String userAgent = String.format(
                "WechatPay-Apache-HttpClient/%s (%s) Java/%s",
                getClass().getPackage().getImplementationVersion(),
                OS,
                VERSION == null ? "Unknown" : VERSION);
        setUserAgent(userAgent);
    }

    public static WechatPayHttpClientBuilder create() {
        return new WechatPayHttpClientBuilder();
    }

    public WechatPayHttpClientBuilder withMerchant(String merchantId, String serialNo, PrivateKey privateKey) {
        this.credentials = new WechatPayCredentials(merchantId, new PrivateKeySigner(serialNo, privateKey));
        return this;
    }

    public WechatPayHttpClientBuilder withCredentials(Credentials credentials) {
        this.credentials = credentials;
        return this;
    }

    public WechatPayHttpClientBuilder withWechatPay(List<X509Certificate> certificates) {
        this.validator = new WechatPayValidator(new CertificatesVerifier(certificates));
        return this;
    }

    public WechatPayHttpClientBuilder withProxy(HttpHost proxy) {
        if (proxy != null) {
            this.setProxy(proxy);
        }
        return this;
    }

    /**
     * Please use {@link #withWechatPay(List)} instead
     *
     * @param certificates 平台证书list
     * @return 具有验证器的builder
     */
    @SuppressWarnings("SpellCheckingInspection")
    @Deprecated
    public WechatPayHttpClientBuilder withWechatpay(List<X509Certificate> certificates) {
        return withWechatPay(certificates);
    }

    public WechatPayHttpClientBuilder withValidator(Validator validator) {
        this.validator = validator;
        return this;
    }

    @Override
    public CloseableHttpClient build() {
        if (credentials == null) {
            throw new IllegalArgumentException("缺少身份认证信息");
        }
        if (validator == null) {
            throw new IllegalArgumentException("缺少签名验证信息");
        }
        return super.build();
    }

    @Override
    protected ClientExecChain decorateProtocolExec(final ClientExecChain requestExecutor) {
        return new SignatureExec(this.credentials, this.validator, requestExecutor);
    }
}
