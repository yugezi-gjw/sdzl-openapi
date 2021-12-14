package com.sdzl.openapi.sdzlopenapi.anticorruption;

import com.google.gson.Gson;
import com.sdzl.openapi.sdzlopenapi.config.SysConfig;
import com.sdzl.openapi.sdzlopenapi.model.Login;
import com.sdzl.openapi.sdzlopenapi.model.User;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by gbt1220 on 12/29/2016.
 */
@Slf4j
@Service
public class UserAntiCorruptionServiceImp {
    @Autowired
    private SysConfig sysConfig;

    public Login login(User user) {
        Login login = null;
        try {
            HttpPost postRequest = getHttpPostRequest(sysConfig.getFhirUrl() + "/login", user);
            login = post(postRequest,Login.class);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return login;
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

    private CloseableHttpClient getHttpsClient() throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException {
        //采用绕过验证的方式处理https请求
        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(
            SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build(),
            NoopHostnameVerifier.INSTANCE);
        //创建自定义的httpclient对象
        return HttpClients.custom().setSSLSocketFactory(socketFactory).build();
    }

    private HttpPost getHttpPostRequest(String url, User user) throws UnsupportedEncodingException {
        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("Content-Type", "application/json");

        StringEntity entity = new StringEntity(new Gson().toJson(user), "utf-8");

        postRequest.setEntity(entity);
        return postRequest;
    }

}