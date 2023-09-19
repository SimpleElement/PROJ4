package org.example.Lab2_BLPS.service.report.ws.converter;

import lombok.SneakyThrows;
import org.example.Lab2_BLPS.service.report.enm.ReportResult;
import org.example.Lab2_BLPS.service.report.enm.ReportType;
import org.example.Lab2_BLPS.service.report.model.NewsServiceBanEntity;
import org.example.Lab2_BLPS.service.report.model.ReportEntity;
import org.example.Lab2_BLPS.service.report.ws.dto.ReportDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@Component
public class ReportToReportDto implements Converter<ReportDto, ReportEntity> {
    @SneakyThrows
    @Override
    public ReportEntity convert(ReportDto source) {
        ReportEntity res = new ReportEntity();

        res.setId(source.getId());
        res.setReportType(Arrays.stream(ReportType.values()).filter(reportType -> reportType.name().equals(source.getReportType())).findFirst().orElse(null));
        res.setReportContentId(source.getReportContentId());
        res.setReportContent(source.getReportContent());
        res.setSuspectUsername(source.getSuspectUsername());
        res.setReportResult(Arrays.stream(ReportResult.values()).filter(reportResult -> reportResult.name().equals(source.getReportType())).findFirst().orElse(null));
        res.setDateOfCreate(source.getDateOfCreate());

        if (Objects.nonNull(source.getUnbanDate())) {
            NewsServiceBanEntity newsServiceBan = new NewsServiceBanEntity();
            newsServiceBan.setUnbanDate(source.getUnbanDate());
            res.setNewsServiceBanEntity(newsServiceBan);
        }
        return res;
    }
}
