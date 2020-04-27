package com.orchid.web.auth;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

//@Component

@ConfigurationProperties(prefix = "orchid.auth.jwt")
@Data
public class JwtProperties {

    /**
     * 签名秘钥
     */
    private String privateKey="6MNSobBWWEXCX6MNSobBRCHGIO0fS";

    /**
     * token过期时间、单位秒
     */
    private long expiration=10;

    /**
     * 签发者
     */
    private String issuer="orchid server";

    /**
     * 接收方
     */
    private String audience="orchid client";
}
