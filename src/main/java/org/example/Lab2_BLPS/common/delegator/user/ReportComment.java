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
import org.example.Lab2_BLPS.service.news.service.repository.CommentRepository;
import org.example.Lab2_BLPS.service.news.service.repository.NewsRepository;
import org.example.Lab2_BLPS.service.report.enm.ReportResult;
import org.example.Lab2_BLPS.service.report.enm.ReportType;
import org.example.Lab2_BLPS.service.report.model.ReportEntity;
import org.example.Lab2_BLPS.service.report.service.repostory.ReportRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class ReportComment implements JavaDelegate {

    private final DateUtil dateUtil;

    private final JwtService jwtService;

    private final NewsRepository newsRepository;

    private final ReportRepository reportRepository;

    private final CommentRepository commentRepository;

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
            execution.setVariable("report", "-");
            execution.setVariable("error", "Ошибка парсинга токена");
            return;
        }

        boolean verifyCheck = SignedJWT.parse(token).verify(new MACVerifier(jwtService.getSecret()));
        execution.setVariable("verifyCheck", verifyCheck);
        if (!verifyCheck) {
            execution.setVariable("report", "-");
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
            execution.setVariable("report", "-");
            execution.setVariable("error", "Время существования Token'а закончилось");
            return;
        }

        boolean canParseNewsId = true;
        try {
            Long.parseLong((String) execution.getVariable("commentId"));
        } catch (Exception e) {
            canParseNewsId = false;
        }

        execution.setVariable("canParseNewsId", canParseNewsId);
        if (!canParseNewsId) {
            execution.setVariable("report", "-");
            execution.setVariable("error", "Ошибка формата поля commentId");
            return;
        }

        long commentId = Long.parseLong((String) execution.getVariable("commentId"));

        boolean thisNewsExist = commentRepository.existsById(commentId);
        execution.setVariable("thisNewsExist", thisNewsExist);
        if (!thisNewsExist) {
            execution.setVariable("report", "-");
            execution.setVariable("error", "Комментария с данным id не существует");
            return;
        }

        ReportEntity report = new ReportEntity();
        report.setId(null);
        report.setReportType(ReportType.COMMENT);
        report.setSuspectUsername(commentRepository.findById(commentId).get().getUsername());
        report.setReportResult(ReportResult.EXPECTATION);
        report.setDateOfCreate(LocalDateTime.now());
        report.setReportContent(commentRepository.findById(commentId).get().getContent());

        execution.setVariable("report", reportRepository.save(report));
        execution.setVariable("error", "-");
    }
}