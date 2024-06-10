package ru.xiitori.crudservice.dto.income;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminIncomeDTO extends IncomeDTO {
    private String clientUsername;
}
