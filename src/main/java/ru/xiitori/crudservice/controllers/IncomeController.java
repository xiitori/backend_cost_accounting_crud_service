package ru.xiitori.crudservice.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.xiitori.crudservice.dto.income.AdminIncomeDTO;
import ru.xiitori.crudservice.models.Income;
import ru.xiitori.crudservice.services.IncomeService;
import ru.xiitori.crudservice.utils.exceptions.IncomeNotFoundException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/incomes")
public class IncomeController {

    private final ModelMapper modelMapper;

    private final IncomeService incomeService;

    @Autowired
    public IncomeController(ModelMapper modelMapper, IncomeService incomeService) {
        this.modelMapper = modelMapper;
        this.incomeService = incomeService;
    }

    @GetMapping
    public List<AdminIncomeDTO> getIncomes() {
        return incomeService.getIncomes().stream()
                .map(income -> modelMapper.map(income, AdminIncomeDTO.class)).toList();
    }

    @GetMapping("/{id}")
    public AdminIncomeDTO getIncomeById(@PathVariable("id") int id) {
        Optional<Income> optional = incomeService.getIncomeById(id);

        if (optional.isEmpty()) {
            throw new IncomeNotFoundException("There is no income with id " + id);
        }

        return modelMapper.map(optional.get(), AdminIncomeDTO.class);
    }
}
