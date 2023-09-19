package org.example.Lab2_BLPS.service.news.ws.filter;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.example.Lab2_BLPS.common.object.page.Page;
import org.example.Lab2_BLPS.service.news.ws.validation.constraint.LikeFilterConstraint;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@LikeFilterConstraint
public class LikeFilter extends Page {

    private Long newsId;
}
