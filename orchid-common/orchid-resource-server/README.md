# Spring Security Oauth2中resource server实现支持

资源服务器的作用就是保护资源，验证令牌是否有效，有效则允许访问受保护的资源。针对认证服务器不同类型的Token存储方式时实现方式不同。



### 1、认证服务器使用InmemoryTokenStore存储Token在内存中
Token信息存在认证服务器内存中时，资源服务器每次校验token时必须远程请求认证服务器的校验端点去校验token的正确性。

实现：设置认证服务器校验token的端点信息，如：
```
security:
  oauth2:
    client:
      client-id: client
      client-secret: client
    resource:
      token-info-uri: http://localhost:9001/oauth/check_token
```


### 2、认证服务器使用RedisTokenStore存储Token在reids中
Token信息存在reids分布式缓存中，资源服务器只需设置相应的RedisTokenStore对象即可，如：

```java
@Configuration
public class TokenConfig {


    /**
     * Token信息存储在分布式缓存Redis中时
     */
    @Configuration
    protected class RedisTokenConfig {


        @Bean
        @ConditionalOnMissingBean(TokenStore.class)
        public TokenStore tokenStore(RedisConnectionFactory redisConnectionFactory){
            RedisTokenStore tokenStore=new RedisTokenStore(redisConnectionFactory);
            tokenStore.setPrefix("orchid:auth-server:");
            return tokenStore;
        }

    }

}
```



实现：设置auth-server token存储方式为redis；**_添加该配置后自动导入了RedisTokenStore对象_**

```
orchid:
  auth-server:
    #    token存储类别：inmemory,redis,jwt
    token-store-type: redis
```




### 3、认证服务器使用JWT Token时
Token信息签名加密后，存储在jwt令牌中。资源服务器校验时只需要获取到认证服务器签名时的秘钥，即可进行令牌的校验和解析。

实现：设置认证服务器获取秘钥信息的端点，如：
```
security:
  oauth2:
    client:
      client-id: client
      client-secret: client
    resource:
      jwt:
        key-uri:  http://localhost:9001/oauth/token_key

```

### 4、接口权限控制
资源服务接口权限控制，可以使用Spring Security提供的权限控制功能，需要预先分配给用户特定的权限，并指定各项操作执行所要求的权限。

用户请求执行某项操作时，Spring Security会先检查用户所拥有的权限是否符合执行该项操作所要求的权限，如果符合，才允许执行该项操作，否则拒绝执行该项操作。

#### 开启级权限控制：
1、开启权限方法级权限控制:```@EnableGlobalMethodSecurity(prePostEnabled = true)```

2、使用注解控制方法的权限：
````
@PreAuthorize(spel)：在方法执行之前进行权限验证
@PostAuthorize(spel)：在方法执行之后进行权限验证
@PreFilter(spel)：在方法执行之前对方法集合类型的参数进行过滤
@PostFilter(spel)：在方法执行之后对方法返回的结合类型值进行过滤
````    

#### 更多权限控制实现



## 5、参考


