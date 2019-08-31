package com.luna.springbootpractice.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HttpClientPoolUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientPoolUtil.class);

    private static final int STATUS_CODE_SUCCESS = 200;

    private static final int MAX_TIMEOUT = 7000;

    private static final String HTTPS = "https";

    private static PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager();

    static {
        connMgr.setMaxTotal(100);
        connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());
        connMgr.setValidateAfterInactivity(1000);
        RequestConfig.Builder configBuilder = RequestConfig.custom();
        configBuilder.setConnectTimeout(MAX_TIMEOUT);
        configBuilder.setSocketTimeout(MAX_TIMEOUT);
        configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
        HttpClientPoolUtil.requestConfig = configBuilder.build();
    }

    private static RequestConfig requestConfig;

    public static JSONObject doGet(String url) {
        return doGet(url, new HashMap(1));
    }

    public static JSONObject doGet(String url, Map<String, Object> params) {
        String apiUrl = url;
        StringBuffer param = new StringBuffer();
        int i = 0;
        for (String key : params.keySet()) {
            if (i == 0) {
                param.append("?");
            } else {
                param.append("&");
            }
            param.append(key).append("=").append(params.get(key));
            i++;
        }
        apiUrl = apiUrl + param;
        String result = null;

        InputStream inputStream = null;
        try {
            HttpGet httpGet = new HttpGet(apiUrl);
            CloseableHttpResponse closeableHttpResponse = getHttpClient(apiUrl).execute(httpGet);
            HttpEntity entity = closeableHttpResponse.getEntity();
            if (entity != null) {
                inputStream = entity.getContent();
                result = IOUtils.toString(inputStream, "UTF-8");
            }
        } catch (IOException e) {
            logger.error("http get is error:", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error("close InputStream is error:", e);
                }
            }
        }
        return JSON.parseObject(result);
    }

    public static JSONObject doPost(String apiUrl) {
        return doPost(apiUrl, new HashMap(1));
    }

    public static JSONObject doPost(String apiUrl, Map<String, Object> params) {
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse httpResponse = null;

        JSONObject jsonObject = null;
        try {
            httpPost.setConfig(requestConfig);
            List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                BasicNameValuePair basicNameValuePair =
                    new BasicNameValuePair((String)entry.getKey(), entry.getValue().toString());
                pairList.add(basicNameValuePair);
            }
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("UTF-8")));
            httpResponse = getHttpClient(apiUrl).execute(httpPost);

            if (httpResponse != null && httpResponse.getStatusLine().getStatusCode() == STATUS_CODE_SUCCESS) {
                String resultStr = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
                jsonObject = JSON.parseObject(resultStr);
            }
        } catch (IOException e) {
            logger.error("http post is error:", e);
        } finally {
            if (httpResponse != null) {
                try {
                    EntityUtils.consume(httpResponse.getEntity());
                } catch (IOException e) {
                    logger.error("close InputStream is error:", e);
                }
            }
        }
        return jsonObject;
    }

    public static JSONObject doPost(String apiUrl, JSONObject jsonData) {
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse httpResponse = null;

        JSONObject jsonObject = null;

        try {
            httpPost.setConfig(requestConfig);

            StringEntity stringEntity = new StringEntity(jsonData.toString(), "UTF-8");
            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            httpResponse = getHttpClient(apiUrl).execute(httpPost);

            if (httpResponse != null && httpResponse.getStatusLine().getStatusCode() == STATUS_CODE_SUCCESS) {

                String resultStr = EntityUtils.toString(httpResponse.getEntity(), "utf-8");

                jsonObject = JSON.parseObject(resultStr);
            }
        } catch (IOException e) {
            logger.error("http post is error:", e);
        } finally {
            if (httpResponse != null) {
                try {
                    EntityUtils.consume(httpResponse.getEntity());
                } catch (IOException e) {
                    logger.error("close InputStream is error:", e);
                }
            }
        }
        return jsonObject;
    }

    private static CloseableHttpClient getHttpClient(String apiUrl) {
        CloseableHttpClient httpClient;
        if (apiUrl.startsWith(HTTPS)) {
            httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory())
                .setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
        } else {
            httpClient = HttpClients.createDefault();
        }
        return httpClient;
    }

    private static SSLConnectionSocketFactory createSSLConnSocketFactory() {
        SSLContext sslContext = SSLContexts.createSystemDefault();
        return new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
    }
}
