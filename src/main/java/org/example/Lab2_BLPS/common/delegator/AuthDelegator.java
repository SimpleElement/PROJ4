package org.example.Lab2_BLPS.common.delegator;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.example.Lab2_BLPS.common.throwable.exception.BadRequestException;
import org.example.Lab2_BLPS.service.authorization.model.Token;
import org.example.Lab2_BLPS.service.authorization.model.UserEntity;
import org.example.Lab2_BLPS.service.authorization.service.AuthorizationServiceCustom;
import org.example.Lab2_BLPS.service.camunda.TokenService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthDelegator implements JavaDelegate {

    private final AuthorizationServiceCustom authService;

    @Override
    public void execute(DelegateExecution execution) {
        try {
            UserEntity user = new UserEntity();

            user.setUsername((String) execution.getVariable("username"));
            user.setPassword((String) execution.getVariable("password"));

            Token token = authService.authorization(user);
            execution.setVariable("accessToken", token.getAccessToken());
            execution.setVariable("refreshToken", token.getRefreshToken());
            execution.setVariable("error", "-");
        } catch (Exception e) {
            execution.setVariable("accessToken", "-");
            execution.setVariable("refreshToken", "-");
            execution.setVariable("error", "Неверный логин или пароль");
        }
    }
}
