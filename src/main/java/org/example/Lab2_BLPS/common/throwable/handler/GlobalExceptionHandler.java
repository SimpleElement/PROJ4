package org.example.Lab2_BLPS.common.throwable.handler;

import java.util.List;

import org.example.Lab2_BLPS.common.throwable.exception.BadRequestException;
import org.example.Lab2_BLPS.common.throwable.exception.NonAuthoritativeInformationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.Map;
import java.util.stream.Collectors;

import static org.example.Lab2_BLPS.common.util.ResponseUtil.*;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<Object> handleBadRequestException(BadRequestException ex, WebRequest request) {
        return new ResponseEntity<>(getErrorBody(HttpStatus.BAD_REQUEST, request, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NonAuthoritativeInformationException.class})
    public ResponseEntity<Object> handleNonAuthoritativeInformationException(NonAuthoritativeInformationException ex, WebRequest request) {
        return new ResponseEntity<>(getErrorBody(HttpStatus.NOT_FOUND, request, ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> body = getErrorBody(status, request, "get_parameters_validation");
        body.put("messages", constraintErrors(ex.getBindingResult()));
        return new ResponseEntity<>(body, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> body = getErrorBody(status, request, "body_parameters_validation");
        body.put("messages", constraintErrors(ex.getBindingResult()));
        return new ResponseEntity<>(body, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> body = getErrorBody(status, request, "body_error");
        body.put("messages", List.of("Ошбибка тела запроса"));
        return new ResponseEntity<>(body, status);
    }
}
