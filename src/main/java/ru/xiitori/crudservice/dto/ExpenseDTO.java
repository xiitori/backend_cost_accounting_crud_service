package ru.xiitori.crudservice.dto;

import ru.xiitori.crudservice.models.types.ExpenseType;

public class ExpenseDTO {

    private int id;

    private ExpenseType expenseType;

    private String description;

    public ExpenseDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ExpenseType getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(ExpenseType expenseType) {
        this.expenseType = expenseType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
