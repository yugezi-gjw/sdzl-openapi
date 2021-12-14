package com.sdzl.openapi.sdzlopenapi.vais;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.concurrent.TimeUnit;
import javax.validation.constraints.NotNull;
import lombok.Getter;

public class AuthenticationCache {

    @Getter
    private final Cache<String, String> tokenCache;


    public AuthenticationCache(int defaultTokenCacheTimeoutInMinutes) {
        this.tokenCache = CacheBuilder.newBuilder().expireAfterAccess(defaultTokenCacheTimeoutInMinutes, TimeUnit.MINUTES).build();
    }

    public void put(@NotNull String username, @NotNull String token) {
        tokenCache.put(username, token);
    }

    public String get(@NotNull String username) {
        return tokenCache.getIfPresent(username);
    }

}
