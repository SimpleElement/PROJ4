package org.example.Lab2_BLPS.service.report.service.repostory;

import org.example.Lab2_BLPS.service.report.model.NewsServiceBanEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface NewsServiceBanRepository extends CrudRepository<NewsServiceBanEntity, Long> {
    boolean existsByUsername(String username);

    NewsServiceBanEntity findByUsername(String username);

    @Modifying
    @Transactional
    @Query("DELETE FROM NewsServiceBanEntity nsb WHERE nsb.username = :username")
    void unbanByUsername(@Param("username") String username);

    @Query("SELECT CASE WHEN count(nsb) > 0 THEN true ELSE false END FROM NewsServiceBanEntity nsb WHERE nsb.unbanDate < CURRENT_DATE()")
    boolean existsUsersByUnban();

    @Query("SELECT nsb.username FROM NewsServiceBanEntity nsb WHERE nsb.unbanDate < CURRENT_DATE()")
    List<String> getUnbanUsername();
}
