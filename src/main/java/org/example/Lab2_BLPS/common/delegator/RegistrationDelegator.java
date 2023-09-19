package org.example.Lab2_BLPS.common.delegator;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.example.Lab2_BLPS.common.throwable.exception.BadRequestException;
import org.example.Lab2_BLPS.service.authorization.enm.RoleOfUser;
import org.example.Lab2_BLPS.service.authorization.model.Token;
import org.example.Lab2_BLPS.service.authorization.model.UserEntity;
import org.example.Lab2_BLPS.service.authorization.service.AuthorizationServiceCustom;
import org.example.Lab2_BLPS.service.camunda.TokenService;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class RegistrationDelegator implements JavaDelegate {

    private final AuthorizationServiceCustom authService;

    @Override
    public void execute(DelegateExecution execution) {
        try {
            UserEntity user = new UserEntity();

            user.setUsername((String) execution.getVariable("username"));
            user.setPassword((String) execution.getVariable("password"));
            user.setRoleOfUser(Arrays.stream(RoleOfUser.values()).filter(el -> el.name().equals(execution.getVariable("role"))).findAny().get());

            Token token = authService.registrationWithRole(user);
            execution.setVariable("accessToken", token.getAccessToken());
            execution.setVariable("refreshToken", token.getRefreshToken());
            execution.setVariable("error", "-");
        } catch (Exception e) {
            execution.setVariable("accessToken", "-");
            execution.setVariable("refreshToken", "-");
            execution.setVariable("error", "Пользователь с данныим именем уже существует");
        }
    }
}
