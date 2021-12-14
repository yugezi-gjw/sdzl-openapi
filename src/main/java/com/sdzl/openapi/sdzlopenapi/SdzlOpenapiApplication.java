package com.sdzl.openapi.sdzlopenapi;

import com.sdzl.openapi.sdzlopenapi.anticorruption.FHIRContextFactory;
import com.sdzl.openapi.sdzlopenapi.vais.AuthenticationCachePool;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SdzlOpenapiApplication {

  public static void main(String[] args) {
    SpringApplication.run(SdzlOpenapiApplication.class, args);

    AuthenticationCachePool.build();
  }

}
