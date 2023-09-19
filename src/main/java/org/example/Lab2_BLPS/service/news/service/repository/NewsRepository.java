package org.example.Lab2_BLPS.service.news.service.repository;

import org.example.Lab2_BLPS.service.mail.model.MessageEntity;
import org.example.Lab2_BLPS.service.news.model.NewsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface NewsRepository extends CrudRepository<NewsEntity, Long> {
    boolean existsById(Long id);
    NewsEntity findNewsById(Long id);
    Page<NewsEntity> findAll(Pageable pageable);
}
