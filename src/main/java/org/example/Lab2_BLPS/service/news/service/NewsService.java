package org.example.Lab2_BLPS.service.news.service;

import lombok.RequiredArgsConstructor;
import org.example.Lab2_BLPS.service.mail.model.MessageEntity;
import org.example.Lab2_BLPS.service.news.enm.StateOfModel;
import org.example.Lab2_BLPS.service.news.model.CommentEntity;
import org.example.Lab2_BLPS.service.news.model.LikeEntity;
import org.example.Lab2_BLPS.service.news.model.NewsEntity;
import org.example.Lab2_BLPS.service.news.service.repository.CommentRepository;
import org.example.Lab2_BLPS.service.news.service.repository.LikeRepository;
import org.example.Lab2_BLPS.service.news.service.repository.NewsRepository;
import org.example.Lab2_BLPS.service.news.service.validator.NewsAssert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;

    private final LikeRepository likeRepository;

    private final CommentRepository commentRepository;

    public Page<NewsEntity> getNews(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return newsRepository.findAll(pageable);
    }

    public Page<CommentEntity> getCommentsByNewsId(Integer pageNumber, Integer pageSize, Long newsId) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return commentRepository.findAllByNewsIdAndStateOfModel(newsId, StateOfModel.ACTIVE, pageable);
    }

    public Page<LikeEntity> getLikesByNewsId(Integer pageNumber, Integer pageSize, Long newsId) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return likeRepository.findAllByNewsIdAndStateOfModel(newsId, StateOfModel.ACTIVE, pageable);
    }

    public void likeNews(LikeEntity like) {
        Assert.state(newsRepository.existsById(like.getNews().getId()), "Новости, которой вы хотите поставить лайк, не существует");
        NewsEntity news = newsRepository.findNewsById(like.getNews().getId());

        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (likeRepository.existsLikeByNewsAndUsername(news, username)) {
            likeRepository.removeByNewsAndUsername(news, username);
        } else {
            like = new LikeEntity();
            like.setNews(news);
            like.setUsername(username);
            like.setStateOfModel(StateOfModel.ACTIVE);
            likeRepository.save(like);
        }
    }

    public CommentEntity commentNews(CommentEntity comment) {
        NewsAssert.isNewsState(newsRepository.existsById(comment.getNews().getId()), "Новости, которую вы пытаетесь прокомментировать, не существует");
        NewsEntity news = newsRepository.findNewsById(comment.getNews().getId());

        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        CommentEntity bdComment = new CommentEntity();
        bdComment.setId(null);
        bdComment.setNews(news);
        bdComment.setUsername(username);
        bdComment.setContent(comment.getContent());
        bdComment.setDate(LocalDateTime.now());
        bdComment.setStateOfModel(StateOfModel.ACTIVE);

        return commentRepository.save(bdComment);
    }

    public void frozenCommentsByUsername(String username) {

    }

    public void unfrozenCommentsByUsername(String username) {

    }

    public void frozenLikesByUsername(String username) {

    }

    public void unfrozenLikesByUsername(String username) {

    }
}
