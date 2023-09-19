package org.example.Lab2_BLPS.service.report.service.validator;

import org.example.Lab2_BLPS.common.throwable.exception.BadRequestException;
import org.example.Lab2_BLPS.service.report.model.NewsServiceBanEntity;
import org.example.Lab2_BLPS.service.report.model.ReportEntity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

public class ReportAssert {
    public static void isUserExists(Boolean res, String message) {
        if (res)
            return;
        throw new BadRequestException(message);
    }

    public static void isCommentExists(Boolean res, String message) {
        if (res)
            return;
        throw new BadRequestException(message);
    }

    public static void isCommentOfUser(Boolean res, String message) {
        if (res)
            return;
        throw new BadRequestException(message);
    }

    public static void reportIdIsNonNull(Long id, String message) {
        if (Objects.nonNull(id))
            return;
        throw new BadRequestException(message);
    }

    public static void reportIsNonNull(ReportEntity report, String message) {
        if (Objects.nonNull(report))
            return;
        throw new BadRequestException(message);
    }

    public static void countModifyingNotZero(int countModifying, String message) {
        if (countModifying != 0)
            return;
        throw new BadRequestException(message);
    }

    public static void reportReadyToModerate(Boolean res, String message) {
        if (res)
            return;
        throw new BadRequestException(message);
    }

    public static void newsServiceBanNonNull(NewsServiceBanEntity entity, String message) {
        if (Objects.nonNull(entity))
            return;
        throw new BadRequestException(message);
    }

    public static void unbanDateNonNull(LocalDateTime date, String message) {
        if (Objects.nonNull(date))
            return;
        throw new BadRequestException(message);
    }
}
