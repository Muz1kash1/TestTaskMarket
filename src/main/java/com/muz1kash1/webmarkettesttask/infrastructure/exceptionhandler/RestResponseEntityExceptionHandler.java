package com.muz1kash1.webmarkettesttask.infrastructure.exceptionhandler;

import static org.zalando.problem.Status.BAD_REQUEST;
import static org.zalando.problem.Status.CONFLICT;
import static org.zalando.problem.Status.INTERNAL_SERVER_ERROR;
import static org.zalando.problem.Status.NOT_FOUND;

import java.util.NoSuchElementException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.zalando.problem.Problem;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = IllegalArgumentException.class)
  protected ResponseEntity<Object> handleIllegalArgumentException(
      RuntimeException exception, WebRequest request) {

    Problem problem =
        Problem.builder()
            .withTitle("Illegal Argument Exception")
            .withDetail("Exception: " + exception + ". WebRequest: " + request)
            .withStatus(BAD_REQUEST)
            .build();

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .contentType(MediaType.valueOf("application/problem+json"))
        .body(problem);
  }

  @ExceptionHandler(
      value = {NoSuchElementException.class, ChangeSetPersister.NotFoundException.class})
  protected ResponseEntity<Object> handleNoSuchElementAndNotFoundException(
      RuntimeException exception, WebRequest request) {

    Problem problem =
        Problem.builder()
            .withTitle("Not Found Exception")
            .withDetail("Exception: " + exception + ". WebRequest: " + request)
            .withStatus(NOT_FOUND)
            .build();

    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .contentType(MediaType.valueOf("application/problem+json"))
        .body(problem);
  }

  @ExceptionHandler(value = IllegalStateException.class)
  protected ResponseEntity<Object> handleIllegalStateException(
      RuntimeException exception, WebRequest request) {

    Problem problem =
        Problem.builder()
            .withTitle("Illegal state")
            .withDetail("Exception: " + exception + ". WebRequest: " + request)
            .withStatus(CONFLICT)
            .build();

    return ResponseEntity.status(HttpStatus.CONFLICT)
        .contentType(MediaType.valueOf("application/problem+json"))
        .body(problem);
  }

  @ExceptionHandler
  protected ResponseEntity<Object> handleAnyElseExceptions(
      RuntimeException exception, WebRequest request) {

    Problem problem =
        Problem.builder()
            .withTitle("Internal server error")
            .withDetail("Exception: " + exception + ". WebRequest: " + request)
            .withStatus(INTERNAL_SERVER_ERROR)
            .build();

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .contentType(MediaType.valueOf("application/problem+json"))
        .body(problem);
  }
}