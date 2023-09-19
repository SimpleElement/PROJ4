package org.example.Lab2_BLPS.service.authorization.ws.converter;

import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;
import org.example.Lab2_BLPS.service.authorization.ws.dto.UserDto;
import org.example.Lab2_BLPS.service.authorization.model.UserEntity;

@Component
public class ConverterUserToUserDto implements Converter<UserEntity, UserDto> {

    @Override
    public UserDto convert(UserEntity source) {
        UserDto res = new UserDto();

        res.setUsername(source.getUsername());
        res.setPassword(source.getPassword());
        return res;
    }
}
