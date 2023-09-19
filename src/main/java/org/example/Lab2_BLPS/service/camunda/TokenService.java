package org.example.Lab2_BLPS.service.camunda;

import org.example.Lab2_BLPS.service.authorization.model.Token;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TokenService {
    private final static Map<String, Token> userTokens = new HashMap<>();

    public static Token getUserToken(String userId) {
        return userTokens.getOrDefault(userId, null);
    }

    public static void putUserToken(String userId, Token token) {
        userTokens.put(userId, token);
    }
}
