package org.example.Lab2_BLPS.common.util;

import javax.validation.ConstraintValidatorContext;

public final class ConstraintUtil {

    public static boolean addConstraintViolation(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        return false;
    }
}
