package org.example.Lab2_BLPS.service.authorization.ws.converter;

import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;
import org.example.Lab2_BLPS.service.authorization.model.Token;
import org.example.Lab2_BLPS.service.authorization.ws.dto.TokenDto;

@Component
public class ConverterTokenDtoToToken implements Converter<TokenDto, Token> {

    @Override
    public Token convert(TokenDto source) {
        Token res = new Token();

        res.setAccessToken(source.getAccessToken());
        res.setRefreshToken(source.getRefreshToken());
        return res;
    }
}
