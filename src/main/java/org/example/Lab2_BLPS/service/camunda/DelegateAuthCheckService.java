package org.example.Lab2_BLPS.service.camunda;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.example.Lab2_BLPS.common.throwable.exception.NonAuthoritativeInformationException;
import org.example.Lab2_BLPS.service.authorization.enm.RoleOfUser;
import org.example.Lab2_BLPS.service.authorization.model.Token;
import org.example.Lab2_BLPS.service.authorization.service.JwtService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class DelegateAuthCheckService {

    private final JwtService jwtService;

    @SneakyThrows
    public void isUser(DelegateExecution execution) {
        String userId = execution.getProcessEngineServices().getIdentityService().getCurrentAuthentication().getUserId();

        Token token = TokenService.getUserToken(userId);
        jwtService.isExpiredAccessToken(token.getAccessToken());

        Map<String, Object> clams = jwtService.getClaims(token.getAccessToken());
        if (!RoleOfUser.USER.name().equals(clams.get("role")))
            throw new NonAuthoritativeInformationException("Ошибка роли");
    }

    @SneakyThrows
    public void isModerator(DelegateExecution execution) {
        String userId = execution.getProcessEngineServices().getIdentityService().getCurrentAuthentication().getUserId();

        Token token = TokenService.getUserToken(userId);
        jwtService.isExpiredAccessToken(token.getAccessToken());

        Map<String, Object> clams = jwtService.getClaims(token.getAccessToken());
        if (!RoleOfUser.MODERATOR.name().equals(clams.get("role")))
            throw new NonAuthoritativeInformationException("Ошибка роли");
    }
}
