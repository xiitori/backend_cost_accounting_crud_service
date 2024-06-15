package ru.xiitori.crudservice.dto.income;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.xiitori.crudservice.models.types.IncomeType;

import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
public class IncomeDTO {

    private int id;

    private IncomeType incomeType;

    private String description;

    private double value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IncomeDTO incomeDTO = (IncomeDTO) o;
        return id == incomeDTO.id && Double.compare(value, incomeDTO.value) == 0 && incomeType == incomeDTO.incomeType && Objects.equals(description, incomeDTO.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, incomeType, description, value);
    }
}
