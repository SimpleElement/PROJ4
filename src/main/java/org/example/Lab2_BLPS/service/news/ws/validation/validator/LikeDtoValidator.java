package org.example.Lab2_BLPS.service.news.ws.validation.validator;

import org.example.Lab2_BLPS.common.util.ConstraintUtil;
import org.example.Lab2_BLPS.service.news.ws.dto.LikeDto;
import org.example.Lab2_BLPS.service.news.ws.validation.constraint.LikeDtoConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class LikeDtoValidator implements ConstraintValidator<LikeDtoConstraint, LikeDto> {
    @Override
    public boolean isValid(LikeDto likeDto, ConstraintValidatorContext context) {
        boolean res = true;

        // Null constraint
        if (Objects.isNull(likeDto.getNewsId())) {
            res = ConstraintUtil.addConstraintViolation(context, "Поле newsId не должно быть null");
        }

        // Size constraint
        if (Objects.nonNull(likeDto.getNewsId()) && likeDto.getNewsId() < 1) {
            res = ConstraintUtil.addConstraintViolation(context, "Поле newsId не должно быть меньше 1");
        }
        if (Objects.nonNull(likeDto.getNewsId()) && likeDto.getNewsId() > 2147483647) {
            res = ConstraintUtil.addConstraintViolation(context, "Поле newsId не должно быть больше 2147483647");
        }

        return res;
    }
}
