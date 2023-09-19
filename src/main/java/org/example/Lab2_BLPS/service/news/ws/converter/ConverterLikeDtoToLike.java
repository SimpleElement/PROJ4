package org.example.Lab2_BLPS.service.news.ws.converter;

import org.example.Lab2_BLPS.service.news.model.LikeEntity;
import org.example.Lab2_BLPS.service.news.model.NewsEntity;
import org.example.Lab2_BLPS.service.news.ws.dto.LikeDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ConverterLikeDtoToLike implements Converter<LikeDto, LikeEntity> {

    @Override
    public LikeEntity convert(LikeDto source) {
        NewsEntity newsEntity = new NewsEntity();
        newsEntity.setId(source.getNewsId());

        LikeEntity res = new LikeEntity();

        res.setUsername(source.getUsername());
        res.setNews(newsEntity);
        return res;
    }
}
