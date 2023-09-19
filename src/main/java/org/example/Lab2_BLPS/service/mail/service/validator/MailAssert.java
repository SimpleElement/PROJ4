package org.example.Lab2_BLPS.service.mail.service.validator;

import org.example.Lab2_BLPS.common.throwable.exception.BadRequestException;

public class MailAssert {
    public static void isUserExists(Boolean res, String message) {
        if (res)
            return;
        throw new BadRequestException(message);
    }
}
