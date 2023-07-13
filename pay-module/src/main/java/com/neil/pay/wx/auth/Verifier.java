package com.neil.pay.wx.auth;

import java.security.cert.X509Certificate;

/**
 * @author nihao
 * @date 2023/7/12
 */
public interface Verifier {

    boolean verify(String serialNumber, byte[] message, String signature);

    /**
     * 获取合法的平台证书
     *
     * @return 合法证书
     */
    X509Certificate getValidCertificate();
}
