package com.sdzl.openapi.sdzlopenapi.vais;

public class UserContextHandler {
    private static ThreadLocal<String> contextLocal = new ThreadLocal<>();

    public static void set(String token){
        contextLocal.set(token);
    }
    public static String get(){
        return contextLocal.get();
    }
}
