package org.example.Lab2_BLPS.service.news.ws.validation.validator;

import org.example.Lab2_BLPS.common.util.ConstraintUtil;
import org.example.Lab2_BLPS.service.news.ws.dto.CommentDto;
import org.example.Lab2_BLPS.service.news.ws.validation.constraint.CommentDtoConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class CommentDtoValidator implements ConstraintValidator<CommentDtoConstraint, CommentDto> {
    @Override
    public boolean isValid(CommentDto commentDto, ConstraintValidatorContext context) {
        boolean res = true;

        // Null constraint
        if (Objects.isNull(commentDto.getContent())) {
            res = ConstraintUtil.addConstraintViolation(context, "Поле content не должно быть null");
        }
        if (Objects.isNull(commentDto.getNewsId())) {
            res = ConstraintUtil.addConstraintViolation(context, "Поле newsId не должно быть null");
        }

        // Blank constraint
        if (Objects.nonNull(commentDto.getContent()) && commentDto.getContent().trim().length() == 0) {
            res = ConstraintUtil.addConstraintViolation(context, "Поле content не должно быть пустым");
        }

        // Size constraint
        if (Objects.nonNull(commentDto.getContent()) && commentDto.getContent().trim().length() > 500) {
            res = ConstraintUtil.addConstraintViolation(context, "Количество букв в поле content не должно быть больше 500");
        }
        if (Objects.nonNull(commentDto.getNewsId()) && commentDto.getNewsId() < 1) {
            res = ConstraintUtil.addConstraintViolation(context, "Поле newsId не должно быть меньше 1");
        }
        if (Objects.nonNull(commentDto.getNewsId()) && commentDto.getNewsId() > 2147483647) {
            res = ConstraintUtil.addConstraintViolation(context, "Поле newsId не должно быть больше 2147483647");
        }

        return res;
    }
}
