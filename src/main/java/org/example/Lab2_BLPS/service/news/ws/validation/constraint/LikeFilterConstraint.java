package org.example.Lab2_BLPS.service.news.ws.validation.constraint;

import org.example.Lab2_BLPS.service.news.ws.validation.validator.LikeFilterValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target( { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { LikeFilterValidator.class })
public @interface LikeFilterConstraint {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
