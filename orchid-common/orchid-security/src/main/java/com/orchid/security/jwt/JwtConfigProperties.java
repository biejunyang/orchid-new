package com.orchid.security.jwt;

import cn.hutool.core.collection.CollectionUtil;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;


@ConfigurationProperties(prefix = "orchid.auth.jwt")
@Data
public class JwtConfigProperties {


    /**
     * 使用启动jwt认证
     */
    private Boolean enable=false;
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
    private String issuer="orchid-server";

    /**
     * 接收方
     */
    private String audience="orchid-client";


    /**
     * 登录url
     */
    private String login="/login";

    /**
     * 用户名参数名称
     */
    private String usernameParam="username";

    /**
     * 密码参数名称
     */
    private String passwordParam="password";



    /**
     * 忽略的请求地址
     */
    private final JwtConfigProperties.IgnoreConfig ignores = new JwtConfigProperties.IgnoreConfig();




    @Data
    public static class IgnoreConfig {
        /**
         * 需要忽略的 URL 格式，不考虑请求方法
         */
        private List<String> pattern = CollectionUtil.newArrayList();

        /**
         * 需要忽略的 GET 请求
         */
        private List<String> get = CollectionUtil.newArrayList();

        /**
         * 需要忽略的 POST 请求
         */
        private List<String> post = CollectionUtil.newArrayList();

        /**
         * 需要忽略的 DELETE 请求
         */
        private List<String> delete = CollectionUtil.newArrayList();

        /**
         * 需要忽略的 PUT 请求
         */
        private List<String> put = CollectionUtil.newArrayList();

        /**
         * 需要忽略的 HEAD 请求
         */
        private List<String> head = CollectionUtil.newArrayList();

        /**
         * 需要忽略的 PATCH 请求
         */
        private List<String> patch = CollectionUtil.newArrayList();

        /**
         * 需要忽略的 OPTIONS 请求
         */
        private List<String> options = CollectionUtil.newArrayList();

        /**
         * 需要忽略的 TRACE 请求
         */
        private List<String> trace = CollectionUtil.newArrayList();
    }

}
