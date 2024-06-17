package ru.xiitori.crudservice.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.xiitori.crudservice.models.Expense;
import ru.xiitori.crudservice.repositories.ExpenseRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExpenseServiceTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private ExpenseService expenseService;

    @Test
    void getExpenses_shouldCallRepository() {
        Expense expense = new Expense();
        when(expenseRepository.findAll()).thenReturn(List.of(expense));

        List<Expense> expenseList = expenseService.getExpenses();

        assertThat(expenseList.size()).isEqualTo(1);
        assertThat(expenseList.getFirst()).isEqualTo(expense);
    }

    @Test
    void getExpensesByClientId_shouldCallRepository() {
        Expense expense = new Expense();
        when(expenseRepository.findExpensesByClient_Id(1)).thenReturn(List.of(expense));

        List<Expense> expenseList = expenseService.getExpensesByClientId(1);

        assertThat(expenseList.size()).isEqualTo(1);
        assertThat(expenseList.getFirst()).isEqualTo(expense);
    }

    @Test
    void getExpenseById_shouldCallRepository() {
        Expense expense = new Expense();
        when(expenseRepository.findById(1)).thenReturn(Optional.of(expense));

        Optional<Expense> expenseOptional = expenseService.getExpenseById(1);

        assertThat(expenseOptional.isPresent()).isTrue();
    }

    @Test
    void saveExpense_shouldCallRepository() {
        Expense expense = mock(Expense.class);

        expenseService.saveExpense(expense);

        verify(expenseRepository, times(1)).save(expense);
    }
}
