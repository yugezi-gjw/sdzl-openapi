package com.sdzl.openapi.sdzlopenapi.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component// 注：必须添加@Component注解，因为该类的实例化操作必须由Spring来完成。
public class ApplicationContextUtil implements ApplicationContextAware {
  private static ApplicationContext context;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    // TODO Auto-generated method stub
    ApplicationContextUtil.context = applicationContext;
  }

  public static ApplicationContext getApplicationContext() {
    return context;
  }

}
