package org.example.Lab2_BLPS.service.news.ws.converter;

import org.example.Lab2_BLPS.service.news.model.CommentEntity;
import org.example.Lab2_BLPS.service.news.model.NewsEntity;
import org.example.Lab2_BLPS.service.news.ws.dto.CommentDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ConverterCommentDtoToComment implements Converter<CommentDto, CommentEntity> {

    @Override
    public CommentEntity convert(CommentDto source) {
        NewsEntity newsEntity = new NewsEntity();
        newsEntity.setId(source.getNewsId());

        CommentEntity res = new CommentEntity();

        res.setId(source.getId());
        res.setUsername(source.getUsername());
        res.setContent(source.getContent());
        res.setDate(source.getDate());
        res.setNews(newsEntity);
        return res;
    }
}
