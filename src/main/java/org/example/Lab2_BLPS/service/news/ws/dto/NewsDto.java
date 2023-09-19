package org.example.Lab2_BLPS.service.news.ws.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.example.Lab2_BLPS.service.news.ws.validation.constraint.NewsDtoConstraint;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NewsDtoConstraint
public class NewsDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("author")
    private String author;

    @JsonProperty("topic")
    private String topic;

    @JsonProperty("description")
    private String description;

    @JsonProperty("data")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime data;
}
