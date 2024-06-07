package ru.xiitori.crudservice.dto.expense;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminExpenseDTO extends ExpenseDTO {

    private String clientUsername;
}
