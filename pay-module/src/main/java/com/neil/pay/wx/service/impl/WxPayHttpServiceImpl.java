package com.neil.pay.wx.service.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.neil.pay.exception.PayException;
import com.neil.pay.utils.GsonParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static com.neil.pay.constant.Constant.APPLICATION_JSON;
import static org.apache.http.HttpHeaders.ACCEPT;
import static org.apache.http.HttpHeaders.CONTENT_TYPE;

/**
 * @author nihao
 * @date 2023/7/12
 */
@Slf4j
public class WxPayHttpServiceImpl extends BaseWxPayService {

    @Override
    public String postV3(String url, String request) throws PayException {
        CloseableHttpClient httpClient = this.createV3Client();
        HttpPost httpPost = this.createHttpPost(url, request);
        httpPost.addHeader(ACCEPT, APPLICATION_JSON);
        httpPost.addHeader(CONTENT_TYPE, APPLICATION_JSON);

        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            //v3已经改为通过状态码判断200 204 成功
            int statusCode = response.getStatusLine().getStatusCode();
            //post方法有可能会没有返回值的情况
            String responseString = null;
            if (response.getEntity() != null) {
                responseString = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            }

            if (HttpStatus.SC_OK == statusCode || HttpStatus.SC_NO_CONTENT == statusCode) {
                log.info("\n【请求地址】：{}\n【请求数据】：{}\n【响应数据】：{}", url, request, responseString);
                return responseString;
            }

            //有错误提示信息返回
            throw convertException(GsonParser.parse(responseString));
        } catch (Exception e) {
            log.error("\n【请求地址】：{}\n【请求数据】：{}\n【异常信息】：{}", url, request, e.getMessage());
            throw (e instanceof PayException) ? (PayException) e : new PayException(e.getMessage(), e);
        } finally {
            httpPost.releaseConnection();
        }
    }


    private CloseableHttpClient createV3Client() throws PayException {
        CloseableHttpClient v3HttpClient = this.getConfig().getV3HttpClient();
        if (Objects.isNull(v3HttpClient)) {
            return this.getConfig().initV3HttpClient();
        }
        return v3HttpClient;
    }

    private HttpPost createHttpPost(String url, String requestStr) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(this.createEntry(requestStr));

        httpPost.setConfig(RequestConfig.custom()
                .setConnectionRequestTimeout(this.getConfig().getHttpConnectionTimeout())
                .setConnectTimeout(this.getConfig().getHttpConnectionTimeout())
                .setSocketTimeout(this.getConfig().getHttpTimeout())
                .build());

        return httpPost;
    }

    private StringEntity createEntry(String requestStr) {
        return new StringEntity(requestStr, ContentType.create(APPLICATION_JSON, "utf-8"));
    }

    private PayException convertException(JsonObject jsonObject) {
        JsonElement codeElement = jsonObject.get("code");
        String code = codeElement == null ? null : codeElement.getAsString();
        String message = jsonObject.get("message").getAsString();
        PayException payException = new PayException(message);
        payException.setCode(code);
        payException.setErrorMsg(message);
        return payException;
    }
}
