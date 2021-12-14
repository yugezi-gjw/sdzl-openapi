package com.sdzl.openapi.sdzlopenapi.util;

import static org.apache.commons.lang3.StringUtils.remove;

import com.sdzl.openapi.sdzlopenapi.anticorruption.UserAntiCorruptionServiceImp;
import com.sdzl.openapi.sdzlopenapi.config.SysConfig;
import com.sdzl.openapi.sdzlopenapi.model.Login;
import com.sdzl.openapi.sdzlopenapi.model.User;
import com.sdzl.openapi.sdzlopenapi.model.VAISToken;
import com.sdzl.openapi.sdzlopenapi.vais.AuthenticationCachePool;
import com.sdzl.openapi.sdzlopenapi.vais.UserContextHandler;
import com.sdzl.openapi.sdzlopenapi.vais.VAISHttpClient;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthenticationUtil {

    @Autowired
    private UserAntiCorruptionServiceImp userAntiCorruptionServiceImp;
    @Autowired
    private SysConfig sysConfig;
    @Autowired
    private VAISHttpClient vaisHttpClient;

    public boolean initDefaultToken() {
        String defaultUserToken = getDefaultUserToken();
        if (Objects.nonNull(defaultUserToken)) {
            UserContextHandler.set(defaultUserToken);
            AuthenticationCachePool
                .getCache().put(sysConfig.getFhirTockenAuthUserName(), defaultUserToken);
            return true;
        }
        return false;
    }

    private String getDefaultUserToken() {
        String defaultUserToken = AuthenticationCachePool.getCache().get(sysConfig.getFhirTockenAuthUserName());
        if(Objects.nonNull(defaultUserToken)){
            return defaultUserToken;
        }

        VAISToken vaisToken = vaisHttpClient.getTokenFromVAIS(sysConfig.getFhirTockenAuthUserName(), sysConfig.getFhirTockenAuthPwd());
        if (Objects.nonNull(vaisToken)) {
            return vaisToken.getAccess_token();
        }
        log.error("Fail to init default user token. Pls check the sys config[{}].",
            ReflectionToStringBuilder.toString(sysConfig));
        return null;
    }


}
