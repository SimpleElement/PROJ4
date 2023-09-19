package org.example.Lab2_BLPS.service.mail.ws.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.example.Lab2_BLPS.service.mail.ws.validation.constraint.MessageDtoConstraint;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@MessageDtoConstraint
public class MessageDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("recipient")
    private String recipient;

    @JsonProperty("topic")
    private String topic;

    @JsonProperty("content")
    private String content;

    @JsonProperty("sender")
    private String sender;

    @JsonProperty("dateOfDispatch")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateOfDispatch;
}
