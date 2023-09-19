package org.example.Lab2_BLPS.service.report.ws.converter;

import org.example.Lab2_BLPS.service.report.model.ReportEntity;
import org.example.Lab2_BLPS.service.report.ws.dto.ReportDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ReportDtoToReport implements Converter<ReportEntity, ReportDto> {
    @Override
    public ReportDto convert(ReportEntity source) {
        ReportDto res = new ReportDto();

        res.setId(source.getId());
        res.setReportType(source.getReportType().name());
        res.setReportContentId(source.getReportContentId());
        res.setReportContent(source.getReportContent());
        res.setSuspectUsername(source.getSuspectUsername());
        res.setReportResult(source.getReportResult().name());
        res.setDateOfCreate(source.getDateOfCreate());
        return res;
    }
}
