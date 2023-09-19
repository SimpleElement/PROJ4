package org.example.Lab2_BLPS.service.authorization.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Token {
    private String accessToken;
    private String refreshToken;
}