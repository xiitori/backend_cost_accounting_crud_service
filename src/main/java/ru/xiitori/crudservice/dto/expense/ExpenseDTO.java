package ru.xiitori.crudservice.dto.expense;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.xiitori.crudservice.models.types.ExpenseType;

import java.time.LocalDateTime;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
public class ExpenseDTO {

    private int id;

    private ExpenseType expenseType;

    private String description;

    private double value;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime madeAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpenseDTO that = (ExpenseDTO) o;
        return id == that.id && Double.compare(value, that.value) == 0 && expenseType == that.expenseType && Objects.equals(description, that.description) && Objects.equals(madeAt, that.madeAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, expenseType, description, value, madeAt);
    }
}
