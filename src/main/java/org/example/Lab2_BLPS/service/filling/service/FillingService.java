package org.example.Lab2_BLPS.service.filling.service;

import lombok.RequiredArgsConstructor;
import org.example.Lab2_BLPS.service.news.model.NewsEntity;
import org.example.Lab2_BLPS.service.news.service.repository.NewsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class FillingService {

    private final NewsRepository newsRepository;

    public void fillingDb() {
        fillingNews();
    }

    public void fillingNews() {
        NewsEntity news = new NewsEntity();
        news.setAuthor("Something author");
        news.setTopic("Something Topic");
        news.setDescription("Something Description");
        news.setData(LocalDateTime.now());

        newsRepository.save(news);
    }
}
