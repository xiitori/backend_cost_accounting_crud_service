package ru.xiitori.crudservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.xiitori.crudservice.models.Expense;
import ru.xiitori.crudservice.repositories.ExpenseRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public List<Expense> getExpenses() {
        return expenseRepository.findAll();
    }

    public List<Expense> getExpensesByClientId(int id) {
        return expenseRepository.findExpensesByClient_Id(id);
    }

    public Optional<Expense> getExpenseById(int id) {
        return expenseRepository.findById(id);
    }

    @Transactional
    public void saveExpense(Expense expense) {
        expense.setMadeAt(LocalDateTime.now());
        expenseRepository.save(expense);
    }

    public List<Expense> getExpensesFromDateToDate(int id, LocalDateTime from, LocalDateTime to) {
        return expenseRepository.findExpensesByClient_IdAndMadeAtBetween(id, from, to);
    }

    @Transactional
    public void deleteExpenseById(int id) {
        expenseRepository.deleteById(id);
    }

    @Transactional
    public void updateExpense(int id, Expense expense) {
        expenseRepository.save(expense);
    }
}
