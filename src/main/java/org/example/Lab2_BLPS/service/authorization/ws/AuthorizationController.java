package org.example.Lab2_BLPS.service.authorization.ws;

import lombok.RequiredArgsConstructor;
import org.example.Lab2_BLPS.service.authorization.model.Token;
import org.example.Lab2_BLPS.service.authorization.model.UserEntity;
import org.example.Lab2_BLPS.service.authorization.ws.dto.TokenDto;
import org.example.Lab2_BLPS.service.authorization.ws.dto.UserDto;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.example.Lab2_BLPS.service.authorization.service.AuthorizationServiceCustom;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth/")
@RequiredArgsConstructor
public class AuthorizationController {

    private final ConversionService conversionService;

    private final AuthorizationServiceCustom authorizationServiceCustom;

    @PostMapping("/registration")
    public TokenDto registration(@Valid @RequestBody UserDto userDto) {
        return conversionService.convert(
                authorizationServiceCustom.registration(
                        conversionService.convert(userDto, UserEntity.class)
                ), TokenDto.class
        );
    }

    @PostMapping("/authorization")
    public TokenDto authorization(@Valid @RequestBody UserDto userDto) {
        return conversionService.convert(
                authorizationServiceCustom.authorization(
                        conversionService.convert(userDto, UserEntity.class)
                ), TokenDto.class
        );
    }

    @PostMapping("/refreshToken")
    public TokenDto refreshToken(@RequestBody TokenDto tokenDto) {
        return conversionService.convert(
                authorizationServiceCustom.refreshToken(
                        conversionService.convert(tokenDto, Token.class)
                ), TokenDto.class
        );
    }

    @PostMapping("/forgottenRefreshKey")
    public void forgottenRefreshKey(@RequestBody TokenDto tokenDto) {
        authorizationServiceCustom.exit(conversionService.convert(tokenDto, Token.class));
    }
}
