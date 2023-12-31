package com.neil.pay.utils;

import com.neil.pay.exception.PayRuntimeException;

import java.security.*;
import java.util.Base64;
import java.util.Random;

/**
 * @author nihao
 * @date 2023/7/17
 */
public class SignUtil {

    public static String sign(String string, PrivateKey privateKey) {
        try {
            Signature sign = Signature.getInstance("SHA256withRSA");
            sign.initSign(privateKey);
            sign.update(string.getBytes());

            return Base64.getEncoder().encodeToString(sign.sign());
        } catch (NoSuchAlgorithmException e) {
            throw new PayRuntimeException("当前Java环境不支持SHA256withRSA", e);
        } catch (SignatureException e) {
            throw new PayRuntimeException("签名计算失败", e);
        } catch (InvalidKeyException e) {
            throw new PayRuntimeException("无效的私钥", e);
        }
    }

    /**
     * 随机生成32位字符串.
     */
    public static String genRandomStr() {
        return genRandomStr(32);
    }

    /**
     * 生成随机字符串
     *
     * @param length 字符串长度
     * @return 随机字符串
     */
    public static String genRandomStr(int length) {
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}
