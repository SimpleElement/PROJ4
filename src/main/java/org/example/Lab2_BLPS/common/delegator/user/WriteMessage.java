package org.example.Lab2_BLPS.common.delegator.user;

import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.example.Lab2_BLPS.common.util.DateUtil;
import org.example.Lab2_BLPS.service.authorization.service.JwtService;
import org.example.Lab2_BLPS.service.authorization.service.repository.UserRepository;
import org.example.Lab2_BLPS.service.mail.model.MessageEntity;
import org.example.Lab2_BLPS.service.mail.service.MailService;
import org.example.Lab2_BLPS.service.mail.service.repository.MessageRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class WriteMessage implements JavaDelegate {

    private final DateUtil dateUtil;

    private final JwtService jwtService;

    private final UserRepository userRepository;

    private final MessageRepository messageRepository;

    @Override
    @SneakyThrows
    public void execute(DelegateExecution execution) {
        String token = (String) execution.getVariable("accessToken");

        boolean canParseToken = true;
        try {
            SignedJWT.parse(token);
        } catch (Exception e) {
            canParseToken = false;
        }

        execution.setVariable("canParseToken", canParseToken);
        if (!canParseToken) {
            execution.setVariable("message", "-");
            execution.setVariable("error", "Ошибка парсинга токена");
            return;
        }

        boolean verifyCheck = SignedJWT.parse(token).verify(new MACVerifier(jwtService.getSecret()));
        execution.setVariable("verifyCheck", verifyCheck);
        if (!verifyCheck) {
            execution.setVariable("message", "-");
            execution.setVariable("error", "RefreshToken не прошёл верефикацию");
            return;
        }

        SignedJWT signedJWT = SignedJWT.parse(token);
        JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
        Date timeCreation = (Date) claimsSet.getClaim("exp");
        long exp = timeCreation.getTime() / 1000;

        boolean isExpiredToken = dateUtil.currentUTCSeconds() >= exp;
        execution.setVariable("isExpiredToken", isExpiredToken);
        if (isExpiredToken) {
            execution.setVariable("message", "-");
            execution.setVariable("error", "Время существования Token'а закончилось");
            return;
        }

        MessageEntity message = new MessageEntity();
        message.setSender((String) jwtService.getClaims(token).get("username"));
        message.setRecipient((String) execution.getVariable("recipient"));
        message.setTopic((String) execution.getVariable("topic"));
        message.setContent((String) execution.getVariable("content"));
        message.setDateOfDispatch(LocalDateTime.now());

        boolean recipientIsExist = userRepository.existsByUsername(message.getRecipient());
        execution.setVariable("recipientIsExist", recipientIsExist);
        if (!recipientIsExist) {
            execution.setVariable("message", "-");
            execution.setVariable("error", "Пользователя, которому вы хотите отправить сообщение, не существует");
            return;
        }

        execution.setVariable("message", messageRepository.save(message));
        execution.setVariable("error", "-");
    }
}
