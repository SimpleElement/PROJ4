package org.example.Lab2_BLPS.service.authorization.ws.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.example.Lab2_BLPS.service.authorization.ws.validation.constraint.UserDtoConstraint;

@Getter
@Setter
@UserDtoConstraint
public class UserDto {

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;
}