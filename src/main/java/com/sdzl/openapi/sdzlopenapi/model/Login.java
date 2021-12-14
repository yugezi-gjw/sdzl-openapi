package com.sdzl.openapi.sdzlopenapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Login Information.<br>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Login {
    private String name;
    private String username;
    private Long resourceSer;
    private String resourceName;
    private String group;

    private String token;
}
