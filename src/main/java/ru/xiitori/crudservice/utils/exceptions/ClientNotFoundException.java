package ru.xiitori.crudservice.utils.exceptions;

public class ClientNotFoundException extends EntityNotFoundException {
    public ClientNotFoundException(String message) {
        super(message);
    }
}
