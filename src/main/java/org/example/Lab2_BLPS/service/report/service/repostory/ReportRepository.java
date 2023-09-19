package org.example.Lab2_BLPS.service.report.service.repostory;

import org.example.Lab2_BLPS.service.report.enm.ReportResult;
import org.example.Lab2_BLPS.service.report.enm.ReportType;
import org.example.Lab2_BLPS.service.report.model.ReportEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface ReportRepository extends CrudRepository<ReportEntity, Long> {
    Page<ReportEntity> findByModeratorUsernameIsNullAndReportResultIs(ReportResult reportResult, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE FROM ReportEntity r SET r.moderatorUsername = :username WHERE r.id = :id AND r.moderatorUsername is null")
    int moderateReportById(@Param("id") Long id, @Param("username") String username);

    boolean existsByModeratorUsernameAndReportResult(String username, ReportResult reportResult);

    ReportEntity findByModeratorUsernameAndReportResult(String username, ReportResult reportResult);

    boolean existsByIdAndModeratorUsernameAndReportResult(Long id, String username, ReportResult reportResult);

    @Modifying
    @Transactional
    @Query("UPDATE FROM ReportEntity r SET r.reportResult = :reportResult WHERE r.id = :id AND r.moderatorUsername = :username")
    void updateReportResultById(@Param("id") Long id, @Param("username") String username,@Param("reportResult") ReportResult reportResult);

    @Modifying
    @Transactional
    @Query("UPDATE FROM ReportEntity r SET r.reportResult = :reportResult WHERE r.reportContentId = :reportContentId AND r.reportType = :reportType AND r.suspectUsername = :suspectUsername")
    void updateReportResultBy(@Param("reportContentId") Long reportContentId, @Param("reportType") ReportType reportType, @Param("suspectUsername") String suspectUsername, @Param("reportResult") ReportResult reportResult);
}
