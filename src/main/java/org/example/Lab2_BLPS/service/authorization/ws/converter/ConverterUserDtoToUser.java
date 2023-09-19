package org.example.Lab2_BLPS.service.authorization.ws.converter;

import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;
import org.example.Lab2_BLPS.service.authorization.ws.dto.UserDto;
import org.example.Lab2_BLPS.service.authorization.model.UserEntity;

@Component
public class ConverterUserDtoToUser implements Converter<UserDto, UserEntity> {
    @Override
    public UserEntity convert(UserDto source) {
        UserEntity res = new UserEntity();

        res.setUsername(source.getUsername());
        res.setPassword(source.getPassword());
        return res;
    }
}
