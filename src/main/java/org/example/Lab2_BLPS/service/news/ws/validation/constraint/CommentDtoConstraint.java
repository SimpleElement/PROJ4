package org.example.Lab2_BLPS.service.news.ws.validation.constraint;

import org.example.Lab2_BLPS.service.news.ws.validation.validator.CommentDtoValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target( { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { CommentDtoValidator.class })
public @interface CommentDtoConstraint {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
