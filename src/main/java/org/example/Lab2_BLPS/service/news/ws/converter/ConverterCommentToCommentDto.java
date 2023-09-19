package org.example.Lab2_BLPS.service.news.ws.converter;

import org.example.Lab2_BLPS.service.news.model.CommentEntity;
import org.example.Lab2_BLPS.service.news.ws.dto.CommentDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ConverterCommentToCommentDto implements Converter<CommentEntity, CommentDto> {

    @Override
    public CommentDto convert(CommentEntity source) {
        CommentDto res = new CommentDto();

        res.setId(source.getId());
        res.setUsername(source.getUsername());
        res.setContent(source.getContent());
        res.setDate(source.getDate());
        res.setNewsId(source.getNews().getId());
        return res;
    }
}
