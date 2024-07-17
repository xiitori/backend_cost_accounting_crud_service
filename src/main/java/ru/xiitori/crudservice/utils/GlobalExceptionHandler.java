package ru.xiitori.crudservice.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.xiitori.crudservice.utils.exceptions.*;

import java.time.format.DateTimeParseException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<ExceptionResponse> handleDateTimeParseException(DateTimeParseException e) {
        return new ResponseEntity<>(new ExceptionResponse(e), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ExceptionResponse> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e) {
        return new ResponseEntity<>(new ExceptionResponse(e), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(value = UpdateException.class)
    public ResponseEntity<ExceptionResponse> handleUpdate(UpdateException ex) {
        return new ResponseEntity<>(new ExceptionResponse(ex), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleException(EntityNotFoundException ex) {
        return new ResponseEntity<>(new ExceptionResponse(ex), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = RegistrationException.class)
    public ResponseEntity<ExceptionResponse> handleException(RegistrationException ex) {
        return new ResponseEntity<>(new ExceptionResponse(ex), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleException(BadCredentialsException ex) {
        return new ResponseEntity<>(new ExceptionResponse(ex), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<ExceptionResponse> handleException(LoginException ex) {
        return new ResponseEntity<>(new ExceptionResponse(ex), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = ClientNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleClientNotFound(ClientNotFoundException ex) {
        return new ResponseEntity<>(new ExceptionResponse(ex), HttpStatus.BAD_REQUEST);
    }
}
