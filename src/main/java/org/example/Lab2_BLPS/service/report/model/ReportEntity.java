package org.example.Lab2_BLPS.service.report.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.Lab2_BLPS.service.news.model.CommentEntity;
import org.example.Lab2_BLPS.service.report.enm.ReportResult;
import org.example.Lab2_BLPS.service.report.enm.ReportType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Table(name = "report_ref")
public class ReportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "report_seq")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "report_type")
    private ReportType reportType;

    @Column(name = "report_content_id")
    private Long reportContentId;

    @Column(name = "report_content")
    private String reportContent;

    @Column(name = "suspect_username")
    private String suspectUsername;

    @Enumerated(EnumType.STRING)
    @Column(name = "report_result")
    private ReportResult reportResult;

    @Column(name = "moderator")
    private String moderatorUsername;

    @Column(name = "date_of_create")
    private LocalDateTime dateOfCreate;

    @OneToOne(mappedBy = "report", fetch=FetchType.LAZY)
    private NewsServiceBanEntity newsServiceBanEntity;
}
