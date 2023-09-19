package org.example.Lab2_BLPS.service.news.service.repository;

import org.example.Lab2_BLPS.service.news.enm.StateOfModel;
import org.example.Lab2_BLPS.service.news.model.CommentEntity;
import org.example.Lab2_BLPS.service.news.model.LikeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface CommentRepository extends CrudRepository<CommentEntity, Long> {
    boolean existsByIdAndUsername(Long id, String username);

    Page<CommentEntity> findAllByNewsIdAndStateOfModel(Long newsId, StateOfModel stateOfModel, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE FROM CommentEntity c set c.stateOfModel = :stateOfmodel WHERE c.username = :username")
    void updatesCommentsModelByUsername(@Param("username") String username, @Param("stateOfmodel") StateOfModel stateOfmodel);
}
