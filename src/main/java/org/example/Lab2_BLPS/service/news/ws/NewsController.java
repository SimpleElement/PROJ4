package org.example.Lab2_BLPS.service.news.ws;

import lombok.RequiredArgsConstructor;
import org.example.Lab2_BLPS.common.object.page.PageResponse;
import org.example.Lab2_BLPS.service.news.model.CommentEntity;
import org.example.Lab2_BLPS.service.news.model.LikeEntity;
import org.example.Lab2_BLPS.service.news.model.NewsEntity;
import org.example.Lab2_BLPS.service.news.service.NewsService;
import org.example.Lab2_BLPS.service.news.ws.dto.CommentDto;
import org.example.Lab2_BLPS.service.news.ws.dto.LikeDto;
import org.example.Lab2_BLPS.service.news.ws.dto.NewsDto;
import org.example.Lab2_BLPS.service.news.ws.filter.CommentFilter;
import org.example.Lab2_BLPS.service.news.ws.filter.LikeFilter;
import org.example.Lab2_BLPS.service.news.ws.filter.NewsFilter;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    private final ConversionService conversionService;

    @GetMapping(path = "/getNews")
    public PageResponse<NewsDto> getMessages(@Valid NewsFilter filter) {
        Page<NewsEntity> page = newsService.getNews(filter.getPageNumber(), filter.getPageSize());

        PageResponse<NewsDto> response = conversionService.convert(page, PageResponse.class);
        response.setContent(
                page.getContent().stream()
                        .map(newsEntity -> conversionService.convert(newsEntity, NewsDto.class))
                        .collect(Collectors.toList())
        );
        response.setTotalElements(page.getTotalElements());
        return response;
    }

    @GetMapping(path = "/getComments")
    public PageResponse<CommentDto> getCommentsByNewsId(@Valid CommentFilter filter) {
        Page<CommentEntity> page = newsService.getCommentsByNewsId(filter.getPageNumber(), filter.getPageSize(), filter.getNewsId());

        PageResponse<CommentDto> response = conversionService.convert(page, PageResponse.class);
        response.setContent(
                page.getContent().stream()
                        .map(commentEntity -> conversionService.convert(commentEntity, CommentDto.class))
                        .collect(Collectors.toList())
        );
        response.setTotalElements(page.getTotalElements());
        return response;
    }

    @GetMapping(path = "/getLikes")
    public PageResponse<LikeDto> getLikesByNewsId(@Valid LikeFilter filter) {
        Page<LikeEntity> page = newsService.getLikesByNewsId(filter.getPageNumber(), filter.getPageSize(), filter.getNewsId());

        PageResponse<LikeDto> response = conversionService.convert(page, PageResponse.class);
        response.setContent(
                page.getContent().stream()
                        .map(likeEntity -> conversionService.convert(likeEntity, LikeDto.class))
                        .collect(Collectors.toList())
        );
        response.setTotalElements(page.getTotalElements());
        return response;
    }

    @PostMapping(path = "/likeNews")
    public void likeNews(@Valid @RequestBody LikeDto like) {
        newsService.likeNews(conversionService.convert(like, LikeEntity.class));
    }

    @PostMapping(path = "/commentNews")
    public CommentDto commentNews(@Valid @RequestBody CommentDto comment) {
        return conversionService.convert(
                newsService.commentNews(conversionService.convert(comment, CommentEntity.class)),
                CommentDto.class
        );
    }
}
