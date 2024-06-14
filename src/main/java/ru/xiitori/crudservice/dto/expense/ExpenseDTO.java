package ru.xiitori.crudservice.dto.expense;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.xiitori.crudservice.models.types.ExpenseType;

import java.time.LocalDateTime;

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
}
