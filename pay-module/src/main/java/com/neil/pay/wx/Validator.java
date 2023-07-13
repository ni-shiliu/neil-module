package com.neil.pay.wx;

import org.apache.http.client.methods.CloseableHttpResponse;

import java.io.IOException;

/**
 * @author nihao
 * @date 2023/7/12
 */
public interface Validator {

    boolean validate(CloseableHttpResponse response) throws IOException;

}
