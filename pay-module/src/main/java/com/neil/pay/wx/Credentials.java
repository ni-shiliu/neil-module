package com.neil.pay.wx;

import org.apache.http.client.methods.HttpRequestWrapper;

import java.io.IOException;

/**
 * @author nihao
 * @date 2023/7/12
 */
public interface Credentials {

    String getSchema();

    String getToken(HttpRequestWrapper request) throws IOException;
}
