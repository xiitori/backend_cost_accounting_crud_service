package ru.xiitori.crudservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.xiitori.crudservice.models.Income;
import ru.xiitori.crudservice.repositories.IncomeRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class IncomeService {

    private final IncomeRepository incomeRepository;

    @Autowired
    public IncomeService(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    public List<Income> getAllIncomesByClientId(int id) {
        return incomeRepository.findAllByClient_Id(id);
    }

    @Transactional
    public void saveIncome(Income income) {
        incomeRepository.save(income);
    }

    @Transactional
    public void deleteIncome(int id) {
        incomeRepository.deleteById(id);
    }

    @Transactional
    public void updateIncome(Income income) {
        incomeRepository.save(income);
    }
}
