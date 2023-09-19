package org.example.Lab2_BLPS.service.report.service;

import lombok.RequiredArgsConstructor;
import org.example.Lab2_BLPS.service.authorization.service.repository.UserRepository;
import org.example.Lab2_BLPS.service.news.enm.StateOfModel;
import org.example.Lab2_BLPS.service.news.service.repository.CommentRepository;
import org.example.Lab2_BLPS.service.news.service.repository.LikeRepository;
import org.example.Lab2_BLPS.service.report.enm.ReportResult;
import org.example.Lab2_BLPS.service.report.model.NewsServiceBanEntity;
import org.example.Lab2_BLPS.service.report.model.ReportEntity;
import org.example.Lab2_BLPS.service.report.service.repostory.NewsServiceBanRepository;
import org.example.Lab2_BLPS.service.report.service.repostory.ReportRepository;
import org.example.Lab2_BLPS.service.report.service.validator.ReportAssert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final UserRepository userRepository;

    private final LikeRepository likeRepository;

    private final ReportRepository reportRepository;

    private final CommentRepository commentRepository;

    private final NewsServiceBanRepository newsServiceBanRepository;

    public ReportEntity sendReport(ReportEntity report) {
        ReportAssert.isUserExists(userRepository.existsByUsername(report.getSuspectUsername()), "Пользователь с данным именем не найден");

        switch (report.getReportType()) {
            case COMMENT:
                ReportAssert.isCommentExists(commentRepository.existsById(report.getReportContentId()), "Коментарий с данным id не найден");
                ReportAssert.isCommentOfUser(
                        commentRepository.existsByIdAndUsername(report.getReportContentId(), report.getSuspectUsername()),
                        "Данный коментарий не пренадлежит заявленному пользователю"
                );
                report.setReportContent(commentRepository.findById(report.getReportContentId()).get().getContent());
        }

        report.setId(null);
        report.setReportResult(ReportResult.EXPECTATION);
        report.setDateOfCreate(LocalDateTime.now());
        return reportRepository.save(report);
    }

    public Page<ReportEntity> getReports(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return reportRepository.findByModeratorUsernameIsNullAndReportResultIs(ReportResult.EXPECTATION, pageable);
    }

    public ReportEntity moderateReport(Long reportId) {
        ReportAssert.reportIdIsNonNull(reportId, "Не указан reportId репорта");

        Optional<ReportEntity> reportOp = reportRepository.findById(reportId);

        ReportAssert.reportReadyToModerate(reportOp.isPresent(), "Репорт по данному reportId не найден");

        ReportEntity report = reportOp.get();

        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (reportRepository.existsByModeratorUsernameAndReportResult(username, ReportResult.EXPECTATION))
            return reportRepository.findByModeratorUsernameAndReportResult(username, ReportResult.EXPECTATION);

        int countModifying = reportRepository.moderateReportById(report.getId(), username);
        ReportAssert.countModifyingNotZero(countModifying, "Выбранная вами заявка уже обработана, попробуйте взять другую");

        return reportRepository.findByModeratorUsernameAndReportResult(username, ReportResult.EXPECTATION);
    }

    public ReportEntity skipReport(Long reportId) {
        ReportAssert.reportIdIsNonNull(reportId, "Не указан reportId репорта");

        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        ReportAssert.reportReadyToModerate(
                reportRepository.existsByIdAndModeratorUsernameAndReportResult(reportId, username, ReportResult.EXPECTATION),
                "Данный reportId не модерируется вами");

        Optional<ReportEntity> reportOp = reportRepository.findById(reportId);

        ReportAssert.reportReadyToModerate(reportOp.isPresent(), "Репорт по данному reportId не найден");

        ReportEntity report = reportOp.get();

        reportRepository.updateReportResultBy(report.getReportContentId(), report.getReportType(), report.getSuspectUsername(), ReportResult.SKIP);
        return reportRepository.findById(reportId).get();
    }

    @Transactional
    public NewsServiceBanEntity acceptReport(NewsServiceBanEntity newsServiceBan) {
        ReportAssert.reportIsNonNull(newsServiceBan.getReport(), "Не указан Report");
        ReportAssert.reportIdIsNonNull(newsServiceBan.getReport().getId(), "Не указан id репорта");

        Optional<ReportEntity> reportOp = reportRepository.findById(newsServiceBan.getReport().getId());

        ReportAssert.reportReadyToModerate(reportOp.isPresent(), "Репорт по данному reportId не найден");

        ReportEntity report = reportOp.get();

        ReportAssert.unbanDateNonNull(newsServiceBan.getUnbanDate(), "Не указана дата разбана");

        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        ReportAssert.reportReadyToModerate(
                reportRepository.existsByIdAndModeratorUsernameAndReportResult(report.getId(), username, ReportResult.EXPECTATION),
                "Данный reportId не модерируется вами");

        likeRepository.updatesLikesModelByUsername(report.getSuspectUsername(), StateOfModel.FROZEN);
        commentRepository.updatesCommentsModelByUsername(report.getSuspectUsername(), StateOfModel.FROZEN);

        reportRepository.updateReportResultById(report.getId(), username, ReportResult.ACCEPT);
        ReportEntity reportFromDb = reportRepository.findById(report.getId()).get();

        newsServiceBan.setId(null);
        newsServiceBan.setUsername(report.getSuspectUsername());
        newsServiceBan.setReport(reportFromDb);
        newsServiceBanRepository.save(newsServiceBan);
        return newsServiceBanRepository.findById(newsServiceBan.getId()).get();
    }

    @Transactional
    public void unbanUserJob() {
        if (haveUsersNeedUnban()) {
            List<String> usernames = newsServiceBanRepository.getUnbanUsername();
            for (String username: usernames) {
                likeRepository.updatesLikesModelByUsername(username, StateOfModel.ACTIVE);
                commentRepository.updatesCommentsModelByUsername(username, StateOfModel.ACTIVE);
                commentRepository.deleteById(newsServiceBanRepository.findByUsername(username).getReport().getReportContentId());
                newsServiceBanRepository.unbanByUsername(username);
            }
        }
    }

    public boolean userHaveBanFromNewsService(String username) {
        return newsServiceBanRepository.existsByUsername(username);
    }

    public NewsServiceBanEntity getNewsServiceBanEntityByUsername(String username) {
        return newsServiceBanRepository.findByUsername(username);
    }

    public boolean haveUsersNeedUnban() {
        return newsServiceBanRepository.existsUsersByUnban();
    }

    public List<String> getUnbanUsernames() {
        return newsServiceBanRepository.getUnbanUsername();
    }
}
