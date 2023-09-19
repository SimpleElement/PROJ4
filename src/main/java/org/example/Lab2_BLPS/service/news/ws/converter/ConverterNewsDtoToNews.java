package org.example.Lab2_BLPS.service.news.ws.converter;

import org.example.Lab2_BLPS.service.news.model.NewsEntity;
import org.example.Lab2_BLPS.service.news.ws.dto.NewsDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ConverterNewsDtoToNews implements Converter<NewsDto, NewsEntity> {

    @Override
    public NewsEntity convert(NewsDto source) {
        NewsEntity res = new NewsEntity();

        res.setId(source.getId());
        res.setAuthor(source.getAuthor());
        res.setTopic(source.getTopic());
        res.setDescription(source.getDescription());
        res.setData(source.getData());
        return res;
    }
}
