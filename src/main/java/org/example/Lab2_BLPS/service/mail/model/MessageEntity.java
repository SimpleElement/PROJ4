package org.example.Lab2_BLPS.service.mail.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Setter
@Getter
@ToString
@Table(name = "message_ref")
public class MessageEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_seq")
    private Long id;

    @Column(name = "recipient")
    private String recipient;

    @Column(name = "sender")
    private String sender;

    @Column(name = "topic")
    private String topic;

    @Column(name = "content")
    private String content;

    @Column(name = "date_of_dispatch")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateOfDispatch;
}
