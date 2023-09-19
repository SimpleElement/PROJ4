package org.example.Lab2_BLPS.service.report.ws.validation.constraint;

import org.example.Lab2_BLPS.service.report.ws.validation.validator.ReportDtoValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target( { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { ReportDtoValidator.class })
public @interface ReportDtoConstraint {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
