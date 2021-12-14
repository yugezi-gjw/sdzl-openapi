package com.sdzl.openapi.sdzlopenapi.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class SysConfig {

    @Value("${sysConfig.fhirUrl}")
    private String fhirUrl;

    @Value("${sysConfig.fhirTockenAuthUserName}")
    private String fhirTockenAuthUserName;

    @Value("${sysConfig.fhirTockenAuthPwd}")
    private String fhirTockenAuthPwd;

    @Value("${sysConfig.fhirConnectionTimeout}")
    private Integer fhirConnectionTimeout;
    @Value("${sysConfig.fhirConnectionRequestTimeout}")
    private Integer fhirConnectionRequestTimeout;
    @Value("${sysConfig.fhirSocketTimeout}")
    private Integer fhirSocketTimeout;
    @Value("${sysConfig.fhirLanguage}")
    private String fhirLanguage;

    @Value("${sysConfig.clientId}")
    private String clientId;
    @Value("${sysConfig.clientSecret}")
    private String clientSecret;
    @Value("${sysConfig.domainName}")
    private String domainName;
    @Value("${sysConfig.gettingTokenEndPoint}")
    private String gettingTokenEndPoint;
    @Value("${sysConfig.refreshTokenEndPoint}")
    private String refreshTokenEndPoint;


}
