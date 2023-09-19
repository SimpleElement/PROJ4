package org.example.Lab2_BLPS.service.mail.ws.validation.constraint;

import org.example.Lab2_BLPS.service.mail.ws.validation.validator.MessageDtoValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target( { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { MessageDtoValidator.class })
public @interface MessageDtoConstraint {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
