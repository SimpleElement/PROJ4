package org.example.Lab2_BLPS.service.mail.ws.validation.validator;

import org.example.Lab2_BLPS.common.util.ConstraintUtil;
import org.example.Lab2_BLPS.service.mail.ws.dto.MessageDto;
import org.example.Lab2_BLPS.service.mail.ws.validation.constraint.MessageDtoConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class MessageDtoValidator implements ConstraintValidator<MessageDtoConstraint, MessageDto> {
    @Override
    public boolean isValid(MessageDto messageDto, ConstraintValidatorContext context) {
        boolean res = true;
        System.out.println(context.getDefaultConstraintMessageTemplate());

        // Null constraint
        if (Objects.isNull(messageDto.getRecipient())) {
            res = ConstraintUtil.addConstraintViolation(context, "Поле recipient не должно быть null");
        }
        if (Objects.isNull(messageDto.getTopic())) {
            res = ConstraintUtil.addConstraintViolation(context, "Поле topic не должно быть null");
        }
        if (Objects.isNull(messageDto.getContent())) {
            res = ConstraintUtil.addConstraintViolation(context, "Поле content не должно быть null");
        }

        // Blank constraint
        if (Objects.nonNull(messageDto.getRecipient()) && messageDto.getRecipient().trim().length() == 0) {
            res = ConstraintUtil.addConstraintViolation(context, "Поле recipient не должно быть пустым");
        }
        if (Objects.nonNull(messageDto.getTopic()) && messageDto.getTopic().trim().length() == 0) {
            res = ConstraintUtil.addConstraintViolation(context, "Поле topic не должно быть пустым");
        }
        if (Objects.nonNull(messageDto.getContent()) && messageDto.getContent().trim().length() == 0) {
            res = ConstraintUtil.addConstraintViolation(context, "Поле content не должно быть пустым");
        }

        // Size constraint
        if (Objects.nonNull(messageDto.getRecipient()) && messageDto.getRecipient().trim().length() > 50) {
            res = ConstraintUtil.addConstraintViolation(context, "Количество букв в поле recipient не должно быть больше 50");
        }
        if (Objects.nonNull(messageDto.getTopic()) && messageDto.getTopic().trim().length() > 250) {
            res = ConstraintUtil.addConstraintViolation(context, "Количество букв в поле topic не должно быть больше 250");
        }
        if (Objects.nonNull(messageDto.getContent()) && messageDto.getContent().trim().length() > 3000) {
            res = ConstraintUtil.addConstraintViolation(context, "Количество букв в поле content не должно быть больше 3000");
        }

        return res;
    }
}
