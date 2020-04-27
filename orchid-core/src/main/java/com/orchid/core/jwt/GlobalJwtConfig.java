package com.orchid.core.jwt;

import java.util.UUID;

public class GlobalJwtConfig {

    private static String issure="orchid";

    private static String audience=null;

    //过期时间，单位毫秒，默认30分钟
    private static long expiration=30*60*1000;

    //秘钥
    private static String secret= UUID.randomUUID().toString();


    public static long getExpiration() {
        return expiration;
    }

    public static void setExpiration(long expiration) {
        GlobalJwtConfig.expiration = expiration;
    }

    public static String getSecret() {
        return secret;
    }

    public static void setSecret(String secret) {
        GlobalJwtConfig.secret = secret;
    }

    public static String getIssure() {
        return issure;
    }

    public static void setIssure(String issure) {
        GlobalJwtConfig.issure = issure;
    }

    public static String getAudience() {
        return audience;
    }

    public static void setAudience(String audience) {
        GlobalJwtConfig.audience = audience;
    }

}
