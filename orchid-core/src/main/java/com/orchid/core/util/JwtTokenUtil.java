package com.orchid.core.util;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.orchid.core.ResultCodeEnum;
import com.orchid.core.exception.ExpireTokenException;
import com.orchid.core.exception.InvalidTokenException;
import com.orchid.core.exception.JwtTokenException;
import com.orchid.core.jwt.GlobalJwtConfig;

import java.text.ParseException;
import java.util.Date;

public class JwtTokenUtil {


    public static String createToken(String subject){
        return createToken(subject, null);
    }

    public static String createToken(Object claims){
        return createToken(null, claims);
    }

    public static String createToken(String subject,Object claims){
        try {
            Date issureTime=new Date();
            SignedJWT signedJWT=new SignedJWT(new JWSHeader(JWSAlgorithm.HS256),
                    new JWTClaimsSet.Builder()
                            .issuer(GlobalJwtConfig.getIssure())
                            .audience(GlobalJwtConfig.getAudience())
                            .issueTime(issureTime).notBeforeTime(issureTime)
                            .expirationTime(new Date(issureTime.getTime()+GlobalJwtConfig.getExpiration()))
                            .subject(subject).claim("claims", claims)
                            .build()
            );

            JWSSigner jwsSigner=new MACSigner(GlobalJwtConfig.getSecret());

            signedJWT.sign(jwsSigner);

            return signedJWT.serialize();
        } catch (KeyLengthException e) {
            e.printStackTrace();
        } catch (JOSEException e) {
            e.printStackTrace();
        }
        return null;
    }



    public static String parseSubject(String token) {
        SignedJWT signedJWT=parse(token);
        try {
            return signedJWT.getJWTClaimsSet().getSubject();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }



    public static <T> T parseClaims(String token, Class<T> clazz) throws ParseException {
        SignedJWT signedJWT=parse(token);
        Object claims=signedJWT.getJWTClaimsSet().getClaim("claims");
        return claims!=null ? (T) claims : null;
    }


    private static SignedJWT parse(String token){
        try {
            SignedJWT signedJWT=SignedJWT.parse(token);
            JWSVerifier jwsVerifier=new MACVerifier(GlobalJwtConfig.getSecret());
            if(signedJWT.verify(jwsVerifier)){
                if(signedJWT.getJWTClaimsSet().getExpirationTime().compareTo(new Date()) < 0){
                    throw new ExpireTokenException(ResultCodeEnum.TOKEN_EXPIRED_ERROR);
                }
                return signedJWT;
            }else{
                throw new InvalidTokenException(ResultCodeEnum.TOKEN_INVALID_ERROR);
            }
        } catch (ParseException | JOSEException e) {
            e.printStackTrace();
            throw new JwtTokenException(ResultCodeEnum.TOKEN_INVALID_ERROR);
        }
    }


}
