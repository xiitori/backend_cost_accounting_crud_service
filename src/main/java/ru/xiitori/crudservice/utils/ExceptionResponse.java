package ru.xiitori.crudservice.utils;

import java.sql.Timestamp;
import java.time.Instant;

public class ExceptionResponse {

    public String message;

    private Timestamp timestamp;

    public ExceptionResponse(String message) {
        this.message = message;
        this.timestamp = Timestamp.from(Instant.now());
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
