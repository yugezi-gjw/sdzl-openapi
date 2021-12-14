package com.sdzl.openapi.sdzlopenapi.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName UserDomainUtil
 * @Description
 * @Author bhp9696
 * @Date 2019/2/2 13:02
 * @Version 1.0
 */
public class UserDomainUtil {

    /**
     * 去除用户名中的域名
     * @param domainUserName
     * @return
     */
    public static String removeDomain(String domainUserName){
        String username = domainUserName;
        if(StringUtils.isNotEmpty(username)){
            String[] split = StringUtils.split(domainUserName,"\\");
            if(split != null){
                if(split.length == 2){
                    username = split[1];
                }else{
                    username = split[0];
                }
            }
        }
        return username;
    }

    /**
     *
     * @param domainNam
     * @param userName
     * @return
     */
    public static String addDomain(String domainNam, String userName){
        return domainNam + "\\" + userName;
    }
}