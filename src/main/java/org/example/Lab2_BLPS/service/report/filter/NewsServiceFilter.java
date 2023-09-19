package org.example.Lab2_BLPS.service.report.filter;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.Lab2_BLPS.common.object.FilterResponse;
import org.example.Lab2_BLPS.service.report.model.NewsServiceBanEntity;
import org.example.Lab2_BLPS.service.report.service.ReportService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.OffsetDateTime;

@Component
@RequiredArgsConstructor
public class NewsServiceFilter extends OncePerRequestFilter {

    private final ReportService reportService;

    @Override
    @SneakyThrows
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        if (request.getServletPath().contains("/api/v1/auth/")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (request.getServletPath().contains("/api/v1/news")) {
            String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (reportService.userHaveBanFromNewsService(username)) {
                NewsServiceBanEntity banInfo = reportService.getNewsServiceBanEntityByUsername(username);
                writeResponse(request, response, "You are blocked from the News resource until " + banInfo.getUnbanDate().toString(), HttpStatus.LOCKED);
                response.flushBuffer();
            }
        }
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