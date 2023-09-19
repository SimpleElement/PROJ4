package org.example.Lab2_BLPS.service.news.service.repository;

import org.example.Lab2_BLPS.service.news.enm.StateOfModel;
import org.example.Lab2_BLPS.service.news.model.LikeEntity;
import org.example.Lab2_BLPS.service.news.model.NewsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface LikeRepository extends CrudRepository<LikeEntity, Long> {
    boolean existsLikeByNewsAndUsername(NewsEntity news, String username);

    boolean existsLikeByNewsIdAndUsername(Long newsId, String username);

    @Transactional
    void removeByNewsAndUsername(NewsEntity news, String username);

    @Transactional
    void removeByNewsIdAndUsername(Long newsId, String username);

    Page<LikeEntity> findAllByNewsIdAndStateOfModel(Long newsId, StateOfModel stateOfModel, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE FROM LikeEntity l set l.stateOfModel = :stateOfModel WHERE l.username = :username")
    void updatesLikesModelByUsername(@Param("username") String username, @Param("stateOfModel") StateOfModel stateOfmodel);
}
