package org.example.Lab2_BLPS.service.report.ws.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.example.Lab2_BLPS.service.report.ws.validation.constraint.ReportDtoConstraint;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.text.Format;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@ReportDtoConstraint
public class ReportDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("reportType")
    private String reportType;

    @JsonProperty("reportContentId")
    private Long reportContentId;

    @JsonProperty("reportContent")
    private String reportContent;

    @JsonProperty("suspectUsername")
    private String suspectUsername;

    @JsonProperty("reportResult")
    private String reportResult;

    @JsonProperty("dateOfCreate")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateOfCreate;

    @JsonProperty("unbanDate")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime unbanDate;
}
