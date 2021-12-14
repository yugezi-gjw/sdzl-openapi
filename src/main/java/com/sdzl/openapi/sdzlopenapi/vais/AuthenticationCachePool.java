package com.sdzl.openapi.sdzlopenapi.vais;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationCachePool {

    @Getter
    private static AuthenticationCache cache;

    public static void build() {
        cache = new AuthenticationCache(30);
    }
}
