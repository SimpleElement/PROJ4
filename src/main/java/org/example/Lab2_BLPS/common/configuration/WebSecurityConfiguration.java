package org.example.Lab2_BLPS.common.configuration;

import lombok.RequiredArgsConstructor;

import org.example.Lab2_BLPS.service.authorization.filter.JwtFilter;
import org.example.Lab2_BLPS.service.report.filter.NewsServiceFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.Filter;

@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {

    private final JwtFilter jwtFilter;

    private final NewsServiceFilter newsServiceFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf()
                .disable();

        http
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(newsServiceFilter, UsernamePasswordAuthenticationFilter.class);

        http
                .authorizeRequests()
                .antMatchers("/api/v1/mail/**").hasAnyAuthority("USER", "MODERATOR")
                .antMatchers("/api/v1/news/**").hasAnyAuthority("USER", "MODERATOR")
                .antMatchers("/api/v1/news/**").hasAnyAuthority("USER", "MODERATOR")
                .antMatchers("/api/v1/userReport/**").hasAuthority("USER")
                .antMatchers("/api/v1/moderatorReport/**").hasAuthority("MODERATOR");

        http.authorizeRequests()
                .antMatchers("/camunda/**").permitAll();

        return http.build();
    }
}