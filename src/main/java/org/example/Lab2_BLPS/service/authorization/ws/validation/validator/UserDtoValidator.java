package org.example.Lab2_BLPS.service.authorization.ws.validation.validator;


import org.example.Lab2_BLPS.common.util.ConstraintUtil;
import org.example.Lab2_BLPS.service.authorization.ws.dto.UserDto;
import org.example.Lab2_BLPS.service.authorization.ws.validation.constraint.UserDtoConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class UserDtoValidator implements ConstraintValidator<UserDtoConstraint, UserDto> {

    @Override
    public boolean isValid(UserDto userDto, ConstraintValidatorContext context) {
        boolean res = true;

        // Null constraint
        if (Objects.isNull(userDto.getUsername()))
            res = ConstraintUtil.addConstraintViolation(context, "Поле username не должно быть null");
        if (Objects.isNull(userDto.getPassword()))
            res = ConstraintUtil.addConstraintViolation(context, "Поле password не должно быть null");

        // Blank constraint
        if (Objects.nonNull(userDto.getUsername()) && userDto.getUsername().trim().length() == 0) {
            res = ConstraintUtil.addConstraintViolation(context, "Поле username не должно быть пустым");
        }
        if (Objects.nonNull(userDto.getPassword()) && userDto.getPassword().trim().length() == 0) {
            res = ConstraintUtil.addConstraintViolation(context, "Поле password не должно быть пустым");
        }

        // Size constraint
        if (Objects.nonNull(userDto.getUsername()) && userDto.getUsername().trim().length() < 9) {
            res = ConstraintUtil.addConstraintViolation(context, "Количество букв в поле username не должно быть меньше 8");
        }
        if (Objects.nonNull(userDto.getPassword()) && userDto.getPassword().trim().length() < 12) {
            res = ConstraintUtil.addConstraintViolation(context, "Количество букв в поле password не должно быть меньше 12");
        }
        if (Objects.nonNull(userDto.getUsername()) && userDto.getUsername().trim().length() > 50) {
            res = ConstraintUtil.addConstraintViolation(context, "Количество букв в поле username не должно быть больше 50");
        }
        if (Objects.nonNull(userDto.getPassword()) && userDto.getPassword().trim().length() > 100) {
            res = ConstraintUtil.addConstraintViolation(context, "Количество букв в поле password не должно быть больше 100");
        }

        return res;
    }
}