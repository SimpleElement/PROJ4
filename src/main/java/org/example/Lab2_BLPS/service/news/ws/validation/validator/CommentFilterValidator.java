package org.example.Lab2_BLPS.service.news.ws.validation.validator;

import org.example.Lab2_BLPS.common.util.ConstraintUtil;
import org.example.Lab2_BLPS.service.news.ws.filter.CommentFilter;
import org.example.Lab2_BLPS.service.news.ws.validation.constraint.CommentFilterConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class CommentFilterValidator implements ConstraintValidator<CommentFilterConstraint, CommentFilter> {
    @Override
    public boolean isValid(CommentFilter commentFilter, ConstraintValidatorContext context) {
        boolean res = true;

        // Null constraint
        if (Objects.isNull(commentFilter.getNewsId())) {
            res = ConstraintUtil.addConstraintViolation(context, "Поле newsId не должно быть null");
        }

        // Size constraint
        if (Objects.nonNull(commentFilter.getNewsId()) && commentFilter.getNewsId() < 1) {
            res = ConstraintUtil.addConstraintViolation(context, "Поле newsId не должно быть меньше 1");
        }
        if (Objects.nonNull(commentFilter.getNewsId()) && commentFilter.getNewsId() > 2147483647) {
            res = ConstraintUtil.addConstraintViolation(context, "Поле newsId не должно быть больше 2147483647");
        }
        return res;
    }
}
