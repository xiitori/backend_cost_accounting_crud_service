package ru.xiitori.crudservice.utils;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.Instant;

@Setter
@Getter
public class ExceptionResponse {

    public String message;

    private Timestamp timestamp;

    public ExceptionResponse(Exception e) {
        this.message = e.getClass().getSimpleName() + ": " + e.getMessage();
        this.timestamp = Timestamp.from(Instant.now());
    }
}
