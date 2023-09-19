package org.example.Lab2_BLPS.service.news.ws.validation.validator;

import org.example.Lab2_BLPS.common.util.ConstraintUtil;
import org.example.Lab2_BLPS.service.news.ws.dto.NewsDto;
import org.example.Lab2_BLPS.service.news.ws.validation.constraint.NewsDtoConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class NewsDtoValidator implements ConstraintValidator<NewsDtoConstraint, NewsDto> {
    @Override
    public boolean isValid(NewsDto newsDto, ConstraintValidatorContext context) {
        boolean res = true;

        // Null constraint
        if (Objects.isNull(newsDto.getId())) {
            res = ConstraintUtil.addConstraintViolation(context, "Поле id не должно быть null");
        }

        // Size constraint
        if (Objects.nonNull(newsDto.getId()) && newsDto.getId() < 1) {
            res = ConstraintUtil.addConstraintViolation(context, "Поле id не должно быть меньше 1");
        }
        if (Objects.nonNull(newsDto.getId()) && newsDto.getId() > 2147483647) {
            res = ConstraintUtil.addConstraintViolation(context, "Поле id не должно быть больше 2147483647");
        }

        return res;
    }
}
