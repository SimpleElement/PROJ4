package org.example.Lab2_BLPS.service.news.ws.validation.validator;

import org.example.Lab2_BLPS.common.util.ConstraintUtil;
import org.example.Lab2_BLPS.service.news.ws.filter.LikeFilter;
import org.example.Lab2_BLPS.service.news.ws.validation.constraint.LikeFilterConstraint;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

@Component
public class LikeFilterValidator implements ConstraintValidator<LikeFilterConstraint, LikeFilter> {

    @Override
    public boolean isValid(LikeFilter likeFilter, ConstraintValidatorContext context) {
        boolean res = true;

        // Null constraint
        if (Objects.isNull(likeFilter.getNewsId())) {
            res = ConstraintUtil.addConstraintViolation(context, "Поле newsId не должно быть null");
        }

        // Size constraint
        if (Objects.nonNull(likeFilter.getNewsId()) && likeFilter.getNewsId() < 1) {
            res = ConstraintUtil.addConstraintViolation(context, "Поле newsId не должно быть меньше 1");
        }
        if (Objects.nonNull(likeFilter.getNewsId()) && likeFilter.getNewsId() > 2147483647) {
            res = ConstraintUtil.addConstraintViolation(context, "Поле newsId не должно быть больше 2147483647");
        }
        return res;
    }
}
