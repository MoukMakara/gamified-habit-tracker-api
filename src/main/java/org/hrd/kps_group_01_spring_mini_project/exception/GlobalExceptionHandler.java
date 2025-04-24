package org.hrd.kps_group_01_spring_mini_project.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.hrd.kps_group_01_spring_mini_project.model.response.ApiResponse;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail handleNotFoundException(NotFoundException ex) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        detail.setStatus(HttpStatus.NOT_FOUND);
        detail.setDetail(ex.getMessage());
        detail.setProperties(Map.of("Timestamp", LocalDateTime.now()));

        return detail;
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ProblemDetail UserAlreadyExistException(UserAlreadyExistException ex) {

        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        detail.setDetail(ex.getMessage());
        detail.setProperty("Timestamp", LocalDateTime.now());

        return detail;
    }

    @ExceptionHandler(HabitLogStatusException.class)
    public ProblemDetail HabitLogStatusException(HabitLogStatusException ex) {

        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        detail.setDetail(ex.getMessage());
        detail.setProperty("Timestamp", LocalDateTime.now());

        return detail;
    }


    @ExceptionHandler(NotYetVerifiedException.class)
    public ProblemDetail NotYetVerifiedException(NotYetVerifiedException ex) {

        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        detail.setDetail(ex.getMessage());
        detail.setProperty("Timestamp", LocalDateTime.now());

        return detail;
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ProblemDetail InvalidTokenException(InvalidTokenException ex) {

        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
        detail.setProperty("error", "UNAUTHORIZED");
        detail.setDetail(ex.getMessage());
        detail.setProperty("Timestamp", LocalDateTime.now());

        return detail;
    }


    @ExceptionHandler(RuntimeException.class)
    public ProblemDetail RuntimeException(RuntimeException ex) {

        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        detail.setDetail(ex.getMessage());
        detail.setProperty("Timestamp", LocalDateTime.now());

        return detail;
    }

    @ExceptionHandler(OTPValidationException.class)
    public ProblemDetail OTPValidationException(OTPValidationException ex) {

        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        detail.setDetail(ex.getMessage());
        detail.setProperty("Timestamp", LocalDateTime.now());

        return detail;
    }


    //for dto
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        detail.setProperty("errors", errors);
        detail.setProperty("Timestamp", LocalDateTime.now());

        return detail;
    }

    //for method parameter
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ProblemDetail handleMethodValidationException(HandlerMethodValidationException ex) {

        Map<String, String> errors = new HashMap<>();

        for (MessageSourceResolvable messageSourceResolvable : ex.getAllErrors()) {
            if (messageSourceResolvable.getCodes() != null) {
                errors.put(messageSourceResolvable.getCodes()[1]
                        .split("\\.")[1], messageSourceResolvable.getDefaultMessage());
            }
        }
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        detail.setProperty("errors", errors);
        detail.setProperty("Timestamp", LocalDateTime.now());

        return detail;
    }

}
