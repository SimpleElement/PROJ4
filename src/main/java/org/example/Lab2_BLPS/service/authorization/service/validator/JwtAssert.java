package org.example.Lab2_BLPS.service.authorization.service.validator;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import lombok.SneakyThrows;
import org.example.Lab2_BLPS.common.throwable.exception.BadRequestException;
import org.example.Lab2_BLPS.common.throwable.exception.NonAuthoritativeInformationException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.text.ParseException;
import java.util.Date;

public class JwtAssert {
    public static void canParseToken(String token, String message) {
        try {
            SignedJWT.parse(token);
        } catch (Exception e) {
            throw new BadRequestException(message);
        }
    }

    public static void verifyToken(String token, String secret, String message) throws ParseException, JOSEException {
        if (SignedJWT.parse(token).verify(new MACVerifier(secret))) {
            return;
        }
        throw new BadRequestException(message);
    }

    @SneakyThrows
    public static void isExpiredAccessToken(Date timeCreation, Long currentTime, String message) {
        long exp = timeCreation.getTime() / 1000;

        if (currentTime >= exp)
            throw new NonAuthoritativeInformationException(message);
    }

    public static void isExistsAccessToken(String token, String message) {
        if (token == null)
            throw new BadRequestException(message);
    }

    public static void isBearerAccessToken(String token, String message) {
        if (token.startsWith("Bearer "))
            return;
        throw new BadRequestException(message);
    }
}
