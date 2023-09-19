package org.example.Lab2_BLPS.service.report.ws.converter;

import org.example.Lab2_BLPS.service.report.model.NewsServiceBanEntity;
import org.example.Lab2_BLPS.service.report.model.ReportEntity;
import org.example.Lab2_BLPS.service.report.ws.dto.NewsServiceBanDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class NewsServiceBanDtoToNewsServiceBan implements Converter<NewsServiceBanDto, NewsServiceBanEntity> {

    @Override
    public NewsServiceBanEntity convert(NewsServiceBanDto source) {
        NewsServiceBanEntity res = new NewsServiceBanEntity();
        res.setUnbanDate(source.getUnbanDate());

        if (source.getReport() != null) {
            ReportEntity rep = new ReportEntity();
            rep.setId(source.getReport().getId());
            res.setReport(rep);
        }
        return res;
    }
}
