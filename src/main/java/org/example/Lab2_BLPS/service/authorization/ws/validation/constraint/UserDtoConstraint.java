package org.example.Lab2_BLPS.service.authorization.ws.validation.constraint;

import org.example.Lab2_BLPS.service.authorization.ws.validation.validator.UserDtoValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target( { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { UserDtoValidator.class })
public @interface UserDtoConstraint {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
