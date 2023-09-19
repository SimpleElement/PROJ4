package org.example.Lab2_BLPS.service.mail.service;

import lombok.RequiredArgsConstructor;
import org.example.Lab2_BLPS.service.authorization.service.repository.UserRepository;
import org.example.Lab2_BLPS.service.mail.model.MessageEntity;
import org.example.Lab2_BLPS.service.mail.service.kafka.CensorKafkaProducer;
import org.example.Lab2_BLPS.service.mail.service.repository.MessageRepository;
import org.example.Lab2_BLPS.service.mail.service.validator.MailAssert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MailService {

    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final CensorKafkaProducer censorKafkaProducer;

    // Message
    private static final String RECIPIENT_NOT_FOUND = "Получателя с таким username не найдено";

    public Page<MessageEntity> getMessages(Integer pageNumber, Integer pageSize) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return messageRepository.findByRecipient(username, pageable);
    }

    public Page<MessageEntity> getMessagesByUsername(String username, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return messageRepository.findByRecipient(username, pageable);
    }

    public MessageEntity writeMessage(MessageEntity messageEntity) {
        MailAssert.isUserExists(userRepository.existsByUsername(messageEntity.getRecipient()), RECIPIENT_NOT_FOUND);
        String sender = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        messageEntity.setId(null);
        messageEntity.setSender(sender);
        messageEntity.setDateOfDispatch(LocalDateTime.now());
        MessageEntity messageEntityDb = messageRepository.save(messageEntity);

        censorKafkaProducer.sendForCensor(messageEntityDb);
        return messageEntityDb;
    }

    public void spamNewUsers() {
        List<String> usernames = messageRepository.findAllNewUsernames();
        for (String username: usernames) {
            MessageEntity message = new MessageEntity();

            message.setRecipient(username);
            message.setSender("Help.PTA");
            message.setTopic("Вы не отправили ещё ни одного сообщения");
            message.setContent("Наша почта топ, попробуйте");
            message.setDateOfDispatch(LocalDateTime.now());
            messageRepository.save(message);
        }
    }
}
