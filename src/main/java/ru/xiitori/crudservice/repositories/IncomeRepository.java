package ru.xiitori.crudservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.xiitori.crudservice.models.Income;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Integer> {
}
