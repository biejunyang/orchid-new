package com.orchid.web.auth;


import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.orchid.web.exception.TokenExpiredException;
import com.orchid.web.exception.TokenInvalidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.text.ParseException;
import java.util.Date;

public class JwtTokenUtil {

    private JwtProperties jwtProperties;


    public String createToken(String subject) throws JOSEException {
        return createToken(subject, null);
    }

    /**
     * Token创建
     * @param subject
     * @return
     * @throws JOSEException
     */
    public String createToken(String subject, Object claims) throws JOSEException {

        //1、头部(type,加密算法)
        JWSHeader jwsHeader=new JWSHeader.Builder(JWSAlgorithm.HS256).build();

        //2、载体(保存数据)
        JWTClaimsSet jwtClaimsSet=new JWTClaimsSet.Builder()
                .subject(subject)
                .issuer(jwtProperties.getIssuer())
                .audience(jwtProperties.getAudience())
                .issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis()+ jwtProperties.getExpiration()*1000))
                .claim("claims", claims)
                .build();

        //3、数字签名
        //签名秘钥
        byte[] SECRET = jwtProperties.getPrivateKey().getBytes();
        MACSigner macSigner = new MACSigner(SECRET);
        SignedJWT signedJWT=new SignedJWT(jwsHeader, jwtClaimsSet);
        signedJWT.sign(macSigner);

        //4/、获取toekn
        String token=signedJWT.serialize();
        return token;
    }


    /**
     * Token解析
     * @param token
     * @return
     * @throws ParseException
     * @throws JOSEException
     */
    public Object parseClaim(String token) throws ParseException, JOSEException {
        SignedJWT jwt=SignedJWT.parse(token);

        JWSVerifier jwsVerifier=new MACVerifier(jwtProperties.getPrivateKey());

        if(!jwt.verify(jwsVerifier)){
            throw new TokenInvalidException();
        }

        Date expirationTime=jwt.getJWTClaimsSet().getExpirationTime();
        if(expirationTime.before(new Date())){
            throw new TokenExpiredException();
        }

        return jwt.getJWTClaimsSet().getClaim("claims");
    }


}
