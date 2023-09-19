package org.example.Lab2_BLPS.service.news.ws.converter;

import org.example.Lab2_BLPS.service.news.model.LikeEntity;
import org.example.Lab2_BLPS.service.news.ws.dto.LikeDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ConverterLikeToLikeDto implements Converter<LikeEntity, LikeDto> {

    @Override
    public LikeDto convert(LikeEntity source) {
        LikeDto res = new LikeDto();

        res.setUsername(source.getUsername());
        res.setNewsId(source.getNews().getId());
        return res;
    }
}
