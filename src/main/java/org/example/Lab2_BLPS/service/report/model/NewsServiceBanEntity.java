package org.example.Lab2_BLPS.service.report.model;

import lombok.Getter;
import lombok.Setter;
import org.example.Lab2_BLPS.service.news.model.NewsEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "new_service_ban_ref")
public class NewsServiceBanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "new_service_ban_seq")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "unban_date")
    private LocalDateTime unbanDate;

    @OneToOne(optional = false)
    @JoinColumn(name = "report_id")
    private ReportEntity report;
}
