package com.sdzl.openapi.sdzlopenapi.vais;

import lombok.Data;

/**
 * VAIS config model
 */
@Data
public class VAISConfig {
    private String clientId;
    private String clientSecret;
    private String domainName;
    private String gettingTokenEndPoint;
    private String refreshTokenEndPoint;
}
