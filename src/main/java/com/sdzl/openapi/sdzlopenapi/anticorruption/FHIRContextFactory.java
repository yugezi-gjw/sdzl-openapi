package com.sdzl.openapi.sdzlopenapi.anticorruption;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.interceptor.BearerTokenAuthInterceptor;
import com.sdzl.openapi.sdzlopenapi.config.SysConfig;
import com.sdzl.openapi.sdzlopenapi.util.ApplicationContextUtil;
import com.sdzl.openapi.sdzlopenapi.vais.UserContextHandler;
import com.varian.fhir.common.Stu3ContextHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by gbt1220 on 12/30/2016.
 */
@Slf4j
@Component
public class FHIRContextFactory {

    protected static FHIRContextFactory instance;
    static Stu3ContextHelper helper;
    protected static FhirContext fhirContext;;

    static {
//        helper = ApplicationContextUtil.getApplicationContext().getBean(Stu3ContextHelper.class);
        fhirContext = FhirContext.forDstu3();
    }

//    private FHIRContextFactory() {
//    }
//
//    /**
//     * Return FHIRContext Instance.<br>
//     *
//     * @return FHIRContext Instance
//     */
//    public static FHIRContextFactory getInstance() {
//        if (instance == null) {
//            instance = new FHIRContextFactory();
//        }
//        return instance;
//    }

    public static void initConfig(SysConfig sysConfig) {
//        fhirContext.getRestfulClientFactory().setConnectTimeout(sysConfig.getFhirConnectionTimeout());
//        fhirContext.getRestfulClientFactory().setConnectionRequestTimeout(sysConfig.getFhirConnectionRequestTimeout());
//        fhirContext.getRestfulClientFactory().setSocketTimeout(sysConfig.getFhirSocketTimeout());
//        fhirContext.getRestfulClientFactory().setPoolMaxTotal(sysConfig.getPoolMax());

    }

    /**
     * Return a New Restful Client.<br>
     *
     * @return Generic Client
     */
    public IGenericClient newRestfulGenericClient(SysConfig sysConfig) {
//        FhirContext fhirContext = Stu3ContextHelper.getStu3Context();
        IGenericClient client = null;
        client = fhirContext.newRestfulGenericClient(sysConfig.getFhirUrl() + "/fhir");
        String userToken = UserContextHandler.get();
        if (userToken != null) {
            client.registerInterceptor(new BearerTokenAuthInterceptor(userToken));
            log.debug("newRestfulGenericClient userName={} tockent={}", sysConfig.getFhirTockenAuthUserName(),
                    userToken);
        } else {
            log.error("Can not get userContext from ThreadLocal");
        }
        return client;
    }
}
