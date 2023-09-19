package org.example.Lab2_BLPS.service.authorization.service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.Lab2_BLPS.service.authorization.model.Token;
import org.example.Lab2_BLPS.service.authorization.model.UserEntity;
import org.example.Lab2_BLPS.common.util.DateUtil;
import org.example.Lab2_BLPS.service.authorization.service.validator.JwtAssert;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("kvKHL5jkO4wrWWzyH+2fEhOnBp/a9nAwDD2XTE8GFmE=")
    private String secret;

    private static final Long accessExpiresIn = 900L;

    private static final Long refreshExpiresIn = 86400L;

    private final DateUtil dateUtil;

    public Token generateToken(UserEntity userEntity) throws JOSEException {
        JWSSigner signer = new MACSigner(secret);

        JWTClaimsSet accessTokenClaimsSet = new JWTClaimsSet.Builder()
                .claim("username", userEntity.getUsername())
                .claim("role", userEntity.getRoleOfUser().name())
                .claim("exp", dateUtil.currentUTCSeconds() + accessExpiresIn)
                .build();
        SignedJWT accessTokenJWS = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), accessTokenClaimsSet);
        accessTokenJWS.sign(signer);

        JWTClaimsSet refreshTokenClaimsSet = new JWTClaimsSet.Builder()
                .claim("username", userEntity.getUsername())
                .claim("exp", dateUtil.currentUTCSeconds() + refreshExpiresIn)
                .build();
        SignedJWT refreshTokenJWS = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), refreshTokenClaimsSet);
        refreshTokenJWS.sign(signer);

        Token token = new Token();
        token.setAccessToken(accessTokenJWS.serialize());
        token.setRefreshToken(refreshTokenJWS.serialize());
        return token;
    }

    @SneakyThrows
    public Map<String, Object> getClaims(String token) {
        SignedJWT signedJWT = SignedJWT.parse(token);
        JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
        return claimsSet.getClaims();
    }

    public void isExpiredAccessToken(String token) throws ParseException, JOSEException {
        SignedJWT signedJWT = SignedJWT.parse(token);
        JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
        JwtAssert.isExpiredAccessToken((Date) claimsSet.getClaim("exp"), dateUtil.currentUTCSeconds(), "Время существования AccessToken закончилось");
    }

    @SneakyThrows
    public void verifyToken(String token) {
        JwtAssert.verifyToken(token, secret, "AccessToken не прошёл верефикацию");
    }

    @SneakyThrows
    public void validateRefreshToken(String token) {
        JwtAssert.canParseToken(token, "Невозможно спарсить RefreshToken");
        JwtAssert.verifyToken(token, secret, "RefreshToken не прошёл верефикацию");

        SignedJWT signedJWT = SignedJWT.parse(token);
        JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();

        JwtAssert.isExpiredAccessToken((Date) claimsSet.getClaim("exp"), dateUtil.currentUTCSeconds(), "Время существования RefreshToken закончилось");
    }

    public Token refreshToken(UserEntity userEntity, String refreshToken) throws JOSEException {
        JWSSigner signer = new MACSigner(secret);

        JWTClaimsSet accessTokenClaimsSet = new JWTClaimsSet.Builder()
                .claim("username", userEntity.getUsername())
                .claim("role", userEntity.getRoleOfUser().name())
                .claim("exp", dateUtil.currentUTCSeconds() + accessExpiresIn)
                .build();
        SignedJWT accessTokenJWS = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), accessTokenClaimsSet);
        accessTokenJWS.sign(signer);

        Token token = new Token();
        token.setAccessToken(accessTokenJWS.serialize());
        token.setRefreshToken(refreshToken);
        return token;
    }

    public String getSecret() {
        return secret;
    }
}
