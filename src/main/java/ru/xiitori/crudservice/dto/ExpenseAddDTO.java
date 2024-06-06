package ru.xiitori.crudservice.dto;

import ru.xiitori.crudservice.models.types.ExpenseType;

public class ExpenseAddDTO {

    private ExpenseType expenseType;

    private String description;

    public ExpenseAddDTO() {
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
