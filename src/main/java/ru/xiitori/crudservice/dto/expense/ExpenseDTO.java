package ru.xiitori.crudservice.dto.expense;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.xiitori.crudservice.models.types.ExpenseType;

@Setter
@Getter
@NoArgsConstructor
public class ExpenseDTO {

    private int id;

    private ExpenseType expenseType;

    private String description;

    private double value;
}
