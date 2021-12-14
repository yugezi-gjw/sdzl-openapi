package com.sdzl.openapi.sdzlopenapi.model;

import lombok.Data;

/**
 * VAIS token model, and the model mappers to VAIS webservice response.
 */
@Data
public class VAISToken {
    private String access_token;
    private Long expires_in;
    private String refresh_token;
    private String token_type;
    private String error;
    private String error_description;
}
