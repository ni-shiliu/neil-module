package com.neil.pay.wx.auth;

import com.neil.pay.wx.Credentials;
import com.neil.pay.wx.WechatPayUploadHttpPost;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.client.methods.HttpRequestWrapper;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

/**
 * @author nihao
 * @date 2023/7/12
 */
@Slf4j
public class WechatPayCredentials implements Credentials {

    protected static final String SYMBOLS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    protected static final SecureRandom RANDOM = new SecureRandom();
    protected final String merchantId;
    protected final Signer signer;

    public WechatPayCredentials(String merchantId, Signer signer) {
        this.merchantId = merchantId;
        this.signer = signer;
    }

    public String getMerchantId() {
        return merchantId;
    }

    protected long generateTimestamp() {
        return System.currentTimeMillis() / 1000;
    }

    protected String generateNonceStr() {
        char[] nonceChars = new char[32];
        for (int index = 0; index < nonceChars.length; ++index) {
            nonceChars[index] = SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length()));
        }
        return new String(nonceChars);
    }

    @Override
    public final String getSchema() {
        return "WECHATPAY2-SHA256-RSA2048";
    }

    @Override
    public final String getToken(HttpRequestWrapper request) throws IOException {
        String nonceStr = generateNonceStr();
        long timestamp = generateTimestamp();

        String message = buildMessage(nonceStr, timestamp, request);
        log.debug("authorization message=[{}]", message);

        Signer.SignatureResult signature = signer.sign(message.getBytes(StandardCharsets.UTF_8));

        String token = "mchid=\"" + getMerchantId() + "\","
                + "nonce_str=\"" + nonceStr + "\","
                + "timestamp=\"" + timestamp + "\","
                + "serial_no=\"" + signature.certificateSerialNumber + "\","
                + "signature=\"" + signature.sign + "\"";
        log.debug("authorization token=[{}]", token);

        return token;
    }

    protected String buildMessage(String nonce, long timestamp, HttpRequestWrapper request) throws IOException {
        URI uri = request.getURI();
        String canonicalUrl = uri.getRawPath();
        if (uri.getQuery() != null) {
            canonicalUrl += "?" + uri.getRawQuery();
        }

        String body = "";
        // PATCH,POST,PUT
        if (request.getOriginal() instanceof WechatPayUploadHttpPost) {
            body = ((WechatPayUploadHttpPost) request.getOriginal()).getMeta();
        } else if (request instanceof HttpEntityEnclosingRequest) {
            body = EntityUtils.toString(((HttpEntityEnclosingRequest) request).getEntity(), StandardCharsets.UTF_8);
        }

        return request.getRequestLine().getMethod() + "\n"
                + canonicalUrl + "\n"
                + timestamp + "\n"
                + nonce + "\n"
                + body + "\n";
    }
}
