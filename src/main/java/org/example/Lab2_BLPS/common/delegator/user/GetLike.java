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
import org.example.Lab2_BLPS.service.mail.service.MailService;
import org.example.Lab2_BLPS.service.news.enm.StateOfModel;
import org.example.Lab2_BLPS.service.news.model.LikeEntity;
import org.example.Lab2_BLPS.service.news.model.NewsEntity;
import org.example.Lab2_BLPS.service.news.service.NewsService;
import org.example.Lab2_BLPS.service.news.service.repository.LikeRepository;
import org.example.Lab2_BLPS.service.news.service.repository.NewsRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class GetLike implements JavaDelegate {

    private final DateUtil dateUtil;

    private final JwtService jwtService;

    private final LikeRepository likeRepository;

    private final NewsRepository newsRepository;

    @Override
    @SneakyThrows
    public void execute(DelegateExecution execution) throws Exception {
        String token = (String) execution.getVariable("accessToken");

        boolean canParseToken = true;
        try {
            SignedJWT.parse(token);
        } catch (Exception e) {
            canParseToken = false;
        }

        execution.setVariable("canParseToken", canParseToken);
        if (!canParseToken) {
            execution.setVariable("like", "-");
            execution.setVariable("error", "Ошибка парсинга токена");
            return;
        }

        boolean verifyCheck = SignedJWT.parse(token).verify(new MACVerifier(jwtService.getSecret()));
        execution.setVariable("verifyCheck", verifyCheck);
        if (!verifyCheck) {
            execution.setVariable("like", "-");
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
            execution.setVariable("like", "-");
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
            execution.setVariable("like", "-");
            execution.setVariable("error", "Ошибка формата поля NewsId");
            return;
        }

        long newsId = Long.parseLong((String) execution.getVariable("newsId"));

        boolean thisNewsExist = newsRepository.existsById(newsId);
        execution.setVariable("thisNewsExist", thisNewsExist);
        if (!thisNewsExist) {
            execution.setVariable("like", "-");
            execution.setVariable("error", "Новости с данным id не существует");
            return;
        }

        int pageNumber = Integer.parseInt((String) execution.getVariable("pageNumber"));
        int pageSize = Integer.parseInt((String) execution.getVariable("pageSize"));

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        execution.setVariable("like",
                Arrays.toString(
                        likeRepository.findAllByNewsIdAndStateOfModel(newsId, StateOfModel.ACTIVE, pageable)
                                .stream()
                                .map(LikeEntity::getUsername)
                                .toArray(String[]::new)
                )
        );
        execution.setVariable("error", "-");
    }
}
