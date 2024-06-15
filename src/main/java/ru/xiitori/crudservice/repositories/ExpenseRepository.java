package ru.xiitori.crudservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.xiitori.crudservice.models.Expense;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer> {
    List<Expense> findExpensesByClient_Id(int id);

    List<Expense> findExpensesByMadeAtBetween(LocalDateTime start, LocalDateTime end);
}
