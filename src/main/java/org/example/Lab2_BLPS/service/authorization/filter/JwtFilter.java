package org.example.Lab2_BLPS.service.authorization.filter;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.Lab2_BLPS.common.object.FilterResponse;
import org.example.Lab2_BLPS.common.throwable.exception.BadRequestException;
import org.example.Lab2_BLPS.common.throwable.exception.NonAuthoritativeInformationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.example.Lab2_BLPS.service.authorization.service.JwtService;
import org.example.Lab2_BLPS.service.authorization.service.validator.JwtAssert;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    @SneakyThrows
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        if (request.getServletPath().contains("/api/v1/auth/") || request.getServletPath().contains("/camunda")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        try {
            JwtAssert.isExistsAccessToken(authHeader, "Не указан AccessToken");
        } catch (BadRequestException ex) {
            writeResponse(request, response, "AccessToken has expired", HttpStatus.BAD_REQUEST);
            return;
        }
        try {
            JwtAssert.isBearerAccessToken(authHeader, "Неревный тип AccessToken");
        } catch (BadRequestException ex) {
            writeResponse(request, response, "Wrong type of AccessToken", HttpStatus.BAD_REQUEST);
            return;
        }

        String token = authHeader.substring(7);

        try {
            JwtAssert.canParseToken(token, "Невозможно спарсить AccessToken");
        } catch (BadRequestException ex) {
            writeResponse(request, response, "Unable to parse AccessToken", HttpStatus.BAD_REQUEST);
            return;
        }
        try {
            jwtService.verifyToken(token);
        } catch (BadRequestException ex) {
            writeResponse(request, response, "AccessToken has not been verified", HttpStatus.BAD_REQUEST);
            return;
        }
        try {
            jwtService.isExpiredAccessToken(token);
        } catch (NonAuthoritativeInformationException ex) {
            writeResponse(request, response, "AccessToken has expired", HttpStatus.NON_AUTHORITATIVE_INFORMATION);
            return;
        }

        Map<String, Object> tokenClaims = jwtService.getClaims(token);

        String username = (String) tokenClaims.get("username");
        String roleOfUser = (String) tokenClaims.get("role");

        List<SimpleGrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(roleOfUser));
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, roles);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    @SneakyThrows
    private void writeResponse(HttpServletRequest request, HttpServletResponse response, String message, HttpStatus httpStatus) {
        response.setStatus(httpStatus.value());
        response.getWriter().write(
                FilterResponse.builder()
                        .timestamp(OffsetDateTime.now().toString())
                        .path(request.getServletPath())
                        .method(request.getMethod())
                        .status(httpStatus.value())
                        .error(message)
                        .build()
                        .toString()
        );
        response.getWriter().flush();
        response.getWriter().close();
    }
}
