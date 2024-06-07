package ru.xiitori.crudservice.dto.expense;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.xiitori.crudservice.models.types.ExpenseType;

@Getter
@Setter
@NoArgsConstructor
public class ExpenseAddDTO {

    private ExpenseType expenseType;

    private String description;

    private double value;
}
