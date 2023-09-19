package org.example.Lab2_BLPS.service.news.ws.converter;

import lombok.RequiredArgsConstructor;
import org.example.Lab2_BLPS.service.news.model.NewsEntity;
import org.example.Lab2_BLPS.service.news.ws.dto.NewsDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConverterNewsToNewsDto implements Converter<NewsEntity, NewsDto> {

    @Override
    public NewsDto convert(NewsEntity source) {
        NewsDto res = new NewsDto();

        res.setId(source.getId());
        res.setAuthor(source.getAuthor());
        res.setTopic(source.getTopic());
        res.setDescription(source.getDescription());
        res.setData(source.getData());
        return res;
    }
}
