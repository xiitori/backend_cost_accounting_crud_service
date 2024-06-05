package ru.xiitori.crudservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.xiitori.crudservice.models.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer> {
}
