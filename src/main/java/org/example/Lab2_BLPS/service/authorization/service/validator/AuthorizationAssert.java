package org.example.Lab2_BLPS.service.authorization.service.validator;

import org.example.Lab2_BLPS.common.throwable.exception.BadRequestException;

public class AuthorizationAssert {
    public static void isUserNotExists(Boolean res, String username, String message) {
        if (res)
            throw new BadRequestException(message);
    }

    public static void isUserExists(Boolean res, String message) {
        if (res)
            return;
        throw new BadRequestException(message);
    }

    public static void isPasswordCorrect(String getPass, String sourcePass, String message) {
        if (getPass.equals(sourcePass))
            return;
        throw new BadRequestException(message);
    }

    public static void isForgottenKeys(Boolean res, String message) {
        if (!res)
            return;
        throw new BadRequestException(message);
    }
}
