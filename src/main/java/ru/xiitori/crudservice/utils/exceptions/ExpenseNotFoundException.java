package ru.xiitori.crudservice.utils.exceptions;

public class ExpenseNotFoundException extends EntityNotFoundException {
    public ExpenseNotFoundException(String message) {
        super(message);
    }
}
