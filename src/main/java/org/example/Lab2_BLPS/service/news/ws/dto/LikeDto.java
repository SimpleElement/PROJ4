package org.example.Lab2_BLPS.service.news.ws.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.example.Lab2_BLPS.service.news.ws.validation.constraint.LikeDtoConstraint;

@Getter
@Setter
@LikeDtoConstraint
public class LikeDto {

    @JsonProperty("username")
    private String username;

    @JsonProperty("newsId")
    private Long newsId;
}
