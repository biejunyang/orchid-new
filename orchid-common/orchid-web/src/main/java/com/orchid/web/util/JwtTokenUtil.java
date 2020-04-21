package com.orchid.web.util;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.util.Date;

public class JwtTokenUtil {

    /**
     * Token创建
     * @param username
     * @return
     * @throws JOSEException
     */
    public static String createToken(String username) throws JOSEException {

        //1、头部(type,加密算法)
        JWSHeader jwsHeader=new JWSHeader.Builder(JWSAlgorithm.HS256).build();

        //2、载体(保存数据)
        JWTClaimsSet jwtClaimsSet=new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("orchid server")
                .audience("orchid client")
                .issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis()+ 5*1000))//5秒过期
                .claim("username", username)
                .build();

        //3、数字签名
        //签名秘钥
        byte[] SECRET = "6MNSobBRCHGIO0fS6MNSobBRCHGIO0fS".getBytes();
        MACSigner macSigner = new MACSigner(SECRET);
        SignedJWT signedJWT=new SignedJWT(jwsHeader, jwtClaimsSet);
        signedJWT.sign(macSigner);

        //4/、获取toekn
        String token=signedJWT.serialize();
        return token;
    }



    public static void main(String[] args) throws JOSEException {
        String token=createToken("admin");

    }
}
