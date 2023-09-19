package org.example.Lab2_BLPS.service.authorization.ws.converter;

import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;
import org.example.Lab2_BLPS.service.authorization.model.Token;
import org.example.Lab2_BLPS.service.authorization.ws.dto.TokenDto;

@Component
public class ConverterTokenToTokenDto implements Converter<Token, TokenDto> {

    @Override
    public TokenDto convert(Token source) {
        return TokenDto.builder()
                .accessToken(source.getAccessToken())
                .refreshToken(source.getRefreshToken())
                .build();
    }
}
