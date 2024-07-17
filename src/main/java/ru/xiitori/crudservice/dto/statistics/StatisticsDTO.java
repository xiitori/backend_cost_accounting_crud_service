package ru.xiitori.crudservice.dto.statistics;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.xiitori.crudservice.dto.expense.ExpenseDTO;
import ru.xiitori.crudservice.dto.income.IncomeDTO;

import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class StatisticsDTO {

    private List<ExpenseDTO> expenses;

    private List<IncomeDTO> incomes;

    private HashMap<String, Double> expenseCategoryMap;

    private HashMap<String, Double> incomeCategoryMap;

    public StatisticsDTO(List<ExpenseDTO> expenses, List<IncomeDTO> incomes) {
        this.expenses = expenses;
        this.incomes = incomes;
        initCategoryMaps();
    }

    private void initCategoryMaps() {
        expenseCategoryMap = new HashMap<>();

        expenses.forEach(expense ->
                expenseCategoryMap.merge(expense.getExpenseType().toString(),
                        expense.getValue(), Double::sum));

        incomeCategoryMap = new HashMap<>();

        incomes.forEach(income -> incomeCategoryMap.merge(income.getIncomeType().toString(),
                        income.getValue(), Double::sum));
    }
}
