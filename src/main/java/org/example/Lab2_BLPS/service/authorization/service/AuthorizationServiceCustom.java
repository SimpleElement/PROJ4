package org.example.Lab2_BLPS.service.authorization.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.Lab2_BLPS.service.authorization.enm.RoleOfUser;
import org.example.Lab2_BLPS.service.authorization.model.Token;
import org.example.Lab2_BLPS.service.authorization.model.UserEntity;
import org.example.Lab2_BLPS.service.authorization.service.repository.UserRepository;
import org.example.Lab2_BLPS.service.authorization.service.util.Sha256PasswordUtil;
import org.example.Lab2_BLPS.service.authorization.service.validator.AuthorizationAssert;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthorizationServiceCustom {

    private final JwtService jwtService;

    private final UserRepository userRepository;

    private final Sha256PasswordUtil passwordUtil;

    // Messages
    private static final String USER_ALREADY_EXISTS = "Пользователь с таким именем уже существует";
    private static final String USER_NOT_FOUNT = "Пользователь с таким именем не найден";
    private static final String PASSWORD_OF_USER_INCORRECT = "Неверный пароль";

    private static final Set<String> forgottenKeys = new HashSet<>();

    @SneakyThrows
    public Token registration(UserEntity user) {
        AuthorizationAssert.isUserNotExists(userRepository.existsByUsername(user.getUsername()), user.getUsername(), USER_ALREADY_EXISTS);

        user.setPassword(passwordUtil.hashCode(user.getPassword()));
        user.setRoleOfUser(RoleOfUser.USER);

        userRepository.save(user);

        return jwtService.generateToken(user);
    }

    @SneakyThrows
    public Token registrationWithRole(UserEntity user) {
        AuthorizationAssert.isUserNotExists(userRepository.existsByUsername(user.getUsername()), user.getUsername(), USER_ALREADY_EXISTS);

        user.setPassword(passwordUtil.hashCode(user.getPassword()));

        userRepository.save(user);
        return jwtService.generateToken(user);
    }

    @SneakyThrows
    public Token authorization(UserEntity user) {
        AuthorizationAssert.isUserExists(userRepository.existsByUsername(user.getUsername()), USER_NOT_FOUNT);

        UserEntity userFromBd = userRepository.findByUsername(user.getUsername());

        AuthorizationAssert.isPasswordCorrect(passwordUtil.hashCode(user.getPassword()), userFromBd.getPassword(), PASSWORD_OF_USER_INCORRECT);

        return jwtService.generateToken(userFromBd);
    }

    @SneakyThrows
    public Token refreshToken(Token token) {
        String refreshToken = token.getRefreshToken();

        AuthorizationAssert.isForgottenKeys(forgottenKeys.contains(refreshToken), "Данный рефреш токен неактуален");

        jwtService.validateRefreshToken(refreshToken);
        Map<String, Object> claims = jwtService.getClaims(refreshToken);

        String username = (String) claims.get("username");

        UserEntity userFromBd = userRepository.findByUsername(username);

        return jwtService.refreshToken(userFromBd, token.getRefreshToken());
    }

    @SneakyThrows
    public void exit(Token token) {
        String refreshToken = token.getRefreshToken();
        Map<String, Object> claims = jwtService.getClaims(refreshToken);

        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (username.equals(claims.get("username")))
            forgottenKeys.add(token.getRefreshToken());
    }
}
