package ru.xiitori.crudservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.xiitori.crudservice.models.Income;
import ru.xiitori.crudservice.repositories.IncomeRepository;
import ru.xiitori.crudservice.security.ClientDetails;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class IncomeService {

    private final IncomeRepository incomeRepository;

    @Autowired
    public IncomeService(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    public List<Income> getIncomesByClientId(int id) {
        return incomeRepository.findAllByClient_Id(id);
    }

    public Optional<Income> getIncomeById(int id) {
        return incomeRepository.findById(id);
    }

    public List<Income> getIncomes() {
        return incomeRepository.findAll();
    }

    @Transactional
    public void saveIncome(Income income) {
        incomeRepository.save(income);
    }

    @Transactional
    public void deleteIncomeById(int id) {
        incomeRepository.deleteById(id);
    }

    @Transactional
    public void updateIncome(Income income) {
        ClientDetails clientDetails = (ClientDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        income.setClient(clientDetails.getClient());

        incomeRepository.save(income);
    }
}
