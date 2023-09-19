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
import org.example.Lab2_BLPS.service.news.enm.StateOfModel;
import org.example.Lab2_BLPS.service.news.model.CommentEntity;
import org.example.Lab2_BLPS.service.news.service.repository.CommentRepository;
import org.example.Lab2_BLPS.service.news.service.repository.NewsRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class CommentNews implements JavaDelegate {

    private final DateUtil dateUtil;

    private final JwtService jwtService;

    private final NewsRepository newsRepository;

    private final CommentRepository commentRepository;

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
            execution.setVariable("comment", "-");
            execution.setVariable("error", "Ошибка парсинга токена");
            return;
        }

        boolean verifyCheck = SignedJWT.parse(token).verify(new MACVerifier(jwtService.getSecret()));
        execution.setVariable("verifyCheck", verifyCheck);
        if (!verifyCheck) {
            execution.setVariable("comment", "-");
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
            execution.setVariable("comment", "-");
            execution.setVariable("error", "Время существования Token'а закончилось");
            return;
        }

        boolean canParseNewsId = true;
        try {
            Long.parseLong((String) execution.getVariable("newsId"));
        } catch (Exception e) {
            canParseNewsId = false;
        }

        execution.setVariable("canParseNewsId", canParseNewsId);
        if (!canParseNewsId) {
            execution.setVariable("comment", "-");
            execution.setVariable("error", "Ошибка формата поля NewsId");
            return;
        }

        long newsId = Long.parseLong((String) execution.getVariable("newsId"));

        boolean thisNewsExist = newsRepository.existsById(newsId);
        execution.setVariable("thisNewsExist", thisNewsExist);
        if (!thisNewsExist) {
            execution.setVariable("comment", "-");
            execution.setVariable("error", "Новости с данным id не существует");
            return;
        }

        CommentEntity comment = new CommentEntity();
        comment.setUsername((String) jwtService.getClaims(token).get("username"));
        comment.setContent((String) execution.getVariable("comment"));
        comment.setNews(newsRepository.findNewsById(newsId));
        comment.setDate(LocalDateTime.now());
        comment.setStateOfModel(StateOfModel.ACTIVE);

        execution.setVariable("comment", commentRepository.save(comment).toString());
        execution.setVariable("error", "-");
    }
}
