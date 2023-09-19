package org.example.Lab2_BLPS.service.mail.service.repository;

import org.example.Lab2_BLPS.service.mail.model.MessageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface MessageRepository extends CrudRepository<MessageEntity, Long> {

    Page<MessageEntity> findByRecipient(String recipient, Pageable pageable);

    @Query("SELECT u.username FROM MessageEntity m RIGHT JOIN UserEntity u ON m.sender = u.username WHERE m.sender IS NULL")
    List<String> findAllNewUsernames();
}
