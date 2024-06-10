package ru.xiitori.crudservice.dto.income;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.xiitori.crudservice.models.types.IncomeType;

@Setter
@Getter
@NoArgsConstructor
public class IncomeDTO {

    private int id;

    private IncomeType incomeType;

    private String description;

    private double value;
}
