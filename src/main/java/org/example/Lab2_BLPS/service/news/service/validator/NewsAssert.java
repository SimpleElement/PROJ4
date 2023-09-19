package org.example.Lab2_BLPS.service.news.service.validator;

import org.example.Lab2_BLPS.common.throwable.exception.BadRequestException;

public class NewsAssert {
    public static void isNewsState(boolean expression, String message) {
        if (!expression) {
            throw new BadRequestException(message);
        }
    }
}
