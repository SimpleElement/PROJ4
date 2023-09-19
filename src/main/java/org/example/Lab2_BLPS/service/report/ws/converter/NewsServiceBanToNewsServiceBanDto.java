package org.example.Lab2_BLPS.service.report.ws.converter;

import lombok.RequiredArgsConstructor;
import org.example.Lab2_BLPS.service.report.model.NewsServiceBanEntity;
import org.example.Lab2_BLPS.service.report.ws.dto.NewsServiceBanDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewsServiceBanToNewsServiceBanDto implements Converter<NewsServiceBanEntity, NewsServiceBanDto> {

    private final ReportDtoToReport toReportDto;

    @Override
    public NewsServiceBanDto convert(NewsServiceBanEntity source) {
        NewsServiceBanDto res = new NewsServiceBanDto();
        if (source.getReport() != null)
            res.setReport(toReportDto.convert(source.getReport()));

        res.setId(source.getId());
        res.setUsername(source.getUsername());
        res.setUnbanDate(source.getUnbanDate());
        return res;
    }
}
