package com.sdzl.openapi.sdzlopenapi.vais;

import com.google.gson.Gson;
import com.sdzl.openapi.sdzlopenapi.config.SysConfig;
import com.sdzl.openapi.sdzlopenapi.model.VAISToken;
import com.sdzl.openapi.sdzlopenapi.util.UserDomainUtil;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Http client for VAIS service
 */
@Slf4j
@Service
public class VAISHttpClient {

//    private sysConfig sysConfig;
    private static String AUTHORIZATION_STR;
    
    @Autowired
    private SysConfig sysConfig;

    /**
     * Get token from VAIS by ADUserId and ADPassword
     *
     * @param userId     AD UserId
     * @param password   AD Password
     * @param domainName domain name
     * @return the token model
     */
    public VAISToken getTokenFromVAIS(String userId, String password, String... domainName) {
        VAISToken token = null;
        try {
            Map<String, String> params = new HashMap() {{
                put("grant_type", "password");
                if (domainName.length == 0) {
                    put("username", UserDomainUtil.addDomain(sysConfig.getDomainName(), userId));
                } else {
                    put("username", UserDomainUtil.addDomain(domainName[0], userId));
                }
                put("password", password);
            }};
            HttpPost postRequest = getHttpPostRequest(sysConfig.getGettingTokenEndPoint(), params);
            token = post(postRequest,VAISToken.class);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return token;
    }

    public VAISToken refreshTokenFromVAIS(String oldRefreshToken) {
        if (sysConfig == null) {
            return null;
        }

        Map<String, String> params = new HashMap() {{
            put("grant_type", "refresh_token");
            put("refresh_token", oldRefreshToken);
        }};
        VAISToken newToken = null;
        try {
            HttpPost postRequest = getHttpPostRequest(sysConfig.getGettingTokenEndPoint(), params);
            newToken = post(postRequest,VAISToken.class);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return newToken;
    }
    private <T> T post(HttpPost postRequest,Class<T> type) {
        CloseableHttpResponse response = null;
        try {
            CloseableHttpClient client = getHttpsClient();
            response = client.execute(postRequest);
            HttpEntity httpEntity = response.getEntity();
            Reader reader = null;
            reader = new InputStreamReader(httpEntity.getContent());
            T t =  new Gson().fromJson(reader, type);
//            T t =  new Yaml().loadAs(httpEntity.getContent(),type);
            EntityUtils.consume(httpEntity);
            return t;
        } catch (Exception e) {
            log.error("{}", e);
            return null;
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }

    private HttpPost getHttpPostRequest(String url, Map<String, String> params) throws UnsupportedEncodingException {
        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("Content-Type", "application/x-www-form-urlencoded");
        postRequest.addHeader("Authorization", "Basic "
                + getAuthorization(sysConfig.getClientId(), sysConfig.getClientSecret()));

        //装填参数
        List<NameValuePair> nvps = new ArrayList<>();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        log.debug("Getting VAIS token request url: [{}]", url);
        log.debug("Getting VAIS token request params: [{}]", nvps.toString());
        //设置参数到请求对象中
        postRequest.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
        return postRequest;
    }

    private CloseableHttpClient getHttpsClient() throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException {
        //采用绕过验证的方式处理https请求
        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(
                SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build(),
                NoopHostnameVerifier.INSTANCE);
        //创建自定义的httpclient对象
        return HttpClients.custom().setSSLSocketFactory(socketFactory).build();
    }

    private String getAuthorization(String clientId, String clientSecret) {
        try {
            if(AUTHORIZATION_STR == null) {
                String encodeClientId = URLEncoder.encode(clientId, "utf-8");
                String encodeClientSecret = URLEncoder.encode(clientSecret, "utf-8");
                String clientStr = encodeClientId + ":" + encodeClientSecret;
                AUTHORIZATION_STR = Base64.encodeBase64String(clientStr.getBytes());
            }
            return AUTHORIZATION_STR;
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
}
