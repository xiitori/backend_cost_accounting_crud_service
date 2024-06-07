package ru.xiitori.crudservice.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.xiitori.crudservice.dto.expense.AdminExpenseDTO;
import ru.xiitori.crudservice.models.Expense;
import ru.xiitori.crudservice.service.ExpenseService;
import ru.xiitori.crudservice.utils.exceptions.ExpenseNotFoundException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ModelMapper mapper;

    private final ExpenseService expenseService;

    @Autowired
    public ExpenseController(ModelMapper mapper, ExpenseService expenseService) {
        this.mapper = mapper;
        this.expenseService = expenseService;
    }


    @GetMapping()
    public List<AdminExpenseDTO> getExpenses() {
        return expenseService.getExpenses().stream().map(this::convertToAdminExpenseDTO).toList();
    }

    @GetMapping("/{id}")
    public AdminExpenseDTO getExpenseById(@PathVariable("id") int id) {
        Optional<Expense> optional = expenseService.getExpenseById(id);

        if (optional.isEmpty()) {
            throw new ExpenseNotFoundException("There is no expense with id " + id);
        }

        return convertToAdminExpenseDTO(optional.get());
    }

    public AdminExpenseDTO convertToAdminExpenseDTO(Expense expense) {
        AdminExpenseDTO adminExpenseDTO = mapper.map(expense, AdminExpenseDTO.class);

        adminExpenseDTO.setClientUsername(expense.getClient().getUsername());

        return adminExpenseDTO;
    }
}
